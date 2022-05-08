package ua.com.foxminded.model.validation;

import java.time.LocalDate;
import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckNotPastDateValidator implements ConstraintValidator<NotPastDate, LocalDate> {

    Logger logger = LoggerFactory.getLogger("SampleLogger");
    
    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext context) {

        if (date == null) {
            return true;
        }
        
        LocalDate today = LocalDate.now();
       
        logger.info("today :" + today); 
        logger.info("date :" + date); 
            
        return !date.isBefore(today);
    }
}
