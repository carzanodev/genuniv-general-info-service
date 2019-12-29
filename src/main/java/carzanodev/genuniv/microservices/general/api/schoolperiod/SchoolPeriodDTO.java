package carzanodev.genuniv.microservices.general.api.schoolperiod;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class SchoolPeriodDTO {

    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @Data
    @AllArgsConstructor
    static class List {
        @JsonProperty("school_periods")
        private Collection<SchoolPeriodDTO> schoolPeriods;
    }

}
