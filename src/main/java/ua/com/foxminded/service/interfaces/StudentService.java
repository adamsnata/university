package ua.com.foxminded.service.interfaces;

import java.util.List;
import java.util.UUID;

import ua.com.foxminded.model.dto.StudentDto;


public interface StudentService {

    public Boolean addStudent(StudentDto student);
    public Boolean editStudent(StudentDto studentDto, UUID uuid);
    public Boolean deleteStudent(UUID uuid);
    public StudentDto findStudent(UUID uuid);
    public List<StudentDto> findAllStudent();
}
