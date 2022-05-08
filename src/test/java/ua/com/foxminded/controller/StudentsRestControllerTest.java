package ua.com.foxminded.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.anything;

import java.sql.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ua.com.foxminded.model.dto.ContactInfoDto;
import ua.com.foxminded.model.dto.GroupDto;
import ua.com.foxminded.model.dto.StudentDto;
import ua.com.foxminded.model.enums.Specialty;
import ua.com.foxminded.model.enums.StudyStatus;
import ua.com.foxminded.service.interfaces.StudentService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class StudentsRestControllerTest {

    @InjectMocks
    private StudentsRestController studentsRestController;

    @Mock
    private StudentService studentService;

    List<StudentDto> students = new ArrayList<>();

    MockMvc mockMvc;

    StudentDto validStudent;

    @BeforeEach
    void setUpBeforeClass() {

        ContactInfoDto contactInfo = new ContactInfoDto()
                .setId(UUID.randomUUID())
                .setIndex(80339)
                .setCountry("China")
                .setCity("Hong Kong")
                .setStreet("Mau the dum")
                .setHouse("1/1")
                .setApartment(1)
                .setEmail("kuku@ru.muku")
                .setPhone1("123456789")
                .setPhone2("98741236547");
        
        validStudent = ((StudentDto) new StudentDto()
                                                     .setIdPerson(UUID.randomUUID())
                                                     .setFirstName("Maria")
                                                     .setLastName("Kokoshka")
                                                     .setContactInfo(contactInfo));
        
        validStudent.setCitizenship("ChinaMensch")
                    .setStudyStatus(StudyStatus.FINISHED)
                    .setStartOfStudy(LocalDate.now())                                                                                             
                    .setGroup(new GroupDto().setId( UUID.randomUUID())
                            .setName("gggr")
                            .setSpecialty(Specialty.ECONOMY));
                                                                                                                  
        students.add(validStudent);

        mockMvc = MockMvcBuilders.standaloneSetup(studentsRestController).build();
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
    void findAllStudent_whenValidArrayStudents_thenStatusOk() throws Exception {
        given(studentService.findAllStudent()).willReturn(students);

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void findAllStudent_whenStudentsNull_thenStatusNotFound() throws Exception {
        given(studentService.findAllStudent()).willReturn(null);

        mockMvc.perform(get("/students"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void createStudent_whenValidIdStudent_thenStatusCreated() throws Exception {
        given(studentService.addStudent(any(StudentDto.class))).willReturn(true);

        mockMvc.perform(post("/student").content(asJsonString(validStudent))
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isCreated());
    }

    @Test
    void createStudent_whenNotValidIdPerson_thenStatusBadRequest() throws Exception {
        given(studentService.addStudent(any(StudentDto.class))).willReturn(true);

        mockMvc.perform(post("/student").content(asJsonString(validStudent.setStartOfStudy(null)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createStudent_whenNotValidStudent_thenStatusNotFound() throws Exception {
        given(studentService.addStudent(any(StudentDto.class))).willReturn(false);

        mockMvc.perform(post("/student").content(asJsonString(validStudent))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getStudent_whenValidStudent_thenStatusOk() throws Exception {
        given(studentService.findStudent(any())).willReturn(validStudent);

        mockMvc.perform(get("/student/" + validStudent.getIdPerson()))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.idPerson", is(validStudent.getIdPerson().toString())))
               .andExpect(jsonPath("$.firstName", is("Maria")));
    }

    @Test
    void getStudent_whenStudentNull_thenStatusNotFound() throws Exception {
        given(studentService.findStudent(any())).willReturn(null);

        mockMvc.perform(get("/student/" + validStudent.getIdPerson()))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    
    @Test
    void editStudent_whenValidIdStudent_thenStatusCreated() throws Exception {
        given(studentService.editStudent(any(StudentDto.class), any())).willReturn(true);
        given(studentService.findStudent(any())).willReturn(validStudent);
        
        mockMvc.perform(put("/student/" + validStudent.getIdPerson())
                .content(asJsonString(validStudent))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
    }

    @Test
    void editStudent_whenNotValidIdPerson_thenStatusBadRequest() throws Exception {

        mockMvc.perform(put("/student/" + validStudent.getIdPerson())
                .content(asJsonString(validStudent.setStartOfStudy(null)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp", is(notNullValue())))
                .andExpect(jsonPath("$.message", is("Validation errors")))
                .andExpect(jsonPath("$.status", is("BAD_REQUEST")))
                .andExpect(jsonPath("$.subErrors[0]") .isArray())
                .andExpect(jsonPath("$.subErrors[0]", hasSize(4)))
                .andExpect(jsonPath("$.subErrors[0]", hasItem("student")))
                .andExpect(jsonPath("$.subErrors[0]", containsInAnyOrder("student","startOfStudy",null,"must not be null")));
        verify(studentService, times(0)).editStudent(any(StudentDto.class),any());
    }

    @Test
    void editStudent_whenNotValidStudent_thenStatusNotFound() throws Exception {
        given(studentService.editStudent(any(StudentDto.class), any())).willReturn(true);
        given(studentService.findStudent(any())).willReturn(new StudentDto());

        mockMvc.perform(put("/student/" + validStudent.getIdPerson())
                .content(asJsonString(validStudent))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteStudent_whenDeleteStudent_thenStatusOK() throws Exception {
        given(studentService.findStudent(any())).willReturn(validStudent).willReturn(new StudentDto());
        given(studentService.deleteStudent(any())).willReturn(true);
        
        mockMvc.perform(delete("/student/" + validStudent.getIdPerson()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteStudent_whenNotFoundStudent_thenStatusNotFound() throws Exception {
        given(studentService.findStudent(any())).willReturn(new StudentDto());

        mockMvc.perform(delete("/student/" + validStudent.getIdPerson()))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteStudent_whenNotDeleteStudent_thenStatus_notImplemented() throws Exception {
        given(studentService.findStudent(any())).willReturn(validStudent).willReturn(validStudent);
        given(studentService.deleteStudent(any())).willReturn(true);

        mockMvc.perform(delete("/student/" + validStudent.getIdPerson()))
                .andExpect(status().isNotImplemented());
    }
}
