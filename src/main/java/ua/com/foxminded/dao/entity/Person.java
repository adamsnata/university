package ua.com.foxminded.dao.entity;

import javax.persistence.*;

@Entity
@Table (name = "persons", schema = "uni")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Person {
    
    @Id
    @Column(name = "id_person", nullable = false)
	private String idPerson;
    @Column(name = "first_name")
	private String firstName;
    @Column(name = "last_name")
	private String lastName;
    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
	private ContactInfo contactInfo;
	
    public Person() {
        
    }

    public String getIdPerson() {
        return idPerson;
    }

    public Person setIdPerson(String idPerson) {
        this.idPerson = idPerson;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Person setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Person setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public Person setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((contactInfo == null) ? 0 : contactInfo.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
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
        Person other = (Person) obj;
        if (contactInfo == null) {
            if (other.contactInfo != null)
                return false;
        } else if (!contactInfo.equals(other.contactInfo))
            return false;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        return true;
    }
    
}
