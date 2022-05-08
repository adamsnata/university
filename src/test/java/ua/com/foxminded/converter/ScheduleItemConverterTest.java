package ua.com.foxminded.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.dao.entity.*;
import ua.com.foxminded.model.dto.*;
import ua.com.foxminded.model.enums.DayOfWeek;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ScheduleItemConverterTest {

    ScheduleItem scheduleItem;
    
    ScheduleItemDto scheduleItemDto;
    
    ScheduleItemConverter scheduleItemConverter = new ScheduleItemConverter();

    String uuid = "d4d8b36a-4435-11eb-b378-0242ac130002";

    @BeforeEach
    void initEach() {
        scheduleItem = new ScheduleItem().setId(uuid)
                .setDayOfWeek("TUESDAY")
                .setGroup(new Group().setName("gr-1"))
                .setRoom(new Room().setName("room 1"))
                .setSubject(new Subject().setName("Maths"))
                .setTimeSlot(new TimeSlot().setSerialNumber(1));
        
        scheduleItemDto = new ScheduleItemDto().setId(UUID.fromString(uuid))
                .setDayOfWeek(DayOfWeek.TUESDAY)
                .setGroup(new GroupDto().setName("gr-1"))
                .setRoom(new RoomDto().setName("room 1"))
                .setSubject(new SubjectDto().setName("Maths"))
                .setTimeSlot(new TimeSlotDto().setSerialNumber(1));
    }

    @Test
    public void convertDtoToEntity_whenValidScheduleItemDTO_thenReturnScheduleItem(){
        ScheduleItem expected = scheduleItem;

        ScheduleItem actual = scheduleItemConverter.convertDtoToEntity(scheduleItemDto);

        assertThat(actual.getDayOfWeek()).isEqualTo(expected.getDayOfWeek());
        assertThat(actual.getGroup().getName()).isEqualTo(expected.getGroup().getName());
        assertThat(actual.getRoom()).isEqualTo(expected.getRoom());
        assertThat(actual.getSubject()).isEqualTo(expected.getSubject());
        assertThat(actual.getTimeSlot()).isEqualTo(expected.getTimeSlot());
    }

    @Test
    public void convertEntityToDto_whenValidScheduleItem_thenReturnScheduleItemDTO(){
        ScheduleItemDto expected = scheduleItemDto;

        ScheduleItemDto actual = scheduleItemConverter.convertEntityToDto(scheduleItem);

        assertThat(actual.getDayOfWeek()).isEqualTo(expected.getDayOfWeek());
        assertThat(actual.getGroup()).isEqualTo(expected.getGroup());
        assertThat(actual.getRoom()).isEqualTo(expected.getRoom());
        assertThat(actual.getSubject()).isEqualTo(expected.getSubject());
        assertThat(actual.getTimeSlot()).isEqualTo(expected.getTimeSlot());
    }
}
