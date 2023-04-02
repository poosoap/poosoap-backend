package com.backend.poosoap.map.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class Toilet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String addr;

    private Point point;

    @Temporal(TemporalType.TIME)
    private Date startTime;

    @Temporal(TemporalType.TIME)
    private Date endTime;

    @Builder
    public Toilet(Long id, String addr, Point point, Date startTime, Date endTime) {
        this.id = id;
        this.addr = addr;
        this.point = point;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
