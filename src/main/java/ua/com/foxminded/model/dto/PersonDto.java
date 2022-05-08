package ua.com.foxminded.model.dto;

import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public abstract class PersonDto {

    private UUID idPerson;
    @NotBlank
    @Size(min=2, max=40)
    private String firstName;
    @NotBlank
    @Size(min=2, max=40)
    private String lastName;
    @NotNull
    @Valid
    private ContactInfoDto contactInfo;
	
    public PersonDto() {
    }

    public PersonDto(PersonDto person) {
        this.firstName = person.firstName;
        this.lastName = person.lastName;
        this.contactInfo = new ContactInfoDto(person.getContactInfo());
    }

    public UUID getIdPerson() {
        return idPerson;
    }

    public PersonDto setIdPerson(UUID idPerson) {
        this.idPerson = idPerson;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public PersonDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public PersonDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ContactInfoDto getContactInfo() {
        return contactInfo;
    }

    public PersonDto setContactInfo(ContactInfoDto contactInfo) {
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
        PersonDto other = (PersonDto) obj;
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

    @Override
    public String toString() {
        return "PersonDto [idPerson=" + idPerson.toString() + ", firstName=" + firstName + ", lastName=" + lastName
                + ", contactInfo=" + contactInfo + "]";
    }
}
