package ua.com.foxminded.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.dao.entity.ContactInfo;
import ua.com.foxminded.dao.entity.Group;
import ua.com.foxminded.dao.entity.Student;
import ua.com.foxminded.dao.entity.Teacher;
import ua.com.foxminded.model.dto.ContactInfoDto;
import ua.com.foxminded.model.dto.StudentDto;
import ua.com.foxminded.model.dto.TeacherDto;
import ua.com.foxminded.model.enums.Degree;
import ua.com.foxminded.model.enums.Department;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class TeacherConverterTest {

    Teacher teacher;
    TeacherDto teacherDto;

    TeacherConverter teacherConverter = new TeacherConverter();

    String uuidPerson = "d4d8b36a-4435-11eb-b378-0242ac130002";
    String uuidContactInfo = "2a6e5b4a-4436-11eb-b378-0242ac130002";

    @BeforeEach
    void initEach() {
        teacher =  ((Teacher) new Teacher().setIdPerson(uuidPerson)
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

        teacherDto = ((TeacherDto) new TeacherDto().setIdPerson(UUID.fromString(uuidPerson))
                .setFirstName("Maria")
                .setLastName("Kokoshka")
                .setContactInfo(new ContactInfoDto().setId(UUID.fromString(uuidContactInfo))
                        .setIndex(80339)
                        .setCountry("China")
                        .setCity("Hong Kong")
                        .setStreet("Mau the dum")
                        .setHouse("1/1")
                        .setApartment(1)
                        .setEmail("kuku@ru.muku")
                        .setPhone1("123456789")
                        .setPhone2("98741236547")))
                .setDegree(Degree.DOCENT)
                .setDepartment(Department.CHEMISTRY)
                .setPermanent(true)
                .setSalary(BigDecimal.valueOf(1000));
    }
    
    @Test
    public void convertDtoToEntity_whenValidTeacherDTO_thenReturnTeacher(){
        Teacher expected = teacher;

        Teacher actual = teacherConverter.convertDtoToEntity(teacherDto);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void convertEntityToDto_whenValidTeacher_thenReturnTeacherDTO(){
        TeacherDto expected = teacherDto;

        TeacherDto actual = teacherConverter.convertEntityToDto(teacher);

        assertThat(actual).isEqualTo(expected);
    }
}
