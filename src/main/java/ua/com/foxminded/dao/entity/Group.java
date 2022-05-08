package ua.com.foxminded.dao.entity;

import java.util.List;

import javax.persistence.*;

@Entity
@Table (name = "groups" , schema = "uni")
public class Group {
   
    @Id
    @Column(name = "id_group", nullable = false)
    private String idGroup;
    @Column(name = "name_group")
    private String name;
    private String specialty;
    @OneToMany(mappedBy = "group")
    private List<Student> students;
    
    public Group() {
      
    }

    public String getId() {
        return idGroup;
    }

    public Group setId(String id) {
        this.idGroup = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Group setName(String name) {
        this.name = name;
        return this;
    }

    public String getSpecialty() {
        return specialty;
    }

    public Group setSpecialty(String specialty) {
        this.specialty = specialty;
        return this;
    }

    public List<Student> getStudents() {
        return students;
    }

    public Group setStudents(List<Student> students) {
        this.students = students;
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((specialty == null) ? 0 : specialty.hashCode());
        result = prime * result + ((students == null) ? 0 : students.hashCode());
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
        Group other = (Group) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (specialty == null) {
            if (other.specialty != null)
                return false;
        } else if (!specialty.equals(other.specialty))
            return false;
        if (students == null) {
            if (other.students != null)
                return false;
        } else if (!students.equals(other.students))
            return false;
        return true;
    }
}
