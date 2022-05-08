package ua.com.foxminded.exception;

import static java.lang.String.format;

public class NoSuchTeacherException extends RuntimeException{

    public NoSuchTeacherException(String id) {
        super(format("Teacher with uuid=%s not found", id));
    }   
    
}
