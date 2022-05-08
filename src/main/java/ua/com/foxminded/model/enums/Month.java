package ua.com.foxminded.model.enums;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.text.*;

public enum Month {

    JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER;
    
    @Override
    public String toString() {
        return WordUtils.capitalizeFully(name());
    }
    
    public static List<String> getAllMonths(){
        return Stream.of(Month.values())
                .map(Month::toString)
                .collect(Collectors.toList());
    }
}
