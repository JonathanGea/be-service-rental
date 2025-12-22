package com.gea.app.shared.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RequestIdGenerator {
    public String generateRequestId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
