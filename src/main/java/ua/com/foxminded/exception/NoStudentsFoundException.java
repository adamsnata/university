package ua.com.foxminded.exception;

import java.util.UUID;

public class NoStudentsFoundException extends RuntimeException{

    public NoStudentsFoundException() {
        super(String.format("Students  not found"));
    }
    
}
