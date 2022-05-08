package ua.com.foxminded.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.dao.entity.ContactInfo;
import ua.com.foxminded.dao.entity.Group;
import ua.com.foxminded.dao.entity.Student;
import ua.com.foxminded.model.dto.ContactInfoDto;
import ua.com.foxminded.model.dto.GroupDto;
import ua.com.foxminded.model.dto.StudentDto;
import ua.com.foxminded.model.enums.Specialty;
import ua.com.foxminded.model.enums.StudyStatus;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class StudentConverterTest {

    StudentConverter studentConverter = new StudentConverter();

    Student student;
    StudentDto studentDto;

    Group group;
    GroupDto groupDto;

    String uuidPerson = "d4d8b36a-4435-11eb-b378-0242ac130002";
    String uuidContactInfo = "2a6e5b4a-4436-11eb-b378-0242ac130002";
    String uuidGroup = "a1790160-4434-11eb-b378-0242ac130002";

    @BeforeEach
    void initEach() {
        group = new Group()
                .setId(uuidGroup)
                .setName("gggr");

        groupDto = new GroupDto()
                .setId(UUID.fromString(uuidGroup))
                .setName("gggr");

        student = ((Student) new Student().setIdPerson(uuidPerson)
                .setFirstName("Maria")
                .setLastName("Kokoshka")
                .setContactInfo(new ContactInfo().setId(uuidContactInfo)
                        .setIndex(80339)
                        .setCountry("China")
                        .setCity("Hong Kong")
                        .setStreet("Mau the dum")
                        .setHouse("1/1")
                        .setApartment(1)
                        .setEmail("kuku@ru.muku")
                        .setPhone1("123456789")
                        .setPhone2("98741236547")))
                .setCitizenship("ChinaMensch")
                .setGroup(group)
                .setStudyStatus("FINISHED")
                .setStartOfStudy(LocalDate.of(2021, 12, 12));

        studentDto = ((StudentDto) new StudentDto().setIdPerson(UUID.fromString(uuidPerson))
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
                .setCitizenship("ChinaMensch")
                .setGroup(groupDto)
                .setStudyStatus(StudyStatus.FINISHED)
                .setStartOfStudy(LocalDate.of(2021, 12, 12));
    }

    @Test
    public void convertDtoToEntity_whenValidStudentDTO_thenReturnStudent(){
        Student expected = student;

        Student actual = studentConverter.convertDtoToEntity(studentDto);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void convertEntityToDto_whenValidStudent_thenReturnStudentDTO(){
        StudentDto expected = studentDto;

        StudentDto actual = studentConverter.convertEntityToDto(student);

        assertThat(actual).isEqualTo(expected);
    }
}
