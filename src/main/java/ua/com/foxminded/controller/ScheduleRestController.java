package ua.com.foxminded.controller;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ua.com.foxminded.error.ErrorDescriptor;
import ua.com.foxminded.model.dto.ScheduleItemDto;
import ua.com.foxminded.model.dto.StudentDto;
import ua.com.foxminded.model.dto.TeacherDto;
import ua.com.foxminded.service.interfaces.ScheduleItemService;
import ua.com.foxminded.service.interfaces.StudentService;
import ua.com.foxminded.service.interfaces.TeacherService;

@RestController
@RequestMapping("/schedule")
public class ScheduleRestController {

    @Autowired
    ScheduleItemService scheduleService;

    @Autowired
    StudentService studentService;

    @Autowired
    TeacherService teacherService;

    @GetMapping(value = "/student/{uuid}",
            produces = {"application/json"})
    public ResponseEntity<Map<String, List<ScheduleItemDto>>> showScheduleStudent(@PathVariable("uuid") String uuid,
            @RequestParam(required = false) String month,
            @RequestParam(required = false) String day) {
        
        StudentDto studentCheck = studentService.findStudent(UUID.fromString(uuid));
        if (studentCheck.getIdPerson()==null) {
            return new ResponseEntity(new ErrorDescriptor().setStatus(HttpStatus.NOT_FOUND).setMessage("Not find Student with id  " + uuid),
                    HttpStatus.NOT_FOUND); 
        }
        boolean isMonthEmpty = (month == null || month == "");
        boolean isDayEmpty = (day == null || day == "");
        
        Map<String, List<ScheduleItemDto>> schedule = new HashMap<String, List<ScheduleItemDto>>();
       
        if (isMonthEmpty && isDayEmpty ) {
            List<ScheduleItemDto> scheduleWeek = scheduleService.findWeekScheduleStudent(UUID.fromString(uuid));
            schedule.put("week", scheduleWeek);
        } else if (isDayEmpty) {
            LocalDate date = LocalDate.of(LocalDate.now().getYear(), Month.valueOf(month.toUpperCase()), 1);
            schedule = scheduleService.findMonthScheduleStudent(
                    UUID.fromString(uuid), date);
        } else {
            LocalDate date = LocalDate.parse(day);           
            List<ScheduleItemDto> scheduleDay = scheduleService.findDayScheduleStudent(UUID.fromString(uuid), date);        
            schedule.put(day, scheduleDay);
        }
          
        return schedule.values().toArray()[0] != null
                ? new ResponseEntity<>(schedule, HttpStatus.OK)
                        : new ResponseEntity(new ErrorDescriptor().setStatus(HttpStatus.NOT_FOUND).setMessage("Not find Student with id  " + uuid),
                                HttpStatus.NOT_FOUND);
    }
    

    @GetMapping(value = "/teacher/{uuid}",
            produces = {"application/json"})
    public ResponseEntity<Map<String, List<ScheduleItemDto>>> showScheduleTeacher(@PathVariable("uuid") String uuid,
            @RequestParam(required = false) String month,
            @RequestParam(required = false) String day) {

        TeacherDto teacherCheck = teacherService.findTeacher(UUID.fromString(uuid));
        if (teacherCheck.getIdPerson()==null) {
            return new ResponseEntity(new ErrorDescriptor().setStatus(HttpStatus.NOT_FOUND).setMessage("Not find teacher with id  " + uuid),
                    HttpStatus.NOT_FOUND);
        }
        
        boolean isMonthEmpty = (month == null || month == "");
        boolean isDayEmpty = (day == null || day == "");
        
        Map<String, List<ScheduleItemDto>> schedule = new HashMap<String, List<ScheduleItemDto>>();
        
        if (isMonthEmpty && isDayEmpty) {
            List<ScheduleItemDto> scheduleWeek = scheduleService.findWeekScheduleTeacher(UUID.fromString(uuid));
            schedule.put("week", scheduleWeek);
        } else if (isDayEmpty) {
            LocalDate date = LocalDate.of(LocalDate.now().getYear(), Month.valueOf(month.toUpperCase()), 1);
            schedule = scheduleService.findMonthScheduleTeacher(
                    UUID.fromString(uuid), date);
            
          } else {
            LocalDate date = LocalDate.parse(day);
            List<ScheduleItemDto> scheduleDay = scheduleService.findDayScheduleTeacher(UUID.fromString(uuid), date);
            schedule.put(day, scheduleDay);
        }

        return schedule.values().toArray()[0] != null &&  !schedule.isEmpty()
                ? new ResponseEntity<>(schedule, HttpStatus.OK)
                        : new ResponseEntity(new ErrorDescriptor().setStatus(HttpStatus.NOT_FOUND).setMessage("Not find teacher with id  " + uuid),
                                HttpStatus.NOT_FOUND);
    }
}

