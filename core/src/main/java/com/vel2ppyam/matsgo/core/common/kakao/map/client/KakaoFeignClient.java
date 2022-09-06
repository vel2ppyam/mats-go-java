package com.vel2ppyam.matsgo.core.common.kakao.map.client;

import com.vel2ppyam.matsgo.core.common.config.KaKaoFeignConfiguration;
import com.vel2ppyam.matsgo.core.common.kakao.map.model.dto.KakaoMapResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="kakaomap", url="${kakao.map.host}", configuration = { KaKaoFeignConfiguration.class })
public interface KakaoFeignClient {

    /**
     GET /v2/local/search/keyword.${FORMAT} HTTP/1.1
     Host: dapi.kakao.com
     Authorization: KakaoAK ${REST_API_KEY}
     String query, String x, String y, int radius(m기준)
     */
    @GetMapping("${kakao.map.path.keyword-search}")
    KakaoMapResponse.KeyWordSearchResponse keywordSearch(@RequestParam(name = "query") String query, @RequestParam(name = "x", required = false) String x, @RequestParam(name = "y", required = false) String y, @RequestParam(name = "radius", required = false) Integer radius, @RequestParam(name = "page", required = false) Integer page);
    @GetMapping("${kakao.map.path.keyword-search}")
    KakaoMapResponse.KeyWordSearchResponse keywordSearch(@RequestParam(name = "query") String query, @RequestParam(name = "page", required = false) Integer page);
}
