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

    private static final List<String> VALID_TYPES = List.of("allow", "block");

    ListManageClient client;
    GroupsService groupsService;

    public ListsResponse getLists() {
        return client.getLists();
    }

    public CreateListResponse addNewList(String address, String type, String comment, boolean enabled) {
        return addNewLists(List.of(address), type, comment, enabled);
    }

    public CreateListResponse addNewLists(List<String> addresses, String type, String comment, boolean enabled) {
        type = StringUtils.toRootLowerCase(type);
        comment = StringUtils.trimToNull(comment);

        validateAddresses(addresses);
        if (!VALID_TYPES.contains(type)) {
            throw new IllegalArgumentException("Invalid type: " + type + ". Must be one of: " + VALID_TYPES);
        }

        return client.createList(new CreateListRequest(addresses, comment, getGroupIds(), enabled), type);
    }

    private List<Integer> getGroupIds() {
        return List.of(groupsService.getDefaultGroupId());
    }

    private void validateAddresses(List<String> addresses) {
        if (CollectionUtils.isEmpty(addresses)) {
            throw new IllegalArgumentException("You must provide at least one address.");
        }

        // validate addresses as https:// or http://
        addresses.stream()
                .filter(address -> !address.startsWith("http://") && !address.startsWith("https://"))
                .findFirst()
                .ifPresent(address -> {
                    throw new IllegalArgumentException("Invalid address: " + address + ". Address must start with http:// or https://");
                });
    }

}
