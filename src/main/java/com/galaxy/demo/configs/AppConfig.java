package com.galaxy.demo.configs;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class AppConfig {
    private final Map<EnvironmentVariable, Optional<String>> configurations;

    public AppConfig() {
        Stream<EnvironmentVariable> stream = Arrays.stream(EnvironmentVariable.values());
        this.configurations = stream.collect(Collectors.toMap(Function.identity(),
                v -> Optional.ofNullable(System.getenv(v.toString()))));
    }

    public String getTwilioChatServiceSID() {
        return configurations.get(EnvironmentVariable.TWILIO_CHAT_SERVICE_SID).orElse("");
    }

    public String getTwilioAPISecret() {
        return configurations.get(EnvironmentVariable.TWILIO_API_SECRET).orElse("");
    }

    public String getTwilioAPIKey() {
        return configurations.get(EnvironmentVariable.TWILIO_API_KEY).orElse("");
    }

    public String getTwilioAccountSID() {
        return configurations.get(EnvironmentVariable.TWILIO_ACCOUNT_SID).orElse("");
    }

    public boolean isIncomplete() {
        return configurations.values().stream().anyMatch(value -> !value.isPresent());
    }

    @Override
    public String toString() {
        return configurations.toString();
    }

    enum EnvironmentVariable {
        TWILIO_CHAT_SERVICE_SID,
        TWILIO_API_SECRET,
        TWILIO_API_KEY,
        TWILIO_ACCOUNT_SID
    }
}
