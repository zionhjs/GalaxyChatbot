package com.galaxy.demo.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UnSubScriptionRequest {
    @NotBlank
    private String userEmail;

    private String userName;

    public UnSubScriptionRequest(@JsonProperty("userNumber") String userEmail, @JsonProperty("senderNumber") String userName) {
        this.userEmail = userEmail;
        this.userName = userName;
    }
}
