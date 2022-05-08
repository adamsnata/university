package ua.com.foxminded.util;

import java.util.Optional;

public class ParameterValidator {
    
    public void isNotNullNotEmpty(String parameter) {
        Optional
            .ofNullable(parameter)
            .orElseThrow(() -> new IllegalArgumentException("Null as parameter is not allowed "));
    
        if (parameter.trim().isEmpty()) {
            throw new IllegalArgumentException("Empty parameter are not allowed ");
        }
    }  
}