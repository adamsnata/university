package ua.com.foxminded.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import ua.com.foxminded.converter.GroupConverter;
import ua.com.foxminded.converter.TeacherConverter;
import ua.com.foxminded.dao.entity.Group;
import ua.com.foxminded.dao.interfaces.GroupDao;
import ua.com.foxminded.dao.interfaces.TeacherDao;
import ua.com.foxminded.model.dto.GroupDto;
import ua.com.foxminded.service.interfaces.GroupService;

@Service("groupService")
@Component
public class GroupServiceImpl implements GroupService{

    @Autowired
    @Qualifier("groupDao")
    GroupDao groupDao;
    
    @Autowired
    GroupConverter groupConverter;
    
    @Override
    public List<GroupDto> findAllGroups() {
        List<GroupDto> groups = new ArrayList<>();
        groupDao.findAll().forEach(group -> {
            groups.add(groupConverter.convertEntityToDto(group));
        });
        return groups;   
    }
    
    @Override
    public GroupDto findGroupByName(String name) {
        GroupDto group = new GroupDto();
        if (name == null || name.isEmpty()) {
            return group; 
        }
        List<Group> groups = groupDao.findGroupByName(name);
        if (groups.size() > 0) {
         group = groupConverter.convertEntityToDto(groups.get(0));
        }
        return group;   
    }
}

