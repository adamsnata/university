package ua.com.foxminded.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import ua.com.foxminded.TestConfig;
import ua.com.foxminded.converter.ScheduleItemConverter;
import ua.com.foxminded.converter.StudentConverter;
import ua.com.foxminded.dao.entity.*;
import ua.com.foxminded.dao.interfaces.ScheduleItemDao;
import ua.com.foxminded.dao.interfaces.StudentDao;
import ua.com.foxminded.model.dto.*;
import ua.com.foxminded.model.enums.DayOfWeek;
import ua.com.foxminded.service.interfaces.ScheduleItemService;

@ContextConfiguration(classes = {TestConfig.class})
@ExtendWith(MockitoExtension.class)
class ScheduleServiceImplTest {

    @InjectMocks
    ScheduleItemServiceImpl scheduleService;

    @Mock
    static ScheduleItemDao scheduleItemDao;

    @Mock
    StudentDao studentDao;

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
    void findWeekScheduleStudent_whenValidSchedule_thenReturnSchedule() {
        when(scheduleItemDao.findByGroupIdGroup(any())).thenReturn(scheduleItems);
        when(studentDao.findById(org.mockito.ArgumentMatchers.any())).thenReturn(Optional.ofNullable(new Student().setGroup(new Group().setId("a3c71f50-434f-11eb-b378-0242ac130002"))));
        List<ScheduleItemDto> schedule = new ArrayList<>();

        List<ScheduleItemDto> scheduleWeek = scheduleService.findWeekScheduleStudent(UUID.fromString(uuid));

        assertThat(scheduleWeek).isNotEqualTo(schedule);
    }

    @Test
    void findWeekScheduleStudent_whenNotFoundStudent_thenReturnEmptySchedule() {
        when(studentDao.findById(org.mockito.ArgumentMatchers.any())).thenReturn(Optional.ofNullable(new Student()));
        List<ScheduleItemDto> schedule = new ArrayList<>();

        List<ScheduleItemDto> scheduleWeek = scheduleService.findWeekScheduleStudent(UUID.fromString(uuid));

        assertThat(scheduleWeek).isEqualTo(schedule);
    }

    @Test
    void findDayScheduleStudent_whenValidDateSchedule_thenReturnSchedule() {
        when(scheduleItemDao.findByGroupIdGroup(any())).thenReturn(scheduleItems);
        when(studentDao.findById(org.mockito.ArgumentMatchers.any())).thenReturn(Optional.ofNullable(new Student().setGroup(new Group().setId("a3c71f50-434f-11eb-b378-0242ac130002"))));
        List<ScheduleItemDto> schedule = new ArrayList<>();

        LocalDate date = LocalDate.of(2020, 12, 8);
        List<ScheduleItemDto> scheduleDay = scheduleService.findDayScheduleStudent(UUID.fromString(uuid), date);

        assertThat(scheduleDay).isNotEqualTo(schedule);
    }

    @Test
    void findDayScheduleStudent_whenInValidDateSchedule_thenReturnSchedule() {
        when(scheduleItemDao.findByGroupIdGroup(any())).thenReturn(scheduleItems);
        when(studentDao.findById(org.mockito.ArgumentMatchers.any())).thenReturn(Optional.ofNullable(new Student().setGroup(new Group().setId("a3c71f50-434f-11eb-b378-0242ac130002"))));
        List<ScheduleItemDto> schedule = new ArrayList<>();

        LocalDate date = LocalDate.of(2020, 12, 9);
        List<ScheduleItemDto> scheduleDay = scheduleService.findDayScheduleStudent(UUID.fromString(uuid), date);

        assertThat(scheduleDay).isEqualTo(schedule);
    }

    @Test
    void findMonthScheduleStudent_whenValidDateSchedule_thenReturnSchedule() {
        when(scheduleItemDao.findByGroupIdGroup(any())).thenReturn(scheduleItems);
        when(studentDao.findById(org.mockito.ArgumentMatchers.any())).thenReturn(Optional.ofNullable(new Student().setGroup(new Group().setId("a3c71f50-434f-11eb-b378-0242ac130002"))));
        List<ScheduleItemDto> schedule = new ArrayList<>();

        LocalDate date = LocalDate.of(2020, 12, 8);
        Map<String, List<ScheduleItemDto>> scheduleMonth = scheduleService.findMonthScheduleStudent(UUID.fromString(uuid), date);

        assertThat(scheduleMonth.size()).isEqualTo(31);
    }

    @Test
    void addScheduleItem_whenAddValidSchedule_thenTrue() {
        when(scheduleItemDao.save(org.mockito.ArgumentMatchers.any(ScheduleItem.class))).thenReturn(new ScheduleItem());

        Boolean isAddSchedule = scheduleService.addScheduleItem(new ScheduleItemDto());

        assertTrue(isAddSchedule);
    }

    @Test
    void addScheduleItem_whenScheduleNull_thenFalse() {
        Boolean isAddSchedule = scheduleService.addScheduleItem(null);

        assertFalse(isAddSchedule);
    }

    @Test
    void addScheduleItem_whenEditValidSchedule_thenTrue() {
        when(scheduleItemDao.save(org.mockito.ArgumentMatchers.any(ScheduleItem.class))).thenReturn(new ScheduleItem());

        Boolean isAddSchedule = scheduleService.editScheduleItem(new ScheduleItemDto());

        assertTrue(isAddSchedule);
    }

    @Test
    void addScheduleItem_whenScheduleNull_thenTrue() {
        Boolean isEditSchedule = scheduleService.editScheduleItem(null);

        assertFalse(isEditSchedule);
    }

    @Test
    void addScheduleItem_whenDaleteValidSchedule_thenTrue() {
        Boolean isDeleteSchedule = scheduleService.deleteScheduleItem(UUID.fromString(uuid));

        assertTrue(isDeleteSchedule);
    }

    @Test
    void addScheduleItem_whenIdNull_thenFalse() {
        Boolean isDeleteSchedule = scheduleService.deleteScheduleItem(null);

        assertFalse(isDeleteSchedule);
    }
}
