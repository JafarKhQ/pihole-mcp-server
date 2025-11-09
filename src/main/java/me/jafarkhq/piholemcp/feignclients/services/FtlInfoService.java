package me.jafarkhq.piholemcp.feignclients.services;

import lombok.RequiredArgsConstructor;
import me.jafarkhq.piholemcp.feignclients.FtlInfoClient;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class FtlInfoService {

    FtlInfoClient ftlInfoClient;

    public String getSystemInfo() {
        return ftlInfoClient.systemInfo();
    }

}
