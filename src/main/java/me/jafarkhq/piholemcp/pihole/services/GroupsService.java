package me.jafarkhq.piholemcp.pihole.services;

import org.springframework.stereotype.Service;


@Service
public class GroupsService {

    public Integer getDefaultGroupId() {
        return getGroupId("default");
    }

    public Integer getGroupId(String name) {
        // Placeholder implementation. return default groupId for now.
        return 0;
    }

}
