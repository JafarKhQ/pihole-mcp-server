package me.jafarkhq.piholemcp.feignclients;

import me.jafarkhq.piholemcp.feignclients.models.AddNewDomainRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@PiholeClient(name = "piholeDomainMange", path = "domains")
public interface DomainManageClient {

    @PostMapping("/{type}/{kind}")
    String addNewDomain(@PathVariable("type") String type, @PathVariable("kind") String kind,
                        @RequestBody AddNewDomainRequest request);

}
