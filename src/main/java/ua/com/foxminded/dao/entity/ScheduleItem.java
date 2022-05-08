package ua.com.foxminded.dao.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Table (name = "schedule_items", schema = "uni")
public class ScheduleItem {
    
    @Id
    private String id;
    @ManyToOne
    @JoinColumn(name = "id_group")
    private Group group;
    @ManyToOne
    @JoinColumn(name = "id_subject")
    private Subject subject;
    @Column(name = "day_of_week")
    private String dayOfWeek;
    @ManyToOne
    @JoinColumn(name = "id_time_slot")
    private TimeSlot timeSlot;
    @ManyToOne
    @JoinColumn(name = "id_room")
    private Room room;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_person" )
    private Teacher teacher;
    
    public ScheduleItem() {
        
    }

    public String getId() {
        return id;
    }

    public ScheduleItem setId(String id) {
        this.id = id;
        return this;
    }

    public Group getGroup() {
        return group;
    }

    public ScheduleItem setGroup(Group group) {
        this.group = group;
        return this;
    }

    public Subject getSubject() {
        return subject;
    }

    public ScheduleItem setSubject(Subject subject) {
        this.subject = subject;
        return this;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public ScheduleItem setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
        return this;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public ScheduleItem setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
        return this;
    }

    public Room getRoom() {
        return room;
    }

    public ScheduleItem setRoom(Room room) {
        this.room = room;
        return this;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public ScheduleItem setTeacher(Teacher teacher) {
        this.teacher = teacher;
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
        ScheduleItem other = (ScheduleItem) obj;
        if (dayOfWeek == null) {
            if (other.dayOfWeek != null)
                return false;
        } else if (!dayOfWeek.equals(other.dayOfWeek))
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

  
    
}
