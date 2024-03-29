package com.vel2ppyam.matsgo.core.common.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCommonEntity<T> implements Serializable {
    private long timestamp;
    private String status;
    private Integer code;
    private String message;
    private T data;

    public ResponseCommonEntity(String status, Integer code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.timestamp = Instant.now().toEpochMilli();
    }

    public ResponseCommonEntity(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = Instant.now().toEpochMilli();
    }

    public ResponseCommonEntity(String status, Integer code, String message, T data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.timestamp = Instant.now().toEpochMilli();
        this.data = data;
    }

    public static Map emptyData() {
        return new HashMap();
    }
}
