package com.sparta.vroomvroom.domain.region.emd.model.entity;

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
@Table(name = "emd")
@Getter
@Setter
@NoArgsConstructor
public class Emd extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "emd_id")
    private UUID id;

    @Column(name = "code", nullable = false, length = 20)
    private String code;

    @Column(name = "name_kr", nullable = false, length = 100)
    private String nameKr;

    @Column(name = "geom", columnDefinition = "geometry(multiPolygon, 4326)", nullable = false)
    private MultiPolygon geom;

    @Column(name = "center", columnDefinition = "geometry(Point, 4326)", nullable = false)
    private Point center;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sigungu_id", nullable = false)
    private Sido sido;
}
