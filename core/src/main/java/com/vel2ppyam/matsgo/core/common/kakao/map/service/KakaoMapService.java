package com.vel2ppyam.matsgo.core.common.kakao.map.service;

import com.vel2ppyam.matsgo.core.common.kakao.map.client.KakaoFeignClient;
import com.vel2ppyam.matsgo.core.common.kakao.map.model.dto.KakaoMapResponse.KeyWordSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KakaoMapService {
    private final KakaoFeignClient kakaoFeignClient;

    public List<KeyWordSearchResponse> getKeywordSearch(String keyword, Double latitude, Double longitude, Double distance) {
        int page = 1;
        List<KeyWordSearchResponse> keyWordSearchResponseList = new ArrayList<>();
        while(true){
            KeyWordSearchResponse kakaoSearchResponse;
            //meter로 변환위해 distance * 1000
            if(latitude != null && longitude != null && distance != null){
                kakaoSearchResponse = kakaoFeignClient.keywordSearch(keyword, String.valueOf(longitude), String.valueOf(latitude), (int)(distance * 1000), page++);

            } else{
                kakaoSearchResponse = kakaoFeignClient.keywordSearch(keyword, page++);
            }
            keyWordSearchResponseList.add(kakaoSearchResponse);
            if(kakaoSearchResponse.getMeta().getIs_end()){
                break;
            }
        }
        return keyWordSearchResponseList;
    }
}
