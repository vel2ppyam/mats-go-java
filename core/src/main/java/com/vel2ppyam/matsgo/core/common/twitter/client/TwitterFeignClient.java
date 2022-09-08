package com.vel2ppyam.matsgo.core.common.twitter.client;

import com.vel2ppyam.matsgo.core.common.config.TwitterFeignConfiguration;
import com.vel2ppyam.matsgo.core.common.twitter.model.dto.TwitterResponse.TweetSearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="twitter", url="${twitter.host}", configuration = { TwitterFeignConfiguration.class })
public interface TwitterFeignClient {


    @GetMapping("${twitter.path.tweet-search}")
    TweetSearchResponse tweetSearch(@RequestParam(name = "q") String q, @RequestParam(name = "result_type", required = false) String resultType);

}
