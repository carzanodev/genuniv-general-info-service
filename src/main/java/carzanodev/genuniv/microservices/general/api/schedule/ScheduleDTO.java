package carzanodev.genuniv.microservices.general.api.schedule;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class ScheduleDTO {

    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("time_slot")
    private String timeSlot;

    @Data
    @AllArgsConstructor
    static class List {
        @JsonProperty("schedules")
        private Collection<ScheduleDTO> schedules;
    }

}
