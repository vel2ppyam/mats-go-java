package com.vel2ppyam.matsgo.core.common.twitter.service;

import com.vel2ppyam.matsgo.core.common.twitter.client.TwitterFeignClient;
import com.vel2ppyam.matsgo.core.common.twitter.model.dto.TwitterResponse.TweetSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TwitterService {
    private final TwitterFeignClient twitterFeignClient;

    public TweetSearchResponse tweetSearch(String query){
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("query", query);
        return twitterFeignClient.tweetSearch(query);
    }
}
