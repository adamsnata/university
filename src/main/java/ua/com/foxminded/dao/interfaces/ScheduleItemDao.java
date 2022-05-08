package ua.com.foxminded.dao.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.dao.entity.ScheduleItem;
import ua.com.foxminded.model.dto.ScheduleItemDto;

@Repository
@Qualifier("scheduleItemDao")
public interface ScheduleItemDao extends CrudRepository<ScheduleItem, String>{

    List<ScheduleItem> findByTeacherIdPerson(String id);
    List<ScheduleItem> findByGroupIdGroup(String id);
   
  @Query(" from ScheduleItem as si left join  Student as st left join Person as p " +
           " where   si.group.idGroup =  st.group.idGroup and p.idPerson = :uuid ")
    List<ScheduleItem> findScheduleByStudent(@Param("uuid") String uuid);
}

