package ua.com.foxminded.controller;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import ua.com.foxminded.model.dto.ContactInfoDto;
import ua.com.foxminded.model.dto.GroupDto;
import ua.com.foxminded.model.dto.TeacherDto;
import ua.com.foxminded.model.dto.TeacherDto;
import ua.com.foxminded.model.enums.Degree;
import ua.com.foxminded.model.enums.Department;
import ua.com.foxminded.model.enums.Specialty;
import ua.com.foxminded.model.enums.StudyStatus;
import ua.com.foxminded.service.interfaces.TeacherService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TeachersRestControllerTest {

    @InjectMocks
    private TeachersRestController teachersRestController;

    @Mock
    private TeacherService teacherService;

    List<TeacherDto> teachers = new ArrayList<TeacherDto>();

    MockMvc mockMvc;

    TeacherDto validTeacher;

    @BeforeEach
    void setUpBeforeClass() {

        ContactInfoDto contactInfo = new ContactInfoDto()
                .setId(UUID.randomUUID())
                .setIndex(80339)
                .setCountry("Russia")
                .setCity("Moskow")
                .setStreet("Lenina")
                .setHouse("100")
                .setApartment(1)
                .setEmail("putin@ru.muku")
                .setPhone1("9999999999")
                .setPhone2("7777777777");
        
        validTeacher = ((TeacherDto) new TeacherDto()
                                                     .setIdPerson(UUID.randomUUID())
                                                     .setFirstName("Anna")
                                                     .setLastName("Petrova")
                                                     .setContactInfo(contactInfo));
        
        validTeacher.setDegree(Degree.PROFESSOR)
        .setDepartment(Department.MECHANICAL_ENGINEERING)
        .setPermanent(true)
        .setSalary(new BigDecimal(1000));
                                                                                                                  
        teachers.add(validTeacher);

        mockMvc = MockMvcBuilders.standaloneSetup(teachersRestController).build();
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void findAllteacher_whenValidArrayTeachers_thenStatusOk() throws Exception {
        given(teacherService.findAllTeacher()).willReturn(teachers);

        mockMvc.perform(get("/teachers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void findAllTeacher_whenTeachersNull_thenStatusNotFound() throws Exception {
        given(teacherService.findAllTeacher()).willReturn(null);

        mockMvc.perform(get("/teachers"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void createTeacher_whenValidIdTeacher_thenStatusCreated() throws Exception {
        given(teacherService.addTeacher(any(TeacherDto.class))).willReturn(true);

        mockMvc.perform(post("/teacher").content(asJsonString(validTeacher))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void createTeacher_whenNotValidIdPerson_thenStatusBadRequest() throws Exception {
        given(teacherService.addTeacher(any(TeacherDto.class))).willReturn(true);

        mockMvc.perform(post("/teacher").content(asJsonString(validTeacher.setDepartment(null)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createTeacher_whenNotValidTeacher_thenStatusNotFound() throws Exception {
        given(teacherService.addTeacher(any(TeacherDto.class))).willReturn(false);

        mockMvc.perform(post("/teacher").content(asJsonString(validTeacher))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getTeacher_whenValidTeacher_thenStatusOk() throws Exception {
        given(teacherService.findTeacher(any())).willReturn(validTeacher);

        mockMvc.perform(get("/teacher/" + validTeacher.getIdPerson()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idPerson", is(validTeacher.getIdPerson().toString())))
                .andExpect(jsonPath("$.firstName", is("Anna")));
    }

    @Test
    void getTeacher_whenTeacherNull_thenStatusNotFound() throws Exception {
        given(teacherService.findTeacher(any())).willReturn(null);

        mockMvc.perform(get("/teacher/" + validTeacher.getIdPerson()))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void editTeacher_whenValidIdTeacher_thenStatusCreated() throws Exception {
        given(teacherService.editTeacher(any(TeacherDto.class), any())).willReturn(true);
        given(teacherService.findTeacher(any())).willReturn(validTeacher);

        mockMvc.perform(put("/teacher/" + validTeacher.getIdPerson())
                .content(asJsonString(validTeacher))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void editTeacher_whenNotValidIdPerson_thenStatusBadRequest() throws Exception {
        given(teacherService.editTeacher(any(TeacherDto.class), any())).willReturn(true);

        mockMvc.perform(put("/teacher/" + validTeacher.getIdPerson())
                .content(asJsonString(validTeacher.setDepartment(null)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void editTeacher_whenNotValidTeacher_thenStatusNotFound() throws Exception {
        given(teacherService.editTeacher(any(TeacherDto.class), any())).willReturn(true);
        given(teacherService.findTeacher(any())).willReturn(new TeacherDto());

        mockMvc.perform(put("/teacher/" + validTeacher.getIdPerson())
                .content(asJsonString(validTeacher))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteTeacher_whenDeleteTeacher_thenStatusOK() throws Exception {
        given(teacherService.findTeacher(any())).willReturn(validTeacher).willReturn(new TeacherDto());
        given(teacherService.deleteTeacher(any())).willReturn(true);

        mockMvc.perform(delete("/teacher/" + validTeacher.getIdPerson()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteTeacher_whenNotFoundTeacher_thenStatusNotFound() throws Exception {
        given(teacherService.findTeacher(any())).willReturn(new TeacherDto());

        mockMvc.perform(delete("/teacher/" + validTeacher.getIdPerson()))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteTeacher_whenNotDeleteTeacher_thenStatus_notImplemented() throws Exception {
        given(teacherService.findTeacher(any())).willReturn(validTeacher).willReturn(validTeacher);
        given(teacherService.deleteTeacher(any())).willReturn(true);

        mockMvc.perform(delete("/teacher/" + validTeacher.getIdPerson()))
                .andExpect(status().isNotImplemented());
    }
}
