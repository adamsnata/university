package ua.com.foxminded.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import ua.com.foxminded.dao.entity.Group;
import ua.com.foxminded.dao.entity.Student;
import ua.com.foxminded.model.dto.GroupDto;
import ua.com.foxminded.model.dto.StudentDto;
import ua.com.foxminded.model.enums.Degree;
import ua.com.foxminded.model.enums.Department;
import ua.com.foxminded.model.enums.Specialty;

@Component
public class GroupConverter {

    public Group convertDtoToEntity(GroupDto groupDto) {
        Group group = new Group();
        Optional.ofNullable(groupDto.getId())
                .ifPresent(ss -> group.setId(ss.toString()));

        group.setName(groupDto.getName());

        Optional.ofNullable(groupDto.getSpecialty())
                .ifPresent(ss -> group.setSpecialty(ss.name()));

        List<Student> students = new ArrayList<>();
        groupDto.getStudents().forEach(studentDto->{
            students.add((Student) new Student().setIdPerson(studentDto.getIdPerson().toString()));
        }
        );
        group.setStudents(students);   
        return group;
    }

    public GroupDto convertEntityToDto(Group group) {
        
        GroupDto groupDto = new GroupDto()
                .setId(UUID.fromString(group.getId()))
                .setName(group.getName());
        
        Optional.ofNullable(group.getSpecialty())
        .ifPresent(ss -> groupDto.setSpecialty(Specialty.valueOf(ss))); 
        
        List<StudentDto> studentDtoList = new ArrayList<>();
        Optional.ofNullable(group.getStudents())
                .ifPresent(st -> {
                    st.forEach(student -> {
                        studentDtoList.add((StudentDto) new StudentDto().setIdPerson(UUID.fromString(student.getIdPerson())));
                    });
                        });
        groupDto.setStudents(studentDtoList);
        return groupDto;
    }
}
