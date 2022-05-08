package ua.com.foxminded.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ua.com.foxminded.dao.entity.Group;
import ua.com.foxminded.dao.entity.Room;
import ua.com.foxminded.dao.entity.ScheduleItem;
import ua.com.foxminded.dao.entity.Student;
import ua.com.foxminded.dao.entity.Subject;
import ua.com.foxminded.dao.entity.Teacher;
import ua.com.foxminded.dao.entity.TimeSlot;
import ua.com.foxminded.dao.interfaces.ScheduleItemDao;
import ua.com.foxminded.dao.interfaces.StudentDao;
import ua.com.foxminded.util.FileParser;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ScheduleItemDaoImplTest {

    private static String scheduleItemUUID = "8f240794-fb1e-11ea-adc1-0242ac120002";
    private static String groupUUID = "0c149265-57c0-4942-a1e5-06c8b6983a23";
    private static String subjectUUID = "c4320591-215a-4b11-958e-9864c184ec2a";
    private static String roomUUID = "026621cc-73a6-40ba-8ea7-86628f4cb802";
    private static String timeSlotUUID = "d789bb56-b534-402b-8baa-38547218761c";
    private static String dayOfWeek = "MONDAY";
    private static String teacherUUID = "69c97b95-8ce5-4923-8d39-4c17bd5389d0";

    private static ScheduleItem scheduleItem;
    private ScheduleItem scheduleItemExpected;

    @Autowired
    private ScheduleItemDao scheduleItemDao;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeAll
    static void Init() throws Exception {

       scheduleItem = new ScheduleItem()
                                                      .setId(scheduleItemUUID)
                                                      .setGroup(new Group().setId(groupUUID))
                                                      .setSubject(new Subject().setId(subjectUUID))
                                                      .setRoom(new Room().setId(roomUUID))
                                                      .setTimeSlot(new TimeSlot().setId(timeSlotUUID))
                                                      .setDayOfWeek(dayOfWeek)
                                                      .setTeacher((Teacher) new Teacher().setIdPerson(teacherUUID));

        ScheduleItem scheduleItemExpected = new ScheduleItem().setDayOfWeek("MONDAY")
                                                              .setGroup(new Group().setName("gr-1"))
                                                              .setRoom(new Room().setName("room 1"))
                                                              .setSubject(new Subject().setName("Maths"))
                                                              .setTimeSlot(new TimeSlot().setSerialNumber(1));

    }

    @Test
    public void scheduleFindByTeacherIdPerson_whenSearchSchedule_ThenCountSchedule3() {
        List<ScheduleItem> schedule = scheduleItemDao.findByTeacherIdPerson(teacherUUID);
        assertThat(schedule.size()).isEqualTo(3);
    }

    @Test
    public void scheduleFindByGroupIdGroup_whenSearchSchedule_ThenCountSchedule2() {
        List<ScheduleItem> schedule = scheduleItemDao.findByGroupIdGroup(groupUUID);
        assertThat(schedule.size()).isEqualTo(2);
    }

    @Test
    public void scheduleDeleteById_whenDeleteSchedule_thenCount1Less() {
        scheduleItemDao.deleteById("5e9330ba-162f-45ea-b9de-605ae734f585");
        List<ScheduleItem> schedule = scheduleItemDao.findByTeacherIdPerson(teacherUUID);
        assertThat(schedule.size()).isEqualTo(2);
    }
    
    @Test
    public void scheduleSave_addNew_sizePlusOne() {
        scheduleItemDao.save(scheduleItem);
        List<ScheduleItem> schedule = scheduleItemDao.findByTeacherIdPerson(teacherUUID);
        assertThat(schedule.size()).isEqualTo(4);
    }
   
    @Test
    public void scheduleSave_editOldSchedule_sizeTheSame() {
        scheduleItemDao.save(scheduleItem.setId("5e9330ba-162f-45ea-b9de-605ae734f585"));
        List<ScheduleItem> schedule = scheduleItemDao.findByTeacherIdPerson(teacherUUID);
        assertThat(schedule.size()).isEqualTo(3);
    }
}
