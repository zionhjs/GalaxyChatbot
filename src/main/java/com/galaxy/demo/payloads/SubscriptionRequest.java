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

    private String userName;

    public SubscriptionRequest(@JsonProperty("userEmail") String userEmail) {
        this.userEmail = userEmail;
    }

    public SubscriptionRequest(@JsonProperty("userEmail") String userEmail, @JsonProperty("userName") String userName) {
        this.userEmail = userEmail;
        this.userName = userName;
    }
}
