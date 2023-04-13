package com.backend.poosoap.map.entity;

import com.backend.poosoap.map.dto.req.ModifyToiletForm;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Toilet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "toilet", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Doodle> doodles;

    private String addr;

    private Point point;

    @Temporal(TemporalType.TIME)
    private Date startTime;

    @Temporal(TemporalType.TIME)
    private Date endTime;

    @Builder
    public Toilet(Long id, String addr, Point point, Date startTime, Date endTime) {
        this.id = id;
        this.doodles = new ArrayList<>();
        this.addr = addr;
        this.point = point;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void modify(ModifyToiletForm modifyToiletForm) {
        this.addr = modifyToiletForm.getAddr();
        this.point = getPoint(modifyToiletForm);
    }

    private static Point getPoint(ModifyToiletForm modifyToiletForm) {
        String pointWKT = String.format("POINT(%s %s)", modifyToiletForm.getLocation().getLatitude(), modifyToiletForm.getLocation().getLongitude());

        Point point = null;
        try {
            point = (Point) new WKTReader().read(pointWKT);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return point;
    }
}
