package ua.com.foxminded.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ua.com.foxminded.model.enums.StudyStatus;
import ua.com.foxminded.model.validation.NotPastDate;

@ApiModel("Student")
public class StudentDto extends PersonDto {

    @NotNull
    private StudyStatus studyStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull
    @NotPastDate
    private LocalDate startOfStudy;
    @NotBlank
    @ApiModelProperty(notes = "citizenship", required = true, example = "Ukraine")
    private String citizenship;
    @PositiveOrZero
    private BigDecimal grant;
    private GroupDto group;
    
    public StudentDto() {
    }

    public StudentDto(StudentDto student) {
        this.setFirstName(student.getFirstName());
        this.setLastName(student.getLastName());
        this.studyStatus = student.studyStatus;
        this.startOfStudy =  LocalDate.of(student.getStartOfStudy().getYear(), student.getStartOfStudy().getMonth(),
                student.getStartOfStudy().getDayOfMonth()) ;
        this.citizenship = student.citizenship;
        this.grant = BigDecimal.valueOf(student.getGrant().longValue());
    }

    public GroupDto getGroup() {
        return group;
    }

    public StudentDto setGroup(GroupDto group) {
        this.group = group;
        return this;
    }

    public StudyStatus getStudyStatus() {
        return studyStatus;
    }

    public StudentDto setStudyStatus(StudyStatus studyStatus) {
        this.studyStatus = studyStatus;
        return this;
    }

    public LocalDate getStartOfStudy() {
        return startOfStudy;
    }

    public StudentDto setStartOfStudy(LocalDate startOfStudy) {
        this.startOfStudy = startOfStudy;
        return this;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public StudentDto setCitizenship(String citizenship) {
        this.citizenship = citizenship;
        return this;
    }

    public BigDecimal getGrant() {
        return grant;
    }

    public StudentDto setGrant(BigDecimal grant) {
        this.grant = grant;
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((citizenship == null) ? 0 : citizenship.hashCode());
        result = prime * result + ((grant == null) ? 0 : grant.hashCode());
        result = prime * result + ((startOfStudy == null) ? 0 : startOfStudy.hashCode());
        result = prime * result + ((studyStatus == null) ? 0 : studyStatus.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        StudentDto other = (StudentDto) obj;
        if (citizenship == null) {
            if (other.citizenship != null)
                return false;
        } else if (!citizenship.equals(other.citizenship))
            return false;
        if (grant == null) {
            if (other.grant != null)
                return false;
        } else if (!grant.equals(other.grant))
            return false;
        if (startOfStudy == null) {
            if (other.startOfStudy != null)
                return false;
        } else if (!startOfStudy.equals(other.startOfStudy))
            return false;
        if (studyStatus != other.studyStatus)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "first name  : " + getFirstName()
                + ", last name" + getLastName()
                + ", studyStatus : " + studyStatus 
                + ", startOfStudy=" + startOfStudy
                + ", citizenship=" + citizenship + ", grant=" + grant ;
    }

   
}
