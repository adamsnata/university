package ua.com.foxminded.service.interfaces;

import java.util.List;
import java.util.UUID;

import ua.com.foxminded.model.dto.GroupDto;

public interface GroupService {
    public List<GroupDto> findAllGroups();
    public GroupDto findGroupByName(String name); 
}
