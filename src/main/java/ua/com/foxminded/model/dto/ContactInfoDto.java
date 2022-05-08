package ua.com.foxminded.model.dto;

import java.util.UUID;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

public class ContactInfoDto {

    private UUID id;
    @Positive
    @Min(2)
    private int index;
    @NotBlank
    private String country;
    @NotBlank
    private String city;
    @NotBlank
    private String street;
    @NotBlank
    private String house;
    @Positive
    private int apartment;
    @NotBlank
    @Pattern(regexp = "^\\+?(?:[0-9] ?){6,14}[0-9]$")
    private String phone1;
    @NotBlank
    @Pattern(regexp = "^\\+?(?:[0-9] ?){6,14}[0-9]$")
    private String phone2;
    @NotBlank
    @Email
    private String email;
	
    public ContactInfoDto() {
       
    }

    public ContactInfoDto(ContactInfoDto contactInfo) {
        this.index = contactInfo.index;
        this.country = contactInfo.country;
        this.city = contactInfo.city;
        this.street = contactInfo.street;
        this.house = contactInfo.house;
        this.apartment = contactInfo.apartment;
        this.phone1 = contactInfo.phone1;
        this.phone2 = contactInfo.phone2;
        this.email = contactInfo.email;
    }

    public UUID getId() {
        return id;
    }

    public ContactInfoDto setId(UUID id) {
        this.id = id;
        return this;
    }

    public int getIndex() {
        return index;
    }

    public ContactInfoDto setIndex(int index) {
        this.index = index;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public ContactInfoDto setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getCity() {
        return city;
    }

    public ContactInfoDto setCity(String city) {
        this.city = city;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public ContactInfoDto setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getHouse() {
        return house;
    }

    public ContactInfoDto setHouse(String house) {
        this.house = house;
        return this;
    }

    public int getApartment() {
        return apartment;
    }

    public ContactInfoDto setApartment(int apartment) {
        this.apartment = apartment;
        return this;
    }

    public String getPhone1() {
        return phone1;
    }

    public ContactInfoDto setPhone1(String phone1) {
        this.phone1 = phone1;
        return this;
    }

    public String getPhone2() {
        return phone2;
    }

    public ContactInfoDto setPhone2(String phone2) {
        this.phone2 = phone2;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ContactInfoDto setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + apartment;
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((house == null) ? 0 : house.hashCode());
        result = prime * result + index;
        result = prime * result + ((phone1 == null) ? 0 : phone1.hashCode());
        result = prime * result + ((phone2 == null) ? 0 : phone2.hashCode());
        result = prime * result + ((street == null) ? 0 : street.hashCode());
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
        ContactInfoDto other = (ContactInfoDto) obj;
        if (apartment != other.apartment)
            return false;
        if (city == null) {
            if (other.city != null)
                return false;
        } else if (!city.equals(other.city))
            return false;
        if (country == null) {
            if (other.country != null)
                return false;
        } else if (!country.equals(other.country))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (house == null) {
            if (other.house != null)
                return false;
        } else if (!house.equals(other.house))
            return false;
        if (index != other.index)
            return false;
        if (phone1 == null) {
            if (other.phone1 != null)
                return false;
        } else if (!phone1.equals(other.phone1))
            return false;
        if (phone2 == null) {
            if (other.phone2 != null)
                return false;
        } else if (!phone2.equals(other.phone2))
            return false;
        if (street == null) {
            if (other.street != null)
                return false;
        } else if (!street.equals(other.street))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ContactInfo [index=" + index + ", country=" + country + ", city="
                + city + ", street=" + street
                + ", house=" + house + ", apartment=" + apartment + ", phone1="
                + phone1 + ", phone2=" + phone2
                + ", email=" + email + "]";
    }
}
