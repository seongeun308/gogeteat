package com.gogeteat.web.repository;

import com.gogeteat.web.entity.Store;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query("SELECT s FROM store s WHERE ST_Distance_Sphere(s.location, :point) <= :distance")
    List<Store> findAllWithinDistance(@Param("point") Point location, @Param("distance") double distance);
}
