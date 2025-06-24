package com.pg.employee.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateNotificationEmployee {

    @JsonProperty("noti_epl_id")
    private String notiEplId;

    @JsonProperty("noti_title")
    private String notiTitle;

    @JsonProperty("noti_content")
    private String notiContent;

    @JsonProperty("noti_type")
    private String notiType;

    @JsonProperty("noti_metadata")
    private String notiMetadata;

    @JsonProperty("sendObject")
    private String sendObject;
}
