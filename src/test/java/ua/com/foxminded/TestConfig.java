package ua.com.foxminded;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import ua.com.foxminded.converter.StudentConverter;
import ua.com.foxminded.dao.interfaces.ScheduleItemDao;
import ua.com.foxminded.service.ScheduleItemServiceImpl;

@Profile("test")
@Configuration
public class TestConfig {

    @Bean
    @Qualifier("scheduleItemDao")
    public ScheduleItemDao scheduleItemDao() {
        return Mockito.mock( ScheduleItemDao.class);
    }

    
    @Bean
    public ScheduleItemServiceImpl scheduleItemServiceImple(){
        return new ScheduleItemServiceImpl();
    }
    
    @Bean
    public  StudentConverter studentConverter(){
        return new StudentConverter();
    }
}
