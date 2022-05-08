package ua.com.foxminded.exception;

import static java.lang.String.format;

public class NoSuchStudentException extends RuntimeException{

    public NoSuchStudentException(String id) {
        super(format("Student with uuid=%s not found", id));
    }   
}
