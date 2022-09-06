package com.vel2ppyam.matsgo.core.domain;

import com.vel2ppyam.matsgo.core.domain.map.model.dto.KakaoMapResponse;
import com.vel2ppyam.matsgo.core.domain.map.service.KakaoMapService;
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
public class KakaoTestController {
    private final KakaoMapService kakaoMapService;
    @GetMapping
    public List<KakaoMapResponse.KeyWordSearchResponse> getMap(
            @RequestParam String keyword,
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam Double distance
    ) {
        return kakaoMapService.getKeywordSearch(keyword, latitude, longitude, distance);
    }
}
