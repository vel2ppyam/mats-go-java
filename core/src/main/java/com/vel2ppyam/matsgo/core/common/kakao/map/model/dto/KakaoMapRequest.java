package com.vel2ppyam.matsgo.core.common.kakao.map.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class KakaoMapRequest {
    @Getter
    @Setter
    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor
    public static class KeyWordSearchRequest{
        private String query;
        private String x;
        private String y;
        private Integer radius;
    }
}
