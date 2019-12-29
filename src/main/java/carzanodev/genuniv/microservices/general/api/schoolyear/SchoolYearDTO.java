package carzanodev.genuniv.microservices.general.api.schoolyear;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class SchoolYearDTO {

    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @Data
    @AllArgsConstructor
    static class List {
        @JsonProperty("school_years")
        private Collection<SchoolYearDTO> schoolYears;
    }

}
