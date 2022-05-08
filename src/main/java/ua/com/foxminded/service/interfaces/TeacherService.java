package ua.com.foxminded.service.interfaces;

import java.util.List;
import java.util.UUID;

import ua.com.foxminded.model.dto.TeacherDto;

public interface TeacherService {
    public Boolean addTeacher(TeacherDto teacher);
    public Boolean editTeacher(TeacherDto teacher, UUID uuid);
    public Boolean deleteTeacher(UUID uuid);
    public TeacherDto findTeacher(UUID uuid);
    public List<TeacherDto> findAllTeacher(); 
}
