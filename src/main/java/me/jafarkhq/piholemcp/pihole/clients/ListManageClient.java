package me.jafarkhq.piholemcp.pihole.clients;

import me.jafarkhq.piholemcp.pihole.models.requests.CreateListRequest;
import me.jafarkhq.piholemcp.pihole.models.responses.CreateListResponse;
import me.jafarkhq.piholemcp.pihole.models.responses.ListsResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@PiholeClient(name = "piholeListManage", path = "lists")
public interface ListManageClient {

    @GetMapping
    ListsResponse getLists();

    @PostMapping
    CreateListResponse createList(@RequestBody CreateListRequest request, @RequestParam String type);

}
