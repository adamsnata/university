package ua.com.foxminded.service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ua.com.foxminded.converter.ScheduleItemConverter;
import ua.com.foxminded.dao.entity.Student;
import ua.com.foxminded.dao.interfaces.ScheduleItemDao;
import ua.com.foxminded.dao.interfaces.StudentDao;
import ua.com.foxminded.model.dto.ScheduleItemDto;
import ua.com.foxminded.service.interfaces.ScheduleItemService;

@Service("scheduleItemService")
public class ScheduleItemServiceImpl implements ScheduleItemService {

    @Autowired
    @Qualifier("scheduleItemDao")
    ScheduleItemDao scheduleItemDao;

    @Autowired
    @Qualifier("studentDao")
    StudentDao studentDao;
    
    @Autowired
    ScheduleItemConverter scheduleItemConverter;

    @Override
    public Boolean addScheduleItem(ScheduleItemDto scheduleItemDto) {
        if (scheduleItemDto == null){
            return false;
        }
        scheduleItemDao.save(scheduleItemConverter.convertDtoToEntity(scheduleItemDto));
        return true;
    }

    @Override
    public Boolean editScheduleItem(ScheduleItemDto scheduleItemDto) {
        if (scheduleItemDto == null){
            return false;
        }
        scheduleItemDao.save(scheduleItemConverter.convertDtoToEntity(scheduleItemDto));
        return true;
    }

    @Override
    public Boolean deleteScheduleItem(UUID id) {
        if (id == null){
            return false;
        }
        scheduleItemDao.deleteById(id.toString());
        return true;
    }
    
    @Override
    public List<ScheduleItemDto> findWeekScheduleStudent(UUID id) {
        List<ScheduleItemDto> schedule = new ArrayList<>();
        Student student = studentDao.findById(id.toString()).orElse(new Student());
        if (student.equals(new Student()) || student.getGroup() == null)
        {
            return schedule;
        }
        String idGroup = student.getGroup().getId();

        scheduleItemDao.findByGroupIdGroup(idGroup).forEach(sch -> {
            schedule.add(scheduleItemConverter.convertEntityToDto(sch));
        });
        return schedule;
      }

    @Override
    public List<ScheduleItemDto> findDayScheduleStudent(UUID id, LocalDate date) {
        List<ScheduleItemDto> ScheduleItems = findWeekScheduleStudent(id);
        String dayOfWeek = date.getDayOfWeek().toString();
        List<ScheduleItemDto> ScheduleItemsDay = ScheduleItems.stream()
                                                              .filter(p -> p.getDayOfWeek()
                                                                            .toString()
                                                                            .equals(dayOfWeek))
                                                              .collect(Collectors.toList());
        return ScheduleItemsDay;
    }

    @Override
    public Map<String, List<ScheduleItemDto>> findMonthScheduleStudent(UUID id, LocalDate date) {
        List<ScheduleItemDto> ScheduleItems = findWeekScheduleStudent(id);
        LocalDate dateStart = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate dateFinish = dateStart.plusMonths(1);

        Map<String, List<ScheduleItemDto>> scheduleMonthStudent = new LinkedHashMap<>();
        for (LocalDate dateN = dateStart; dateN.isBefore(dateFinish); dateN = dateN.plusDays(1)) {
            String dayOfWeek = dateN.getDayOfWeek().toString();
            List<ScheduleItemDto> scheduleDayOfWeek = ScheduleItems.stream()
                                                                   .filter(e -> e.getDayOfWeek()
                                                                                 .toString()
                                                                                 .equals(dayOfWeek))
                                                                   .collect(Collectors.toList());
            scheduleMonthStudent.put(dateN.toString(), scheduleDayOfWeek);
        }
        return scheduleMonthStudent;
    }

    @Override
    public List<ScheduleItemDto> findWeekScheduleTeacher(UUID id) {
        List<ScheduleItemDto> schedule = new ArrayList<>();
        if (id == null){
            return schedule;
        }
        scheduleItemDao.findByTeacherIdPerson(id.toString()).forEach(sch -> {
            schedule.add(scheduleItemConverter.convertEntityToDto(sch));
        });
        return schedule;  
    }
    
    @Override
    public Map<String, List<ScheduleItemDto>> findMonthScheduleTeacher(UUID id, LocalDate date) {
        List<ScheduleItemDto> ScheduleItems = findWeekScheduleTeacher(id);
        LocalDate dateStart = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate dateFinish = dateStart.plusMonths(1);

        Map<String, List<ScheduleItemDto>> scheduleMonthTeacher = new LinkedHashMap<>();
        for (LocalDate dateN = dateStart; dateN.isBefore(dateFinish); dateN = dateN.plusDays(1)) {
            String dayOfWeek = dateN.getDayOfWeek().toString();
            List<ScheduleItemDto> scheduleDayOfWeek = ScheduleItems.stream()
                                                                   .filter(e -> e.getDayOfWeek()
                                                                                 .toString()
                                                                                 .equals(dayOfWeek))
                                                                   .collect(Collectors.toList());
            if (scheduleDayOfWeek.size() > 0) {
                scheduleMonthTeacher.put(dateN.toString(), scheduleDayOfWeek);
            }
        }
        return scheduleMonthTeacher;
    }

    @Override
    public List<ScheduleItemDto> findDayScheduleTeacher(UUID id, LocalDate date) {
        List<ScheduleItemDto> ScheduleItems = findWeekScheduleTeacher(id);
        String dayOfWeek = date.getDayOfWeek().toString();
        List<ScheduleItemDto> ScheduleItemsDay = ScheduleItems.stream()
                                                              .filter(p -> p.getDayOfWeek()
                                                                            .toString()
                                                                            .equals(dayOfWeek))
                                                              .collect(Collectors.toList());
        return ScheduleItemsDay;
    }
}
