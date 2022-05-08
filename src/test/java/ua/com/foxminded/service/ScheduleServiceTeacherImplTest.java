package ua.com.foxminded.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import ua.com.foxminded.TestConfig;
import ua.com.foxminded.converter.ScheduleItemConverter;
import ua.com.foxminded.dao.entity.*;
import ua.com.foxminded.dao.interfaces.ScheduleItemDao;
import ua.com.foxminded.dao.interfaces.TeacherDao;
import ua.com.foxminded.model.dto.ScheduleItemDto;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {TestConfig.class})
@ExtendWith(MockitoExtension.class)
public class ScheduleServiceTeacherImplTest {
    @InjectMocks
    ScheduleItemServiceImpl scheduleService;

    @Mock
    static ScheduleItemDao scheduleItemDao;

    @Mock
    TeacherDao teacherDao;

    @Spy
    ScheduleItemConverter scheduleItemConverter;

    static List<ScheduleItem> scheduleItems;

    static String uuid = "e90567da-434e-11eb-b378-0242ac130002";

    @BeforeAll
    public static void setUpBeforeClass() throws Exception {

        scheduleItems = new ArrayList<>();
        scheduleItems.add(new ScheduleItem().setId(uuid)
                .setDayOfWeek("TUESDAY")
                .setGroup(new Group().setName("gr-1"))
                .setRoom(new Room().setName("room 1"))
                .setSubject(new Subject().setName("Maths"))
                .setTimeSlot(new TimeSlot().setSerialNumber(1)));
    }

    @Test
    void findWeekScheduleTeacher_whenValidSchedule_thenReturnSchedule() {
        when(scheduleItemDao.findByTeacherIdPerson(any())).thenReturn(scheduleItems);
        List<ScheduleItemDto> schedule = new ArrayList<>();

        List<ScheduleItemDto> scheduleWeek = scheduleService.findWeekScheduleTeacher(UUID.fromString(uuid));

        assertThat(scheduleWeek).isNotEqualTo(schedule);
    }

    @Test
    void findWeekScheduleTeacher_whenIdNull_thenReturnEmptySchedule() {
        List<ScheduleItemDto> schedule = new ArrayList<>();

        List<ScheduleItemDto> scheduleWeek = scheduleService.findWeekScheduleTeacher(null);

        assertThat(scheduleWeek).isEqualTo(schedule);
    }

    @Test
    void findDayScheduleTeacher_whenValidDateSchedule_thenReturnSchedule() {
        when(scheduleItemDao.findByTeacherIdPerson(any())).thenReturn(scheduleItems);
       List<ScheduleItemDto> schedule = new ArrayList<>();

        LocalDate date = LocalDate.of(2020, 12, 8);
        List<ScheduleItemDto> scheduleDay = scheduleService.findDayScheduleTeacher(UUID.fromString(uuid), date);

        assertThat(scheduleDay).isNotEqualTo(schedule);
    }

    @Test
    void findDayScheduleTeacher_whenInValidDateSchedule_thenReturnSchedule() {
        when(scheduleItemDao.findByTeacherIdPerson(any())).thenReturn(scheduleItems);
        List<ScheduleItemDto> schedule = new ArrayList<>();

        LocalDate date = LocalDate.of(2020, 12, 9);
        List<ScheduleItemDto> scheduleDay = scheduleService.findDayScheduleTeacher(UUID.fromString(uuid), date);

        assertThat(scheduleDay).isEqualTo(schedule);
    }

    @Test
    void findMonthScheduleTeacher_whenValidDateSchedule_thenReturnSchedule() {
        when(scheduleItemDao.findByTeacherIdPerson(any())).thenReturn(scheduleItems);

        LocalDate date = LocalDate.of(2020, 12, 8);
        Map<String, List<ScheduleItemDto>> scheduleMonth = scheduleService.findMonthScheduleTeacher(UUID.fromString(uuid), date);

        assertThat(scheduleMonth.size()).isEqualTo(5);
    }
}

