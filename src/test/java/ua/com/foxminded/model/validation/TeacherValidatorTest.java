package ua.com.foxminded.model.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.dao.entity.ContactInfo;
import ua.com.foxminded.dao.entity.Teacher;
import ua.com.foxminded.model.dto.ContactInfoDto;
import ua.com.foxminded.model.dto.TeacherDto;
import ua.com.foxminded.model.dto.TeacherDto;
import ua.com.foxminded.model.enums.Degree;
import ua.com.foxminded.model.enums.Department;
import ua.com.foxminded.model.enums.Department;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class TeacherValidatorTest {

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private TeacherDto teacherDto;

    String uuidPerson = "d4d8b36a-4435-11eb-b378-0242ac130002";
    String uuidContactInfo = "2a6e5b4a-4436-11eb-b378-0242ac130002";

    @BeforeEach
    void initEach() {

        teacherDto = ((TeacherDto) new TeacherDto().setIdPerson(UUID.fromString(uuidPerson))
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
                .setDegree(Degree.DOCENT)
                .setDepartment(Department.CHEMISTRY)
                .setPermanent(true)
                .setSalary(BigDecimal.valueOf(1000));
    }
    @Test
    public void teacherValidFirstName_whenFirstNameNull_thenReturnFirstNameErrorValidationNotBlank() throws Exception {
        teacherDto.setFirstName(null);

        Set<ConstraintViolation<TeacherDto>> constraintViolations = validator
                .validate(teacherDto);
        System.out.println(constraintViolations.size());
        assertTrue(constraintViolations.size() == 1);
        List<ConstraintViolation> constraintViolation = constraintViolations.stream().collect(Collectors.toList());

        assertEquals(constraintViolation.get(0).getMessageTemplate(),"{javax.validation.constraints.NotBlank.message}");
        assertEquals(constraintViolation.get(0).getPropertyPath().toString(),"firstName");
        assertNull(constraintViolation.get(0).getInvalidValue());
    }

    @Test
    public void teacherValidLastName_whenLastNameNull_thenReturnLastNameErrorValidationNotBlank() throws Exception {
        teacherDto.setLastName(null);

        Set<ConstraintViolation<TeacherDto>> constraintViolations = validator
                .validate(teacherDto);
        System.out.println(constraintViolations.size());
        assertTrue(constraintViolations.size() == 1);
        List<ConstraintViolation>   constraintViolation = constraintViolations.stream().collect(Collectors.toList());

        assertEquals(constraintViolation.get(0).getMessageTemplate(),"{javax.validation.constraints.NotBlank.message}");
        assertEquals(constraintViolation.get(0).getPropertyPath().toString(),"lastName");
        assertNull(constraintViolation.get(0).getInvalidValue());
    }

    @Test
    public void teacherValidDepartment_whenDepartmentNull_thenReturnDepartmentErrorValidationNotNull() throws Exception {
        teacherDto.setDepartment(null);

        Set<ConstraintViolation<TeacherDto>> constraintViolations = validator
                .validate(teacherDto);
        System.out.println(constraintViolations.size());
        assertTrue(constraintViolations.size() == 1);
        List<ConstraintViolation>   constraintViolation = constraintViolations.stream().collect(Collectors.toList());

        assertEquals(constraintViolation.get(0).getMessageTemplate(),"{javax.validation.constraints.NotNull.message}");
        assertEquals(constraintViolation.get(0).getPropertyPath().toString(),"department");
        assertNull(constraintViolation.get(0).getInvalidValue());
    }

    @Test
    public void teacherValidSalary_whenSalaryNegativ_thenReturnSalaryErrorValidationPositiveOrZero() throws Exception {
        teacherDto.setSalary(BigDecimal.valueOf(-200));

        Set<ConstraintViolation<TeacherDto>> constraintViolations = validator
                .validate(teacherDto);
        System.out.println(constraintViolations.size());
        assertTrue(constraintViolations.size() == 1);
        List<ConstraintViolation>   constraintViolation = constraintViolations.stream().collect(Collectors.toList());

        assertEquals(constraintViolation.get(0).getMessageTemplate(),"{javax.validation.constraints.PositiveOrZero.message}");
        assertEquals(constraintViolation.get(0).getPropertyPath().toString(),"salary");
        assertEquals(constraintViolation.get(0).getInvalidValue(),BigDecimal.valueOf(-200));
    }

    @Test
    public void teacherValidContactInfo_whenContactInfoNegativ_thenReturnContactInfoErrorValidationNotNull() throws Exception {
        teacherDto.setContactInfo(null);

        Set<ConstraintViolation<TeacherDto>> constraintViolations = validator
                .validate(teacherDto);
        System.out.println(constraintViolations.size());
        assertTrue(constraintViolations.size() == 1);
        List<ConstraintViolation>   constraintViolation = constraintViolations.stream().collect(Collectors.toList());

        assertEquals(constraintViolation.get(0).getMessageTemplate(),"{javax.validation.constraints.NotNull.message}");
        assertEquals(constraintViolation.get(0).getPropertyPath().toString(),"contactInfo");
        assertNull(constraintViolation.get(0).getInvalidValue());
    }
}
