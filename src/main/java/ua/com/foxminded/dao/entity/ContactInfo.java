package ua.com.foxminded.dao.entity;

import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table (name = "contact_infos", schema = "uni")
public class ContactInfo {

    @Id
    @Column(name = "id_cont_info")
    private String id;
    @OneToOne()
    @JoinColumn(name = "id_person")  
    private Person person;
	private int index;
	private String country;
	private String city;
	private String street;
	private String house;
	private int apartment;
    private String phone1;
	private String phone2;
	private String email;
	
    public ContactInfo() {
       
    }

    public String getId() {
        return id;
    }

    public ContactInfo setId(String id) {
        this.id = id;
        return this;
    }

    public int getIndex() {
        return index;
    }

    public ContactInfo setIndex(int index) {
        this.index = index;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public ContactInfo setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getCity() {
        return city;
    }

    public ContactInfo setCity(String city) {
        this.city = city;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public ContactInfo setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getHouse() {
        return house;
    }

    public ContactInfo setHouse(String house) {
        this.house = house;
        return this;
    }

    public int getApartment() {
        return apartment;
    }

    public ContactInfo setApartment(int apartment) {
        this.apartment = apartment;
        return this;
    }

    public String getPhone1() {
        return phone1;
    }

    public ContactInfo setPhone1(String phone1) {
        this.phone1 = phone1;
        return this;
    }

    public String getPhone2() {
        return phone2;
    }

    public ContactInfo setPhone2(String phone2) {
        this.phone2 = phone2;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ContactInfo setEmail(String email) {
        this.email = email;
        return this;
    }

    public Person getPerson() {
        return person;
    }

    public ContactInfo setPerson(Person person) {
        this.person = person;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactInfo)) return false;

        ContactInfo that = (ContactInfo) o;

        if (getIndex() != that.getIndex()) return false;
        if (getApartment() != that.getApartment()) return false;
        if (getPerson() != null ? !getPerson().equals(that.getPerson()) : that.getPerson() != null) return false;
        if (getCountry() != null ? !getCountry().equals(that.getCountry()) : that.getCountry() != null) return false;
        if (getCity() != null ? !getCity().equals(that.getCity()) : that.getCity() != null) return false;
        if (getStreet() != null ? !getStreet().equals(that.getStreet()) : that.getStreet() != null) return false;
        if (getHouse() != null ? !getHouse().equals(that.getHouse()) : that.getHouse() != null) return false;
        if (getPhone1() != null ? !getPhone1().equals(that.getPhone1()) : that.getPhone1() != null) return false;
        if (getPhone2() != null ? !getPhone2().equals(that.getPhone2()) : that.getPhone2() != null) return false;
        return getEmail() != null ? getEmail().equals(that.getEmail()) : that.getEmail() == null;
    }

    @Override
    public int hashCode() {
        int result = getPerson() != null ? getPerson().hashCode() : 0;
        result = 31 * result + getIndex();
        result = 31 * result + (getCountry() != null ? getCountry().hashCode() : 0);
        result = 31 * result + (getCity() != null ? getCity().hashCode() : 0);
        result = 31 * result + (getStreet() != null ? getStreet().hashCode() : 0);
        result = 31 * result + (getHouse() != null ? getHouse().hashCode() : 0);
        result = 31 * result + getApartment();
        result = 31 * result + (getPhone1() != null ? getPhone1().hashCode() : 0);
        result = 31 * result + (getPhone2() != null ? getPhone2().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        return result;
    }
}
