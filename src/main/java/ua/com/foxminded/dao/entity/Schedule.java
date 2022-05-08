package ua.com.foxminded.dao.entity;

import java.util.List;

public class Schedule {
    
    private List<ScheduleItem> scheduleItems;
    private Period period;

    public Schedule() {
       
    }

    public List<ScheduleItem> getScheduleItems() {
        return scheduleItems;
    }

    public Schedule setScheduleItems(List<ScheduleItem> scheduleItems) {
        this.scheduleItems = scheduleItems;
        return this;
    }

    public Period getPeriod() {
        return period;
    }

    public Schedule setPeriod(Period period) {
        this.period = period;
        return this;
    }
}
