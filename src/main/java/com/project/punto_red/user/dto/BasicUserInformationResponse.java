package com.project.punto_red.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BasicUserInformationResponse {

    @JsonProperty(value = "administrator",required = true)
    private boolean administrator;

    @JsonProperty(value = "profile_image",required = true)
    private String profileImage;

    @JsonProperty(value = "user_name",required = true)
    private String name;

    @JsonProperty(value = "user_lastname",required = true)
    private String lastname;

    public BasicUserInformationResponse() {
    }

    public BasicUserInformationResponse(boolean administrator, String profileImage, String name, String lastname) {
        this.administrator = administrator;
        this.profileImage = profileImage;
        this.name = name;
        this.lastname = lastname;
    }
}
