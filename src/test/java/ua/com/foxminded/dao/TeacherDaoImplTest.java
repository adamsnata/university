package ua.com.foxminded.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ua.com.foxminded.dao.entity.Teacher;
import ua.com.foxminded.dao.interfaces.TeacherDao;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TeacherDaoImplTest {

    @Autowired
    TeacherDao teacherDao;

    private static String personUUID = "69c97b95-8ce5-4923-8d39-4c17bd5389d0";

    @Test
    public void teacherFindById_whenSearchTeacher_thenFirstNameRight() {
       
        String expected = "Yashwant";
 
        Teacher foundTeacher = teacherDao.findById(personUUID).orElse(new Teacher());
 
       String actual = foundTeacher.getFirstName(); 
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void teacherFindAll_whenSearchAllTeachers_thenTeachersCount3() {
    
        List<Teacher> teachers = (List<Teacher>) teacherDao.findAll();

        assertThat(teachers.size()).isEqualTo(3);
    }

    @Test
    public void teacherDeleteById_whenDeleteTeacher_thenTeachersCount1Less() {

        teacherDao.deleteById("69c97b95-8ce5-4923-8d39-4c17bd5389d0");
        List<Teacher> teachers = (List<Teacher>) teacherDao.findAll();

        assertThat(teachers.size()).isEqualTo(2);
    }

    @Test
    public void teacherEdit_whenEdit1Teacher_thenFirstNameNew() {

        Teacher teacherEdit = new Teacher()
                                           .setDegree("DOCENT")
                                           .setDepartment("MATHEMATICS")
                                           .setPermanent(true)
                                           .setSalary(new BigDecimal(9999));
        teacherEdit.setIdPerson("eb144b62-3d34-4187-a162-73f0e9ef5b68")
                   .setFirstName("Maria_Changed")
                   .setLastName("Ivanovna");

        String expected = teacherEdit.getFirstName();

        List<Teacher> teachersOld = (List<Teacher>) teacherDao.findAll();

        Teacher editTeacher = teacherDao.save(teacherEdit);
        List<Teacher> teachersNew = (List<Teacher>) teacherDao.findAll();

        String actual = editTeacher.getFirstName();
        assertThat(teachersOld.size()).isEqualTo(teachersNew.size());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void teacherAdd_whenAdd1Teacher_thenTeachersCount1More() {

        Teacher teacherNew = new Teacher()
                                          .setDegree("DOCENT")
                                          .setDepartment("MATHEMATICS")
                                          .setPermanent(true)
                                          .setSalary(new BigDecimal(9999));
        teacherNew.setIdPerson("fe24c14c-3e21-11eb-b378-0242ac130002")
                   .setFirstName("New Teacher")
                   .setLastName("Ivanovna");

        String expected = teacherNew.getFirstName();

        List<Teacher> teachersOld = (List<Teacher>) teacherDao.findAll();

        Teacher editTeacher = teacherDao.save(teacherNew);
        List<Teacher> teachersNew = (List<Teacher>) teacherDao.findAll();

        String actual = editTeacher.getFirstName();
        assertThat(teachersOld.size() + 1).isEqualTo(teachersNew.size());
        assertThat(actual).isEqualTo(expected);
    }
}
