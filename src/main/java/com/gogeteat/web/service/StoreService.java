package com.gogeteat.web.service;

import com.gogeteat.web.entity.Store;
import com.gogeteat.web.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    //TODO : Store 중복 처리 로직 작성
    // 1. 서버 내에서 자체 id를 생성 후 DB에 저장
    public Store saveStore(Store store) {
        return storeRepository.save(store);
    }

    public Store getStore(Long storeId) {
        return storeRepository.findById(storeId).orElseGet(Store::new);
    }

    public Store updateStore(Long oldStoreId, Store newStore){
        if(storeRepository.existsById(oldStoreId)){
            storeRepository.deleteById(oldStoreId);
            return storeRepository.save(newStore);
        }
        else
            throw new NoSuchElementException("Old Store is not exist.");
    }

    public void deleteStore(Long storeId) {
        storeRepository.deleteById(storeId);
    }

    // TODO : Point 객체 생성 Util로 따로 빼기
    public List<Store> getStoreList(double x, double y, double distance){
        Point currentPoint = new GeometryFactory().createPoint(new Coordinate(x, y));
        return storeRepository.findAllWithinDistance(currentPoint, distance);
    }



}
