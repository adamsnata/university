package ua.com.foxminded.dao.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "periods", schema = "uni")
public class Period {

    @Id
    @Column(name = "id_period", nullable = false)
    private String id;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "finish_date")
    private LocalDate finishDate;
    
    public Period() {
       
    }

    public String getId() {
        return id;
    }

    public Period setId(String id) {
        this.id = id;
        return this;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Period setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public Period setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
        return this;
    }
}
