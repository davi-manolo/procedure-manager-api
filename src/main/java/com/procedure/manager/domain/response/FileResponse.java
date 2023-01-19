package com.procedure.manager.domain.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileResponse {

    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private String contentBase64;

}
