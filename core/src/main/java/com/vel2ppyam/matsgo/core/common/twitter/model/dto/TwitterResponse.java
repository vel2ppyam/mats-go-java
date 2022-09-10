package com.vel2ppyam.matsgo.core.common.twitter.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class TwitterResponse {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TweetSearchResponse {
        List<TweetSearchResponseDetail> data;
        Meta meta;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class TweetSearchResponseDetail {
        String id;
        String text;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Meta {
        String newest_id;
        String oldest_id;
        Integer result_count;
        String next_token;
    }
}
