package me.jafarkhq.piholemcp.pihole.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public record SystemInfoResponse(
        System system,
        double took) {

    public record System(
            int uptime,
            Memory memory,
            int procs,
            Cpu cpu,
            Ftl ftl) {
    }

    public record Memory(
            Ram ram,
            Swap swap) {
    }

    public record Ram(
            int total,
            int free,
            int used,
            int available,
            @JsonProperty("%used") double percentUsed) {
    }

    public record Swap(
            int total,
            int used,
            int free,
            @JsonProperty("%used") double percentUsed) {
    }

    public record Cpu(
            int nprocs,
            @JsonProperty("%cpu") double percentCpu,
            Load load) {
    }

    public record Load(
            List<Double> raw,
            List<Double> percent) {
    }

    public record Ftl(
            @JsonProperty("%mem") double percentMem,
            @JsonProperty("%cpu") double percentCpu) {
    }

}
