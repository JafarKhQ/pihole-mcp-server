package me.jafarkhq.piholemcp.pihole.services;

import lombok.RequiredArgsConstructor;
import me.jafarkhq.piholemcp.pihole.clients.FtlInfoClient;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class FtlInfoService {

    FtlInfoClient ftlInfoClient;

    public String getSystemInfo() {
        return ftlInfoClient.systemInfo();
    }

}
