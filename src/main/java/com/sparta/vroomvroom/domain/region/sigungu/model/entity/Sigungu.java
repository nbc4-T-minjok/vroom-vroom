package com.sparta.vroomvroom.domain.region.sigungu.model.entity;

import com.sparta.vroomvroom.domain.region.sido.model.entity.Sido;
import com.sparta.vroomvroom.global.conmon.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;

import java.util.UUID;

@Entity
@Table(name = "sigungu")
@Getter
@Setter
@NoArgsConstructor
public class Sigungu extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "sigungu_id")
    private UUID id;

    @Column(nullable = false, length = 20)
    private String code;

    @Column(name = "name_kr", nullable = false, length = 100)
    private String nameKr;

    @Column(columnDefinition = "geometry(multiPolygon, 4326)", nullable = false)
    private MultiPolygon geom;

    @Column(columnDefinition = "geometry(Point, 4326)", nullable = false)
    private Point center;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sido_id", nullable = false)
    private Sido sido;
}
