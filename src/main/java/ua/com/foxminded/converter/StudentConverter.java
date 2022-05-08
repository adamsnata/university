package ua.com.foxminded.converter;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import ua.com.foxminded.dao.entity.ContactInfo;
import ua.com.foxminded.dao.entity.Group;
import ua.com.foxminded.dao.entity.Person;
import ua.com.foxminded.dao.entity.Student;
import ua.com.foxminded.model.dto.ContactInfoDto;
import ua.com.foxminded.model.dto.GroupDto;
import ua.com.foxminded.model.dto.StudentDto;
import ua.com.foxminded.model.enums.StudyStatus;

@Component
public class StudentConverter {
   
    public Student convertDtoToEntity(StudentDto studentDto) {    
        
        Student student = new Student();
        Optional.ofNullable(studentDto.getGroup())  
        .ifPresent(gr ->   {
            Group group = new Group();
            Optional.ofNullable(gr.getId())
                    .ifPresent(grI -> group.setId(grI.toString()));
            group.setName(gr.getName());
            student.setGroup(group);
        });
        Optional.ofNullable(studentDto.getIdPerson())
        .ifPresent(ss -> student.setIdPerson(ss.toString()));        
         student.setFirstName(studentDto.getFirstName())
                        .setLastName(studentDto.getLastName());
        student.setCitizenship(studentDto.getCitizenship())
        .setGrant(studentDto.getGrant())
        .setStartOfStudy(studentDto.getStartOfStudy());
        Optional.ofNullable(studentDto.getStudyStatus())  
        .ifPresent(ss -> student.setStudyStatus(ss.name()));   
      
        ContactInfoDto contactInfoDto = studentDto.getContactInfo();
        ContactInfo contactInfo = new ContactInfo();
        Optional.ofNullable(contactInfoDto)
                .ifPresent( contactInfoDto1 -> {
                            contactInfo
                                    .setIndex(contactInfoDto.getIndex())
                                    .setCountry(contactInfoDto.getCountry())
                                    .setCity(contactInfoDto.getCity())
                                    .setStreet(contactInfoDto.getStreet())
                                    .setHouse(contactInfoDto.getHouse())
                                    .setApartment(contactInfoDto.getApartment())
                                    .setPhone1(contactInfoDto.getPhone1())
                                    .setPhone2(contactInfoDto.getPhone2())
                                    .setEmail(contactInfoDto.getEmail());
                            Optional.ofNullable(contactInfoDto.getId())
                                    .ifPresent(ci -> contactInfo.setId(ci.toString()));
                        });
        student.setContactInfo(contactInfo);
      return   student;
        
    }

    public StudentDto convertEntityToDto(Student student) {
        StudentDto  studentDto = new StudentDto();
        if (student.getIdPerson() == null) {
            return studentDto;
        }
        ((StudentDto) studentDto
                .setIdPerson(UUID.fromString(student.getIdPerson()))
                .setFirstName(student.getFirstName())
                .setLastName(student.getLastName()))  
        .setCitizenship(student.getCitizenship())
        .setGrant(student.getGrant())
        .setStartOfStudy(student.getStartOfStudy());
        Optional.ofNullable(student.getStudyStatus())
        .ifPresent(ss -> studentDto.setStudyStatus(StudyStatus.valueOf(ss)));  
        
        Optional.ofNullable(student.getGroup())
        .ifPresent(gr -> studentDto.setGroup(new GroupDto().setId(UUID.fromString(gr.getId()))
                .setName(gr.getName())));
        
        ContactInfo contactInfo = student.getContactInfo();
        ContactInfoDto contactInfoDto = new ContactInfoDto();
        if (contactInfo != null) {
            contactInfoDto
                    .setIndex(contactInfo.getIndex())
                    .setCountry(contactInfo.getCountry())
                    .setCity(contactInfo.getCity())
                    .setStreet(contactInfo.getStreet())
                    .setHouse(contactInfo.getHouse())
                    .setApartment(contactInfo.getApartment())
                    .setPhone1(contactInfo.getPhone1())
                    .setPhone2(contactInfo.getPhone2())
                    .setEmail(contactInfo.getEmail())
                    .setId(UUID.fromString(contactInfo.getId()));    
        }
        studentDto.setContactInfo(contactInfoDto);        
        return studentDto;
    }
}