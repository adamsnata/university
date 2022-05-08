package ua.com.foxminded.exception;

public class NoTeachersFoundException extends RuntimeException{

    public NoTeachersFoundException() {
        super(String.format("Teachers  not found"));
    }
}
