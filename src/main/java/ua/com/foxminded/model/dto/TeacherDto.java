package ua.com.foxminded.model.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import ua.com.foxminded.model.enums.Degree;
import ua.com.foxminded.model.enums.Department;

public class TeacherDto extends PersonDto{
    
    private Degree degree;
    @NotNull
    private Department department;
    private boolean permanent;
    @PositiveOrZero
    private BigDecimal salary;
   
    public TeacherDto() {
     
    }

    public TeacherDto(TeacherDto teacher) {
        this.degree = teacher.degree;
        this.department = teacher.department;
        this.permanent = teacher.permanent;
        this.salary = BigDecimal.valueOf(teacher.getSalary().longValue());
    }

    public Degree getDegree() {
        return degree;
    }

    public TeacherDto setDegree(Degree degree) {
        this.degree = degree;
        return this;
    }

    public Department getDepartment() {
        return department;
    }

    public TeacherDto setDepartment(Department department) {
        this.department = department;
        return this;
    }

    public boolean isPermanent() {
        return permanent;
    }

    public TeacherDto setPermanent(boolean permanent) {
        this.permanent = permanent;
        return this;
    }
   

    public BigDecimal getSalary() {
        return salary;
    }

    public TeacherDto setSalary(BigDecimal salary) {
        this.salary = salary;
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((degree == null) ? 0 : degree.hashCode());
        result = prime * result + ((department == null) ? 0 : department.hashCode());
        result = prime * result + (permanent ? 1231 : 1237);
        result = prime * result + ((salary == null) ? 0 : salary.hashCode());
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
        TeacherDto other = (TeacherDto) obj;
        if (degree != other.degree)
            return false;
        if (department != other.department)
            return false;
        if (permanent != other.permanent)
            return false;
        if (salary == null) {
            if (other.salary != null)
                return false;
        } else if (!salary.equals(other.salary))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TeacherDto [ degree=" + degree + ", department=" + department
                + ", permanent=" + permanent + ", salary=" + salary + ", getIdPerson()=" + getIdPerson()
                + ", getFirstName()=" + getFirstName() + ", getLastName()=" + getLastName() + "]";
    }

    
    
}
