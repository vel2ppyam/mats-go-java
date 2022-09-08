package com.vel2ppyam.matsgo.core.domain;

import com.vel2ppyam.matsgo.core.common.kakao.map.model.dto.KakaoMapResponse;
import com.vel2ppyam.matsgo.core.common.kakao.map.service.KakaoMapService;
import com.vel2ppyam.matsgo.core.common.twitter.model.dto.TwitterResponse;
import com.vel2ppyam.matsgo.core.common.twitter.model.dto.TwitterResponse.TweetSearchResponse;
import com.vel2ppyam.matsgo.core.common.twitter.service.TwitterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/test", produces = "application/json")
@RequiredArgsConstructor
public class TestController {
    private final KakaoMapService kakaoMapService;

    private final TwitterService twitterService;
    @GetMapping("/kakao")
    public List<KakaoMapResponse.KeyWordSearchResponse> getMap(
            @RequestParam String keyword,
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam Double distance
    ) {
        return kakaoMapService.getKeywordSearch(keyword, latitude, longitude, distance);
    }

    @GetMapping("/twitter")
    public TweetSearchResponse searchTweet(
    ) {
        return twitterService.tweetSearch();
    }
}
