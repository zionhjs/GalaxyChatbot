package com.galaxy.demo.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class QuotationRequest {
    @NotBlank
    private String userEmail;

    private String messageInside;

    private String messageOutside;

    public QuotationRequest(@JsonProperty("userEmail") String userEmail, @JsonProperty("message_inside") String message_inside, @JsonProperty("message_outside") String message_outside) {
        this.userEmail = userEmail;
        this.messageInside = message_inside;
        this.messageOutside = message_outside;
    }
}
