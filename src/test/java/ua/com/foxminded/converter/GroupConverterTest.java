package ua.com.foxminded.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.dao.entity.Group;
import ua.com.foxminded.dao.entity.Student;
import ua.com.foxminded.model.dto.GroupDto;
import ua.com.foxminded.model.dto.StudentDto;
import ua.com.foxminded.model.enums.Specialty;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class GroupConverterTest {

    GroupConverter groupConverter = new GroupConverter();

    Group group;
    GroupDto groupDto;

    Student student;
    StudentDto studentDto;

    List<Student> students = new ArrayList<>();
    List<StudentDto> studentsDTO = new ArrayList<>();

    String uuidGroup = "a1790160-4434-11eb-b378-0242ac130002";
    String uuidPerson = "d4d8b36a-4435-11eb-b378-0242ac130002";

    @BeforeEach
    void initEach() {
        System.out.println(" in Each");
        group = new Group()
                .setId(uuidGroup)
                .setName("gggr")
                .setSpecialty("ECONOMY");

        groupDto = new GroupDto()
                .setId(UUID.fromString(uuidGroup))
                .setName("gggr")
                .setSpecialty(Specialty.ECONOMY);

        student =  ((Student) new Student().setIdPerson(uuidPerson));

        studentDto = (StudentDto) new StudentDto().setIdPerson(UUID.fromString(uuidPerson));

        students.add(student);
        studentsDTO.add(studentDto);

        group.setStudents(students);
        groupDto.setStudents(studentsDTO);
    }

    @Test
    public void convertDtoToEntity_whenValidGroupDTO_thenReturnGroup(){
      Group expected = group;

      Group actual = groupConverter.convertDtoToEntity(groupDto);

      assertThat(actual).isEqualTo(expected);
  }

    @Test
    public void convertEntityToDto_whenValidGroup_thenReturnGroupDTO(){
        GroupDto expected = groupDto;

        GroupDto actual = groupConverter.convertEntityToDto(group);

        assertThat(actual).isEqualTo(expected);
    }
}
