package me.jafarkhq.piholemcp.pihole.services;

import lombok.RequiredArgsConstructor;
import me.jafarkhq.piholemcp.pihole.clients.FtlInfoClient;
import me.jafarkhq.piholemcp.pihole.models.responses.SystemInfoResponse;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class FtlInfoService {

    FtlInfoClient ftlInfoClient;

    public SystemInfoResponse getSystemInfo() {
        return ftlInfoClient.systemInfo();
    }

}
