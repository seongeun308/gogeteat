package com.gogeteat;

import com.gogeteat.web.entity.Store;
import com.gogeteat.web.repository.StoreRepository;
import com.gogeteat.web.service.StoreService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
@Rollback
public class DataInitTest {

    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private StoreService service;

    @Test
    public void createStoreTestData() {
        // 기준점 설정 (서울)
        double baseLatitude = 37.5665;
        double baseLongitude = 126.9780;

        // 미터 단위를 경도 및 위도 차이로 변환 (대략적 계산)
        final double meterToDegreeFactor = 1 / 111320.0; // 약 1m를 위도 경도로 변환하기 위한 팩터

        GeometryFactory geometryFactory = new GeometryFactory();

        // 100번 반복, 각 반복에서 10개의 Store를 생성
        IntStream.range(0, 100).forEach(i -> {
            double distance = i * 50; // i번 째 반복에서 50m씩 증가한 거리

            IntStream.range(0, 10).forEach(j -> {
                // 거리 변환
                double latOffset = distance * meterToDegreeFactor;
                double lonOffset = distance * meterToDegreeFactor / Math.cos(Math.toRadians(baseLatitude));

                // 새로운 좌표 계산
                double latitude = baseLatitude + latOffset;
                double longitude = baseLongitude + lonOffset;

                // Store 생성 및 저장
                Store store = Store.builder()
                        .storeName("Store " + (i * 10 + j + 1))
                        .address("Seoul, Korea " + (i * 10 + j + 1))
                        .tel("010-0000-00" + (i * 10 + j + 1))
                        .x(longitude)
                        .y(latitude)
                        .build();

                storeRepository.save(store);
            });
        });

        // 데이터 저장 확인
        long count = storeRepository.count();
        System.out.println("Total stores saved: " + count); // 총 1000개의 Store가 저장됨을 확인
    }

    @Test
    void getStoreList(){
        List<Store> storeList = service.getStoreList(126.9780, 37.5665, 50.0);
        Assertions.assertThat(storeList.size()).isEqualTo(10);
    }


}
