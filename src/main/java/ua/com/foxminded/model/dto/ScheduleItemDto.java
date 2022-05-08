package ua.com.foxminded.model.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import ua.com.foxminded.model.enums.DayOfWeek;

public class ScheduleItemDto {
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UUID id;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private TeacherDto teacher;
    private GroupDto group;
    private SubjectDto subject;
    private DayOfWeek dayOfWeek;
    private TimeSlotDto timeSlot;
    private RoomDto room;
    
    public ScheduleItemDto() {
        
    }

    public ScheduleItemDto(ScheduleItemDto scheduleItem) {
        this.group = new GroupDto(scheduleItem.getGroup()) ;
        this.subject = new SubjectDto(scheduleItem.getSubject());
        this.dayOfWeek = scheduleItem.dayOfWeek;
        this.timeSlot = new TimeSlotDto(scheduleItem.getTimeSlot());
        this.room = new RoomDto(scheduleItem.getRoom());
    }

    public UUID getId() {
        return id;
    }

    public ScheduleItemDto setId(UUID id) {
        this.id = id;
        return this;
    }

    public TeacherDto getTeacher() {
        return teacher;
    }

    public ScheduleItemDto setTeacher(TeacherDto teacher) {
        this.teacher = teacher;
        return this;
    }

    public GroupDto getGroup() {
        return group;
    }

    public ScheduleItemDto setGroup(GroupDto group) {
        this.group = group;
        return this;
    }

    public SubjectDto getSubject() {
        return subject;
    }

    public ScheduleItemDto setSubject(SubjectDto subject) {
        this.subject = subject;
        return this;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public ScheduleItemDto setDayOfWeek(DayOfWeek dayOWeek) {
        this.dayOfWeek = dayOWeek;
        return this;
    }

    public TimeSlotDto getTimeSlot() {
        return timeSlot;
    }

    public ScheduleItemDto setTimeSlot(TimeSlotDto timeSlot) {
        this.timeSlot = timeSlot;
        return this;
    }

    public RoomDto getRoom() {
        return room;
    }

    public ScheduleItemDto setRoom(RoomDto room) {
        this.room = room;
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dayOfWeek == null) ? 0 : dayOfWeek.hashCode());
        result = prime * result + ((group == null) ? 0 : group.hashCode());
        result = prime * result + ((room == null) ? 0 : room.hashCode());
        result = prime * result + ((subject == null) ? 0 : subject.hashCode());
        result = prime * result + ((teacher == null) ? 0 : teacher.hashCode());
        result = prime * result + ((timeSlot == null) ? 0 : timeSlot.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ScheduleItemDto other = (ScheduleItemDto) obj;
        if (dayOfWeek != other.dayOfWeek)
            return false;
        if (group == null) {
            if (other.group != null)
                return false;
        } else if (!group.equals(other.group))
            return false;
        if (room == null) {
            if (other.room != null)
                return false;
        } else if (!room.equals(other.room))
            return false;
        if (subject == null) {
            if (other.subject != null)
                return false;
        } else if (!subject.equals(other.subject))
            return false;
        if (teacher == null) {
            if (other.teacher != null)
                return false;
        } else if (!teacher.equals(other.teacher))
            return false;
        if (timeSlot == null) {
            if (other.timeSlot != null)
                return false;
        } else if (!timeSlot.equals(other.timeSlot))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return String.format("                 %0$-10s %0$-20s %0$-30s %s%n",
                dayOfWeek, timeSlot.getSerialNumber(), subject.getName(), group.getName(), room.getName());
    }
}
