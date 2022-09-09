package com.vel2ppyam.matsgo.core.common.twitter.model.dto;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TwitterUrl {
    @Value("${twitter.host}")
    public static String host;

    @Value("${twitter.path.tweet-search}")
    public static String tweetSearchUrl;

    public static String getTweetSearchUrl() {
        return host + tweetSearchUrl;
    }
}
