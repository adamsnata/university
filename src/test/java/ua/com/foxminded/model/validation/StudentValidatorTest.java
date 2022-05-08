package ua.com.foxminded.model.validation;

import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.dao.entity.ContactInfo;
import ua.com.foxminded.dao.entity.Group;
import ua.com.foxminded.dao.entity.Student;
import ua.com.foxminded.model.dto.ContactInfoDto;
import ua.com.foxminded.model.dto.GroupDto;
import ua.com.foxminded.model.dto.StudentDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import ua.com.foxminded.model.dto.TeacherDto;
import ua.com.foxminded.model.enums.StudyStatus;

import javax.validation.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class StudentValidatorTest {

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private StudentDto studentDto;

    String uuidPerson = "d4d8b36a-4435-11eb-b378-0242ac130002";
    String uuidContactInfo = "2a6e5b4a-4436-11eb-b378-0242ac130002";

    @BeforeEach
    void initEach() {

        studentDto = ((StudentDto) new StudentDto().setIdPerson(UUID.fromString(uuidPerson))
                .setFirstName("Maria")
                .setLastName("Kokoshka")
                .setContactInfo(new ContactInfoDto().setId(UUID.fromString(uuidContactInfo))
                        .setIndex(80339)
                        .setCountry("China")
                        .setCity("Hong Kong")
                        .setStreet("Mau the dum")
                        .setHouse("1/1")
                        .setApartment(1)
                        .setEmail("kuku@ru.muku")
                        .setPhone1("123456789")
                        .setPhone2("98741236547")))
                .setCitizenship("ChinaMensch")
                .setStudyStatus(StudyStatus.FINISHED)
                .setStartOfStudy(LocalDate.of(2021, 12, 12));
    }

    @Test
    public void studentValidFirstName_whenFirstNameNull_thenReturnFirstNameErrorValidationNotBlank() throws Exception {
        studentDto.setFirstName(null);

        Set<ConstraintViolation<StudentDto>> constraintViolations = validator
                .validate(studentDto);
        System.out.println(constraintViolations.size());
        assertTrue(constraintViolations.size() == 1);
        List<ConstraintViolation>   constraintViolation = constraintViolations.stream().collect(Collectors.toList());

        assertEquals(constraintViolation.get(0).getMessageTemplate(),"{javax.validation.constraints.NotBlank.message}");
        assertEquals(constraintViolation.get(0).getPropertyPath().toString(),"firstName");
        assertNull(constraintViolation.get(0).getInvalidValue());
    }

    @Test
    public void studentValidLastName_whenLastNameNull_thenReturnLastNameErrorValidationNotBlank() throws Exception {
        studentDto.setLastName(null);

        Set<ConstraintViolation<StudentDto>> constraintViolations = validator
                .validate(studentDto);
        System.out.println(constraintViolations.size());
        assertTrue(constraintViolations.size() == 1);
        List<ConstraintViolation>   constraintViolation = constraintViolations.stream().collect(Collectors.toList());

        assertEquals(constraintViolation.get(0).getMessageTemplate(),"{javax.validation.constraints.NotBlank.message}");
        assertEquals(constraintViolation.get(0).getPropertyPath().toString(),"lastName");
        assertNull(constraintViolation.get(0).getInvalidValue());
    }

    @Test
    public void studentValidStudyStatus_whenStudyStatusNull_thenReturnStudyStatusErrorValidationNotNull() throws Exception {
        studentDto.setStudyStatus(null);

        Set<ConstraintViolation<StudentDto>> constraintViolations = validator
                .validate(studentDto);
        System.out.println(constraintViolations.size());
        assertTrue(constraintViolations.size() == 1);
        List<ConstraintViolation>   constraintViolation = constraintViolations.stream().collect(Collectors.toList());

        assertEquals(constraintViolation.get(0).getMessageTemplate(),"{javax.validation.constraints.NotNull.message}");
        assertEquals(constraintViolation.get(0).getPropertyPath().toString(),"studyStatus");
        assertNull(constraintViolation.get(0).getInvalidValue());
    }

    @Test
    public void studentValidStartOfStudy_whenStartOfStudyNull_thenReturnStartOfStudyErrorValidationNotNull() throws Exception {
        studentDto.setStartOfStudy(null);

        Set<ConstraintViolation<StudentDto>> constraintViolations = validator
                .validate(studentDto);
        System.out.println(constraintViolations.size());
        assertTrue(constraintViolations.size() == 1);
        List<ConstraintViolation>   constraintViolation = constraintViolations.stream().collect(Collectors.toList());

        assertEquals(constraintViolation.get(0).getMessageTemplate(),"{javax.validation.constraints.NotNull.message}");
        assertEquals(constraintViolation.get(0).getPropertyPath().toString(),"startOfStudy");
        assertNull(constraintViolation.get(0).getInvalidValue());
    }

    @Test
    public void studentValidStartOfStudy_whenStartOfStudyPastDate_thenReturnStartOfStudyErrorValidationNotPastDate() throws Exception {
        studentDto.setStartOfStudy(LocalDate.of(2020, 12, 12));

        Set<ConstraintViolation<StudentDto>> constraintViolations = validator
                .validate(studentDto);
        System.out.println(constraintViolations.size());
        assertTrue(constraintViolations.size() == 1);
        List<ConstraintViolation>   constraintViolation = constraintViolations.stream().collect(Collectors.toList());

        assertEquals(constraintViolation.get(0).getMessageTemplate(),"{ua.com.foxminded.constraints.NotPastDate}");
        assertEquals(constraintViolation.get(0).getPropertyPath().toString(),"startOfStudy");
        assertEquals(constraintViolation.get(0).getInvalidValue().toString(),"2020-12-12");
    }

    @Test
    public void studentValidCitizenship_whenCitizenshipNull_thenReturnCitizenshipErrorValidationNotBlank() throws Exception {
        studentDto.setCitizenship(null);

        Set<ConstraintViolation<StudentDto>> constraintViolations = validator
                .validate(studentDto);
        System.out.println(constraintViolations.size());
        assertTrue(constraintViolations.size() == 1);
        List<ConstraintViolation>   constraintViolation = constraintViolations.stream().collect(Collectors.toList());

        assertEquals(constraintViolation.get(0).getMessageTemplate(),"{javax.validation.constraints.NotBlank.message}");
        assertEquals(constraintViolation.get(0).getPropertyPath().toString(),"citizenship");
        assertNull(constraintViolation.get(0).getInvalidValue());
    }

    @Test
    public void studentValidGrant_whenGrantNegativ_thenReturnGrantErrorValidationPositiveOrZero() throws Exception {
        studentDto.setGrant(BigDecimal.valueOf(-200));

        Set<ConstraintViolation<StudentDto>> constraintViolations = validator
                .validate(studentDto);
        System.out.println(constraintViolations.size());
        assertTrue(constraintViolations.size() == 1);
        List<ConstraintViolation>   constraintViolation = constraintViolations.stream().collect(Collectors.toList());

        assertEquals(constraintViolation.get(0).getMessageTemplate(),"{javax.validation.constraints.PositiveOrZero.message}");
        assertEquals(constraintViolation.get(0).getPropertyPath().toString(),"grant");
        assertEquals(constraintViolation.get(0).getInvalidValue(),BigDecimal.valueOf(-200));
    }

    @Test
    public void studentValidContactInfo_whenContactInfoNegativ_thenReturnContactInfoErrorValidationNotNull() throws Exception {
        studentDto.setContactInfo(null);

        Set<ConstraintViolation<StudentDto>> constraintViolations = validator
                .validate(studentDto);
        System.out.println(constraintViolations.size());
        assertTrue(constraintViolations.size() == 1);
        List<ConstraintViolation>   constraintViolation = constraintViolations.stream().collect(Collectors.toList());

        assertEquals(constraintViolation.get(0).getMessageTemplate(),"{javax.validation.constraints.NotNull.message}");
        assertEquals(constraintViolation.get(0).getPropertyPath().toString(),"contactInfo");
        assertNull(constraintViolation.get(0).getInvalidValue());
    }
}
