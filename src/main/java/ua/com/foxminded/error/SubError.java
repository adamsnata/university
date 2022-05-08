package ua.com.foxminded.error;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SubError {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String object;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String field;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String rejectedValue;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String message;
    
    public String getObject() {
        return object;
    }
    public SubError setObject(String object) {
        this.object = object;
        return this;
    }
    public String getField() {
        return field;
    }
    public SubError setField(String field) {
        this.field = field;
        return this;
    }
    public String getRejectedValue() {
        return rejectedValue;
    }
    public SubError setRejectedValue(String rejectedValue) {
        this.rejectedValue = rejectedValue;
        return this;
    }
    public String getMessage() {
        return message;
    }
    public SubError setMessage(String message) {
        this.message = message;
        return this;
    }
}
