package ua.com.foxminded.model.dto;

import java.time.LocalDate;
import java.util.UUID;

public class PeriodDto {

    private UUID id;
    private LocalDate startDate;
    private LocalDate finishDate;
    
    public PeriodDto() {
        
    }

    public PeriodDto(PeriodDto periodDto) {
        this.startDate = periodDto.startDate;
        this.finishDate = periodDto.finishDate;
    }

    public UUID getId() {
        return id;
    }

    public PeriodDto setId(UUID id) {
        this.id = id;
        return this; 
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public PeriodDto setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public PeriodDto setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((finishDate == null) ? 0 : finishDate.hashCode());
        result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
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
        PeriodDto other = (PeriodDto) obj;
        if (finishDate == null) {
            if (other.finishDate != null)
                return false;
        } else if (!finishDate.equals(other.finishDate))
            return false;
        if (startDate == null) {
            if (other.startDate != null)
                return false;
        } else if (!startDate.equals(other.startDate))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PeriodDto [id=" + id + ", startDate=" + startDate + ", finishDate=" + finishDate + "]";
    }      
}
