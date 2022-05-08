package ua.com.foxminded.model.dto;

import java.time.LocalTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TimeSlotDto {
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UUID id;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int serialNumber;
    private LocalTime startTime;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalTime finishTime;
    
    public TimeSlotDto() {
    
    }

    public TimeSlotDto(TimeSlotDto timeSlot) {
        this.serialNumber = timeSlot.serialNumber;
        this.startTime = LocalTime.of(timeSlot.getStartTime().getHour(), timeSlot.getStartTime().getMinute());
        this.finishTime = LocalTime.of(timeSlot.getFinishTime().getHour(), timeSlot.getFinishTime().getMinute());  
    }

    public UUID getId() {
        return id;
    }

    public TimeSlotDto setId(UUID id) {
        this.id = id;
        return this;
    }
    
    public int getSerialNumber() {
        return serialNumber;
    }

    public TimeSlotDto setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public TimeSlotDto setStartTime(LocalTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public LocalTime getFinishTime() {
        return finishTime;
    }

    public TimeSlotDto setFinishTime(LocalTime finishTime) {
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
        TimeSlotDto other = (TimeSlotDto) obj;
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

    @Override
    public String toString() {
        return "TimeSlotDto [id=" + id + ", serialNumber=" + serialNumber 
                + ", startTime=" + startTime + ", finishTime="
                + finishTime + "]";
    } 
}
