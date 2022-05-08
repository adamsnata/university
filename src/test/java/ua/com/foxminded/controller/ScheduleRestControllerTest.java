package ua.com.foxminded.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ua.com.foxminded.model.dto.GroupDto;
import ua.com.foxminded.model.dto.RoomDto;
import ua.com.foxminded.model.dto.ScheduleItemDto;
import ua.com.foxminded.model.dto.StudentDto;
import ua.com.foxminded.model.dto.SubjectDto;
import ua.com.foxminded.model.dto.TimeSlotDto;
import ua.com.foxminded.model.enums.DayOfWeek;

import ua.com.foxminded.service.interfaces.ScheduleItemService;
import ua.com.foxminded.service.interfaces.StudentService;

@ExtendWith(MockitoExtension.class)
class ScheduleRestControllerTest {

    @InjectMocks
    private ScheduleRestController scheduleRestController;

    @Mock
    private ScheduleItemService scheduleService;

    @Mock
    private StudentService studentService;
    
    MockMvc mockMvc;

    StudentDto validStudent;
    
    ScheduleItemDto schedule;
    
    List<ScheduleItemDto> scheduleWeek = new ArrayList<>();
    
    LocalDate date;
    
    String month;
    
    @BeforeEach
    void setUpBeforeClass() {
        
        validStudent = (StudentDto) new StudentDto().setIdPerson(UUID.randomUUID());
        
        schedule = new ScheduleItemDto().setDayOfWeek(DayOfWeek.FRIDAY)
                .setGroup(new GroupDto().setName("group"))
                .setSubject(new SubjectDto().setName("Sport"))
                .setTimeSlot(new TimeSlotDto().setStartTime(LocalTime.of(8, 10)))
                .setRoom(new RoomDto().setName("good"));
                        
        scheduleWeek.add(schedule);
        
        month = "JUNE";
        
        date = LocalDate.of(LocalDate.now().getYear(), Month.valueOf(month.toUpperCase()), 1);
        
        mockMvc = MockMvcBuilders.standaloneSetup(scheduleRestController).build();
    }

    @Test
    void getSchedule_whenStudentWeek_thanStatusOk() throws Exception {
        given(studentService.findStudent(any())).willReturn(validStudent);
        given(scheduleService.findWeekScheduleStudent(any())).willReturn(scheduleWeek);
        
        mockMvc.perform(get("/schedule/student/" + validStudent.getIdPerson()))
                .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.week[0].group.name", is(schedule.getGroup().getName())))
               .andExpect(jsonPath("$.week[0].subject.name", is(schedule.getSubject().getName())))
               .andExpect(jsonPath("$.week[0].dayOfWeek", is(schedule.getDayOfWeek().toString())))
               .andExpect(jsonPath("$.week[0].timeSlot.startTime[0]").value(equalTo(8)))
               .andExpect(jsonPath("$.week[0].room.name", is(schedule.getRoom().getName())));
    }
    
    @Test
    void getSchedule_whenStudentDay_thanStatusOk() throws Exception {
        given(studentService.findStudent(any())).willReturn(validStudent);
        given(scheduleService.findDayScheduleStudent(any(),any())).willReturn(scheduleWeek);
        
        mockMvc.perform(get("/schedule/student/" + validStudent.getIdPerson() + "?day=2020-06-01"))
                .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.2020-06-01[0].group.name", is(schedule.getGroup().getName())))
               .andExpect(jsonPath("$.2020-06-01[0].subject.name", is(schedule.getSubject().getName())))
               .andExpect(jsonPath("$.2020-06-01[0].dayOfWeek", is(schedule.getDayOfWeek().toString())))
               .andExpect(jsonPath("$.2020-06-01[0].timeSlot.startTime[0]").value(equalTo(8)))
               .andExpect(jsonPath("$.2020-06-01[0].room.name", is(schedule.getRoom().getName())));
    }
    
    @Test
    void getSchedule_whenStudentMonth__thanStatusOk() throws Exception {
        
        Map<String, List<ScheduleItemDto>> scheduleMonth = new HashMap<String, List<ScheduleItemDto>>();
        scheduleMonth.put("2020-06-01", scheduleWeek);
        
        given(studentService.findStudent(any())).willReturn(validStudent);   
        given(scheduleService.findMonthScheduleStudent(any(),any())).willReturn(scheduleMonth);
        
        mockMvc.perform(get("/schedule/student/" + validStudent.getIdPerson() + "?month=JUNE"))
                .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.2020-06-01[0].group.name", is(schedule.getGroup().getName())))
               .andExpect(jsonPath("$.2020-06-01[0].subject.name", is(schedule.getSubject().getName())))
               .andExpect(jsonPath("$.2020-06-01[0].dayOfWeek", is(schedule.getDayOfWeek().toString())))
               .andExpect(jsonPath("$.2020-06-01[0].timeSlot.startTime[0]").value(equalTo(8)))
               .andExpect(jsonPath("$.2020-06-01[0].room.name", is(schedule.getRoom().getName())));
    }

    @Test
    void getSchedule_whenStudentNotFound_thanStatusNotFound() throws Exception {
        given(studentService.findStudent(any())).willReturn(new StudentDto());

        mockMvc.perform(get("/schedule/student/" + validStudent.getIdPerson()))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getSchedule_whenScheduleNull_thanStatusNotFound() throws Exception {
        given(studentService.findStudent(any())).willReturn(validStudent);
        given(scheduleService.findWeekScheduleStudent(any())).willReturn(null);

        mockMvc.perform(get("/schedule/student/" + validStudent.getIdPerson()))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
