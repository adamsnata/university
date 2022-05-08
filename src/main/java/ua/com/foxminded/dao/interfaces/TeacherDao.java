package ua.com.foxminded.dao.interfaces;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.dao.entity.Teacher;

@Repository
@Qualifier("teacherDao")
public interface TeacherDao extends CrudRepository<Teacher, String>{

}
