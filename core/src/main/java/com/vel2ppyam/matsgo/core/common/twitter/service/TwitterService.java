package com.vel2ppyam.matsgo.core.common.twitter.service;

import com.vel2ppyam.matsgo.core.common.twitter.client.TwitterFeignClient;
import com.vel2ppyam.matsgo.core.common.twitter.model.dto.TwitterResponse;
import com.vel2ppyam.matsgo.core.common.twitter.model.dto.TwitterResponse.TweetSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TwitterService {
    private final TwitterFeignClient twitterFeignClient;

    public TweetSearchResponse tweetSearch(){
        return twitterFeignClient.tweetSearch("nasa","popular");
    }
}
