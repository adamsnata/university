package ua.com.foxminded.error;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ErrorDescriptor {
    
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String message;
    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    private List<SubError>  subErrors = new ArrayList<SubError>();

    public ErrorDescriptor() {
        this.timestamp = LocalDateTime.now();
        this.message = "Validation errors";
        this.status = HttpStatus.BAD_REQUEST;
    }

    public ErrorDescriptor(BindingResult bindingResult, String object) {
        this();
        bindingResult.getFieldErrors().forEach(error ->
        {        
            SubError subError = new SubError().setField(error.getField())
                    .setMessage(error.getDefaultMessage())
                    .setObject(object);
            Optional.ofNullable(error.getRejectedValue())
            .ifPresent(errorValue -> subError.setRejectedValue(errorValue.toString()));   
            subErrors.add(subError);    
        });     
    }
    
    public HttpStatus getStatus() {
        return status;
    }

    public  ErrorDescriptor setStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public  ErrorDescriptor setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public  ErrorDescriptor setMessage(String message) {
        this.message = message;
        return this;
    }

    public List<SubError> getSubErrors() {
        return subErrors;
    }

    public ErrorDescriptor setSubErrors(List<SubError> subErrors) {
        this.subErrors = subErrors;
        return this;
    }
}
