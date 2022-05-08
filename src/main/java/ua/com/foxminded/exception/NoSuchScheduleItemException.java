package ua.com.foxminded.exception;

import static java.lang.String.format;

public class NoSuchScheduleItemException extends RuntimeException{
    
    public NoSuchScheduleItemException(String id) {
        super(format("ScheduleItem with uuid=%s not found", id));
    }   
}
