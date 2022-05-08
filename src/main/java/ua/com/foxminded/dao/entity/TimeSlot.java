package ua.com.foxminded.dao.entity;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "time_slots", schema = "uni")
public class TimeSlot {
    
    @Id
    @Column(name = "id_time_slot", nullable = false)
    private String id;
    @Column(name = "serial_number")
    private int serialNumber;
    @Column(name = "start_time")
    private LocalTime startTime;
    @Column(name = "finish_time")
    private LocalTime finishTime;
    
    public TimeSlot() {
    
    }

    public String getId() {
        return id;
    }

    public TimeSlot setId(String id) {
        this.id = id;
        return this;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public TimeSlot setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public TimeSlot setStartTime(LocalTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public LocalTime getFinishTime() {
        return finishTime;
    }

    public TimeSlot setFinishTime(LocalTime finishTime) {
        this.finishTime = finishTime;
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((finishTime == null) ? 0 : finishTime.hashCode());
        result = prime * result + serialNumber;
        result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
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
        TimeSlot other = (TimeSlot) obj;
        if (finishTime == null) {
            if (other.finishTime != null)
                return false;
        } else if (!finishTime.equals(other.finishTime))
            return false;
        if (serialNumber != other.serialNumber)
            return false;
        if (startTime == null) {
            if (other.startTime != null)
                return false;
        } else if (!startTime.equals(other.startTime))
            return false;
        return true;
    }
}
