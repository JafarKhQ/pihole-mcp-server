package me.jafarkhq.piholemcp.pihole.services;

import lombok.RequiredArgsConstructor;
import me.jafarkhq.piholemcp.pihole.clients.ListManageClient;
import me.jafarkhq.piholemcp.pihole.models.requests.CreateListRequest;
import me.jafarkhq.piholemcp.pihole.models.responses.CreateListResponse;
import me.jafarkhq.piholemcp.pihole.models.responses.ListsResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ListManageService {

    ListManageClient client;
    GroupsService groupsService;

    public ListsResponse getLists() {
        return client.getLists();
    }

    public CreateListResponse createList(List<String> addresses, String comment, boolean enabled) {
        if (CollectionUtils.isEmpty(addresses)) {
            throw new IllegalArgumentException("You must provide at least one address.");
        }


        comment = StringUtils.trimToNull(comment);

        return client.createList(new CreateListRequest(addresses, comment, getGroupIds(), enabled));
    }

    private List<Integer> getGroupIds() {
        return List.of(groupsService.getDefaultGroupId());
    }

}
