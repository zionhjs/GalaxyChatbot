package com.galaxy.demo.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SubscriptionRequest {
    @NotBlank
    private String userEmail;

    private String userNumber;

    public SubscriptionRequest(@JsonProperty("userEmail") String userEmail, @JsonProperty("userNumber") String userNumber) {
        this.userEmail = userEmail;
        this.userNumber = userNumber;
    }
}
