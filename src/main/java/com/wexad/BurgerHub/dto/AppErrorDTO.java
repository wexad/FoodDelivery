package com.wexad.BurgerHub.dto;

import lombok.Getter;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
public class AppErrorDTO {
    private final String errorMessage;
    private final String errorPath;
    private final Integer errorCode;
    private final LocalDateTime timestamp;

    public AppErrorDTO(String errorMessage, String errorPath, Integer errorCode) {
        this.errorMessage = errorMessage;
        this.errorPath = errorPath;
        this.errorCode = errorCode;
        this.timestamp = LocalDateTime.now(Clock.system(ZoneId.of("Asia/Tashkent")));
    }
}
