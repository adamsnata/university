package ua.com.foxminded.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
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

import ua.com.foxminded.model.dto.*;
import ua.com.foxminded.model.enums.DayOfWeek;
import ua.com.foxminded.service.interfaces.ScheduleItemService;
import ua.com.foxminded.service.interfaces.TeacherService;

@ExtendWith(MockitoExtension.class)
class ScheduleRestControllerTeacherTest {

    @InjectMocks
    private ScheduleRestController scheduleRestController;

    @Mock
    private ScheduleItemService scheduleService;

    @Mock
    private TeacherService teacherService;
    
    MockMvc mockMvc;

    TeacherDto validTeacher;
    
    ScheduleItemDto schedule;
    
    List<ScheduleItemDto> scheduleWeek = new ArrayList<>();
    
    LocalDate date;
    
    String month;
    
    @BeforeEach
    void setUpBeforeClass() {
        
        validTeacher = (TeacherDto) new TeacherDto().setIdPerson(UUID.randomUUID());
        
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
    void getSchedule_whenTeacherWeek_thanStatusOK() throws Exception {
        given(teacherService.findTeacher(any())).willReturn(validTeacher);
        given(scheduleService.findWeekScheduleTeacher(any())).willReturn(scheduleWeek);
        
        mockMvc.perform(get("/schedule/teacher/" + validTeacher.getIdPerson()))
                .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.week[0].group.name", is(schedule.getGroup().getName())))
               .andExpect(jsonPath("$.week[0].subject.name", is(schedule.getSubject().getName())))
               .andExpect(jsonPath("$.week[0].dayOfWeek", is(schedule.getDayOfWeek().toString())))
               .andExpect(jsonPath("$.week[0].timeSlot.startTime[0]").value(equalTo(8)))
               .andExpect(jsonPath("$.week[0].room.name", is(schedule.getRoom().getName())));
    }
    
    @Test
    void getSchedule_whenTeacherDay_thanStatusOK() throws Exception {
        given(teacherService.findTeacher(any())).willReturn(validTeacher);
        given(scheduleService.findDayScheduleTeacher(any(),any())).willReturn(scheduleWeek);
        
        mockMvc.perform(get("/schedule/teacher/" + validTeacher.getIdPerson() + "?day=2020-06-01"))
                .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.2020-06-01[0].group.name", is(schedule.getGroup().getName())))
               .andExpect(jsonPath("$.2020-06-01[0].subject.name", is(schedule.getSubject().getName())))
               .andExpect(jsonPath("$.2020-06-01[0].dayOfWeek", is(schedule.getDayOfWeek().toString())))
               .andExpect(jsonPath("$.2020-06-01[0].timeSlot.startTime[0]").value(equalTo(8)))
               .andExpect(jsonPath("$.2020-06-01[0].room.name", is(schedule.getRoom().getName())));
    }
    
    @Test
    void getSchedule_whenTeacherMonth_thanStatusOK() throws Exception {
        
        Map<String, List<ScheduleItemDto>> scheduleMonth = new HashMap<String, List<ScheduleItemDto>>();
        scheduleMonth.put("2020-06-01", scheduleWeek);
        
        given(teacherService.findTeacher(any())).willReturn(validTeacher);   
        given(scheduleService.findMonthScheduleTeacher(any(),any())).willReturn(scheduleMonth);
        
        mockMvc.perform(get("/schedule/teacher/" + validTeacher.getIdPerson() + "?month=JUNE"))
                .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.2020-06-01[0].group.name", is(schedule.getGroup().getName())))
               .andExpect(jsonPath("$.2020-06-01[0].subject.name", is(schedule.getSubject().getName())))
               .andExpect(jsonPath("$.2020-06-01[0].dayOfWeek", is(schedule.getDayOfWeek().toString())))
               .andExpect(jsonPath("$.2020-06-01[0].timeSlot.startTime[0]").value(equalTo(8)))
               .andExpect(jsonPath("$.2020-06-01[0].room.name", is(schedule.getRoom().getName())));
    }

    @Test
    void getSchedule_whenTeacherNotFound_thanStatusNotFound() throws Exception {
        given(teacherService.findTeacher(any())).willReturn(new TeacherDto());

        mockMvc.perform(get("/schedule/teacher/" + validTeacher.getIdPerson()))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getSchedule_whenScheduleNull_thanStatusNotFound() throws Exception {
        given(teacherService.findTeacher(any())).willReturn(validTeacher);
        given(scheduleService.findWeekScheduleTeacher(any())).willReturn(null);

        mockMvc.perform(get("/schedule/teacher/" + validTeacher.getIdPerson()))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
