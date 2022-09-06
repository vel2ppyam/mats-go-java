package com.vel2ppyam.matsgo.core.common.twitter.client;

import com.vel2ppyam.matsgo.core.common.twitter.model.dto.TwitterResponse.TweetSearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "twitter", url = "${twitter.host}", configuration = {TwitterFeignClient.class})
public interface TwitterFeignClient {


    @GetMapping("${twitter.path.tweet-search}")
    TweetSearchResponse tweetSearch();

}
