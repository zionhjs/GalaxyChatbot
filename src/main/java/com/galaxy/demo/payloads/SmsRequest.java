package com.galaxy.demo.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SmsRequest {
    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String message;

    public SmsRequest(@JsonProperty("phoneNumber") String phoneNumber, @JsonProperty("message") String message){
        this.phoneNumber = phoneNumber;
        this.message = message;
    }

    @Override
    public String toString(){
        return "SmsRequest{" +
                "phoneNumber= " + phoneNumber +
                ", message='" + message + '\'' +
                '}';
    }
}
