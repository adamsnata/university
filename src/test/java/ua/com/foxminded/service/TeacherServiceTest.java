package ua.com.foxminded.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import ua.com.foxminded.TestConfig;
import ua.com.foxminded.converter.TeacherConverter;
import ua.com.foxminded.dao.entity.ContactInfo;
import ua.com.foxminded.dao.entity.Group;
import ua.com.foxminded.dao.entity.Teacher;
import ua.com.foxminded.dao.interfaces.TeacherDao;
import ua.com.foxminded.model.dto.TeacherDto;
import ua.com.foxminded.model.dto.TeacherDto;
import ua.com.foxminded.model.enums.Degree;
import ua.com.foxminded.model.enums.Department;
import ua.com.foxminded.service.interfaces.GroupService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {TestConfig.class})
@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {
    @Mock
    TeacherDao teacherDao;

    @InjectMocks
    TeacherServiceImpl teacherServise;

    @Spy
    TeacherConverter teacherConverter = new TeacherConverter();

    @Spy
    GroupService groupService;

    String uuid = "1bfc7ee3-28de-4e7d-b068-8dccd095d655";

    Teacher validTeacher;

    @BeforeEach
    void setUp()  {

        validTeacher =  ((Teacher) new Teacher().setIdPerson(uuid)
                .setFirstName("Maria")
                .setLastName("Kokoshka")
                .setContactInfo(new ContactInfo().setId(UUID.randomUUID().toString())
                        .setIndex(80339)
                        .setCountry("China")
                        .setCity("Hong Kong")
                        .setStreet("Mau the dum")
                        .setHouse("1/1")
                        .setApartment(1)
                        .setEmail("kuku@ru.muku")
                        .setPhone1("123456789")
                        .setPhone2("98741236547")))
                .setDegree("DOCENT")
                .setDepartment("CHEMISTRY")
                .setPermanent(true)
                .setSalary(BigDecimal.valueOf(1000));
    }

    @Test
    void addTeacher_whenAddValidTeacher_thenTrue() {
        when(teacherDao.save(org.mockito.ArgumentMatchers.any(Teacher.class))).thenReturn(new Teacher());
        when(teacherDao.findById(any())).thenReturn(Optional.ofNullable(validTeacher));

        boolean isAddTeacher = teacherServise.addTeacher(new TeacherDto());

        assertTrue(isAddTeacher);
    }

    @Test
    void addTeacher_whenAddNotValidTeacher_thenFalse() {
        when(teacherDao.save(org.mockito.ArgumentMatchers.any(Teacher.class))).thenReturn(new Teacher());
        when(teacherDao.findById(any())).thenReturn( Optional.ofNullable(new Teacher()));

        boolean isAddTeacher = teacherServise.addTeacher(new TeacherDto());

        assertFalse(isAddTeacher);
    }

    @Test
    void addTeacher_whenAddNullTeacher_thenFalse() {
        boolean isAddTeacher = teacherServise.addTeacher(null);

        assertFalse(isAddTeacher);
    }

    @Test
    void editTeacher_whenEditValidTeacher_thenTrue() {
        when(teacherDao.save(org.mockito.ArgumentMatchers.any(Teacher.class))).thenReturn(new Teacher());

        boolean isEditTeacher = teacherServise.editTeacher(new TeacherDto(), UUID.fromString(uuid));
        assertTrue(isEditTeacher);
    }

    @Test
    void editTeacher_whenNulldTeacher_thenFalse() {
        boolean isEditTeacher = teacherServise.editTeacher(null, null);

        assertFalse(isEditTeacher);
    }

    @Test
    void deleteTeacher_whenValidTeacher_thenTrue() {
        assertTrue(teacherServise.deleteTeacher(UUID.randomUUID()));
    }

    @Test
    void deleteTeacher_whenValidTeacher_thenFalse() {
        assertFalse(teacherServise.deleteTeacher(null));
    }

    @Test
    void findTeacher_whenUuidValid_thenReturnTeacher() {
        when(teacherDao.findById(org.mockito.ArgumentMatchers.any())).thenReturn(Optional.ofNullable(validTeacher));
        TeacherDto teacherFound = teacherServise.findTeacher(UUID.fromString(uuid));

        assertThat(teacherFound).isNotEqualTo(new TeacherDto());
    }
    
    @Test
    void findTeacher_whenUuidNull_thenReturnNewTeacher() {

        TeacherDto teacherFound = teacherServise.findTeacher(null);

        assertThat(teacherFound).isEqualTo(new TeacherDto());
    }

    @Test
    void findAllTest() {
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(validTeacher);
        given(teacherDao.findAll()).willReturn(teachers);

        List<TeacherDto> teachersFound = teacherServise.findAllTeacher();

        assertThat(teachersFound).hasSize(1);
    }
}

