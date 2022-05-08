package ua.com.foxminded.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ua.com.foxminded.dao.entity.Student;
import ua.com.foxminded.dao.interfaces.StudentDao;
import ua.com.foxminded.model.enums.StudyStatus;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class StudentDaoImplTest {
    
    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private StudentDao studentDao;
    
    private static String personUUID = "69c4623a-fb11-11ea-adc1-0242ac120002";
    private static Student student;

    @BeforeAll
    static void Init() throws Exception {
     
        student = new Student()
                               .setStudyStatus(StudyStatus.FINISHED.toString())
                               .setCitizenship("German")
                               .setGrant(new BigDecimal(100))
                               .setStartOfStudy(LocalDate.of(2015, 12, 31));
        student.setIdPerson(personUUID)
               .setFirstName("Nina")
               .setLastName("Ivan");
    }
   
    @Test
    public void studentFindById_whenSearchStudent_thenFirstNameRight() {
        entityManager.persist(student);
        entityManager.flush();

        Optional<Student> foundStudent = studentDao.findById(personUUID);

        String studentName = foundStudent.orElse(new Student()).getFirstName();

        assertThat(studentName).isEqualTo(student.getFirstName());
  }
    
    @Test
    public void studentFindAll_whenSearchAllStudents_thenStudentsCount4() {

        entityManager.persist(student);
        entityManager.flush();

        List<Student> students = (List<Student>) studentDao.findAll();

        assertThat(students.size()).isEqualTo(4);
  }
    
    @Test
    public void studentDeleteById_whenDeleteStudent_thenStudentsCount1Less() {
       studentDao.deleteById("f17e5b3a-5963-4098-a2ff-26b497701e70");
       List<Student> students = (List<Student>) studentDao.findAll();

       assertThat(students.size()).isEqualTo(2);
  }  
    
    @Test
    public void studentEdit_whenEdit1Student_thenFirstNameNew() {

      Student  studentEdit = new Student()
                               .setStudyStatus(StudyStatus.FINISHED.toString())
                               .setCitizenship("German")
                               .setGrant(new BigDecimal(100))
                               .setStartOfStudy(LocalDate.of(2015, 12, 31));
      studentEdit.setIdPerson("b68266d6-3494-4909-b8df-807d40299ff7")
               .setFirstName("Katja_changed")
               .setLastName("IvanLoza");
        
        String expected = studentEdit.getFirstName();
        
        List<Student> studentsOld = (List<Student>) studentDao.findAll();
     
        Student editStudent =  studentDao.save(studentEdit);
        List<Student> studentsNew = (List<Student>) studentDao.findAll();

       String actual = editStudent.getFirstName();
       assertThat(studentsOld.size()).isEqualTo(studentsNew.size());
       assertThat(actual).isEqualTo(expected);
  } 
    
    @Test
    public void studentAdd_whenAdd1Student_thenStudentsCount1More() {

      Student  studentNew = new Student()
                               .setStudyStatus(StudyStatus.FINISHED.toString())
                               .setCitizenship("German")
                               .setGrant(new BigDecimal(100))
                               .setStartOfStudy(LocalDate.of(2015, 12, 31));
      studentNew.setIdPerson("a5acbb43-7c2d-4de5-bf01-b9a4162c66dc")
               .setFirstName("Nata_new")
               .setLastName("Dada");
        
        String expected = studentNew.getFirstName();
        
        List<Student> studentsOld = (List<Student>) studentDao.findAll();
        
        Student editStudent =  studentDao.save(studentNew);
        List<Student> studentsNew = (List<Student>) studentDao.findAll();

       String actual = editStudent.getFirstName();
       assertThat(studentsOld.size() + 1).isEqualTo(studentsNew.size());
       assertThat(actual).isEqualTo(expected);
  }    
}
