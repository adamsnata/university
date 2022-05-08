package ua.com.foxminded.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import ua.com.foxminded.TestConfig;
import ua.com.foxminded.converter.GroupConverter;
import ua.com.foxminded.dao.entity.Group;
import ua.com.foxminded.dao.interfaces.GroupDao;
import ua.com.foxminded.model.dto.GroupDto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ContextConfiguration(classes = {TestConfig.class})
@ExtendWith(MockitoExtension.class)
public class GroupServiceTest {

    @Mock
    GroupDao groupDao;

    @InjectMocks
    GroupServiceImpl groupServise;

    @Spy
    GroupConverter groupConverter;

    Group group;

    List<Group> groups = new ArrayList<>();
    @BeforeEach
    void setUp() {
        group = new Group()
                .setId(UUID.randomUUID().toString())
                .setName("gggr")
                .setSpecialty("ECONOMY");
        groups.add(group);
    }


    @Test
    void findAllTest_when1Group_thenReturnSize1 () {
        given(groupDao.findAll()).willReturn(groups);

        List<GroupDto> groupsFound = groupServise.findAllGroups();

        assertThat(groupsFound).hasSize(1);
    }

    @Test
    void findGroupByName_whenGroupNameNull_thenEmptyGroup () {
        GroupDto groupFound = groupServise.findGroupByName(null);

        assertThat(groupFound).isEqualTo(new GroupDto());
    }

    @Test
    void findGroupByName_whenGroupValid_thenEmptyGroup () {
        given(groupDao.findGroupByName(any())).willReturn(groups);

        GroupDto groupFound = groupServise.findGroupByName("group");

        assertThat(groupFound).isNotEqualTo(new GroupDto());
    }
}
