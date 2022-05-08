package ua.com.foxminded.model.enums;

public enum DayOfWeek {
    MONDAY, TUESDAY, WENDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;
    
    public String toString() {
        switch (this) {
        case MONDAY:
            return "MONDAY";
        case TUESDAY:
            return "TUESDAY";    
        case WENDNESDAY:
            return "WENDNESDAY";
        case THURSDAY:
            return "THURSDAY";
        case FRIDAY:
            return "FRIDAY";
        case SATURDAY:
            return "SATURDAY";
        case SUNDAY:
            return "SUNDAY";
        default:
            return "";
        }    
    } 
}
