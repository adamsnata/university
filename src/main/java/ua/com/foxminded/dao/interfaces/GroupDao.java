package ua.com.foxminded.dao.interfaces;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.dao.entity.Group;
import ua.com.foxminded.dao.entity.ScheduleItem;

@Repository
@Qualifier("groupDao")
public interface GroupDao extends CrudRepository<Group, String>{
    List<Group> findGroupByName(String name);
}
