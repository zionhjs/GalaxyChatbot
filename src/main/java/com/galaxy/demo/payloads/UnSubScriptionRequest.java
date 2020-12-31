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

    private String userNumber;

    public UnSubScriptionRequest(@JsonProperty("userEmail") String userEmail, @JsonProperty("senderNumber") String userNumber) {
        this.userEmail = userEmail;
        this.userNumber = userNumber;
    }
}
