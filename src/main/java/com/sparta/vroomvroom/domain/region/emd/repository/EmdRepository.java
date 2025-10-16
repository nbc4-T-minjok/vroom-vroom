package com.sparta.vroomvroom.domain.region.emd.repository;

import com.sparta.vroomvroom.domain.region.emd.model.entity.Emd;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmdRepository extends JpaRepository<Emd,Long> {

    @Query("SELECT e FROM Emd e WHERE ST_Contains(e.geom, :point) = true")
    Emd findByPoint(@Param("point") Point point);

    /**
     * 위도/경도를 받아서 Point를 만들고 해당 읍면동 조회
     */
     default Emd findByLatLng(double latitude, double longitude) {
         GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
         Point point = geometryFactory.createPoint(new Coordinate(longitude, latitude)); // 경도, 위도 순
         return findByPoint(point);
     }
}
