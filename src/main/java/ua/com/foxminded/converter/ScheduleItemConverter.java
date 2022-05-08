package ua.com.foxminded.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import ua.com.foxminded.dao.entity.Group;
import ua.com.foxminded.dao.entity.Room;
import ua.com.foxminded.dao.entity.ScheduleItem;
import ua.com.foxminded.dao.entity.Student;
import ua.com.foxminded.dao.entity.Subject;
import ua.com.foxminded.dao.entity.Teacher;
import ua.com.foxminded.dao.entity.TimeSlot;
import ua.com.foxminded.model.dto.GroupDto;
import ua.com.foxminded.model.dto.RoomDto;
import ua.com.foxminded.model.dto.ScheduleItemDto;
import ua.com.foxminded.model.dto.SubjectDto;
import ua.com.foxminded.model.dto.TimeSlotDto;
import ua.com.foxminded.model.enums.DayOfWeek;

@Component
public class ScheduleItemConverter {
    
public   ScheduleItem convertDtoToEntity(ScheduleItemDto scheduleItemDto) {

    List<Student> students = new ArrayList<>();

    Group group = new Group();
    if (scheduleItemDto.getGroup() != null) {
        Optional.ofNullable(scheduleItemDto.getGroup().getStudents())
                .ifPresent(st -> st.forEach(studentDto ->
        {
            Student student = new Student();
            Optional.ofNullable(studentDto.getIdPerson())
                    .ifPresent(ss -> student.setIdPerson(ss.toString()));
            student.setFirstName(studentDto.getFirstName())
                    .setLastName(studentDto.getLastName());
            students.add(student);
        }));


        Optional.ofNullable(scheduleItemDto.getGroup().getId())
                .ifPresent(ss -> group.setId(ss.toString()));
        group.setName(scheduleItemDto.getGroup().getName())
                .setStudents(students);
    }

    Room room = new Room();
    if (scheduleItemDto.getRoom() != null) {
        Optional.ofNullable(scheduleItemDto.getRoom().getId())
                .ifPresent(ss -> room.setId(ss.toString()));
        room.setName(scheduleItemDto.getRoom().getName());
    }

    Subject subject = new Subject();

    if (scheduleItemDto.getSubject() != null) {
        Optional.ofNullable(scheduleItemDto.getSubject().getId())
                .ifPresent(ss -> subject.setId(ss.toString()));
        subject.setName(scheduleItemDto.getSubject().getName());
    }

    TimeSlot timeSlot = new TimeSlot();

    if (scheduleItemDto.getTimeSlot() != null) {
        Optional.ofNullable(scheduleItemDto.getTimeSlot().getId())
                .ifPresent(ss -> timeSlot.setId(ss.toString()));
        timeSlot.setSerialNumber(scheduleItemDto.getTimeSlot()
                .getSerialNumber())
                .setStartTime(scheduleItemDto.getTimeSlot()
                        .getStartTime())
                .setFinishTime(scheduleItemDto.getTimeSlot()
                        .getFinishTime());
    }


    Teacher teacher = new Teacher();
    if (scheduleItemDto.getTeacher() != null) {
    Optional.ofNullable(scheduleItemDto.getTeacher())
            .ifPresent(ss -> teacher.setIdPerson(ss.getIdPerson().toString()));

    teacher.setFirstName(scheduleItemDto.getTeacher().getFirstName())
            .setLastName(scheduleItemDto.getTeacher().getLastName());
    }
            ScheduleItem scheduleItem = new ScheduleItem();
            
            Optional.ofNullable(scheduleItemDto.getId())
            .ifPresent(ss -> scheduleItem.setId(ss.toString()));
            
            Optional.ofNullable(scheduleItemDto.getDayOfWeek())
            .ifPresent(ss -> scheduleItem.setDayOfWeek(ss.name()));
            scheduleItem.setGroup(group)
                        .setRoom(room)
                        .setSubject(subject)
                        .setTimeSlot(timeSlot)
                        .setTeacher(teacher);
        
        return scheduleItem;
    }

    public   ScheduleItemDto convertEntityToDto(ScheduleItem scheduleItem) {

        ScheduleItemDto scheduleItemDto = new ScheduleItemDto();

        Optional.ofNullable(scheduleItem.getDayOfWeek())
                .ifPresent(ss -> scheduleItemDto.setDayOfWeek(DayOfWeek.valueOf(ss)));
        scheduleItemDto.setGroup(
                new GroupDto().setName(scheduleItem.getGroup().getName()));
        scheduleItemDto.setRoom(
                new RoomDto().setName(scheduleItem.getRoom().getName()));
        scheduleItemDto.setSubject(
                new SubjectDto().setName(scheduleItem.getSubject().getName()));
        scheduleItemDto.setTimeSlot(new TimeSlotDto().setSerialNumber(
                scheduleItem.getTimeSlot().getSerialNumber())
                .setStartTime(scheduleItem.getTimeSlot().getStartTime()));

        return scheduleItemDto;
    }

}
