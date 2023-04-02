package com.backend.poosoap.map.service.impl;

import com.backend.poosoap.map.common.GeometryUtil;
import com.backend.poosoap.map.dto.req.Direction;
import com.backend.poosoap.map.dto.req.Location;
import com.backend.poosoap.map.dto.req.ToiletReq;
import com.backend.poosoap.map.dto.res.ToiletRes;
import com.backend.poosoap.map.dto.res.ToiletsRes;
import com.backend.poosoap.map.entity.Toilet;
import com.backend.poosoap.map.repository.ToiletRepository;
import com.backend.poosoap.map.service.ToiletService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ToiletServiceImpl implements ToiletService {

    private final ToiletRepository toiletRepository;

    private final EntityManager em;

    @Override
    public Long saveToilet(ToiletReq req) {

        String pointWKT = String.format("POINT(%s %s)", req.getLocation().getLatitude(), req.getLocation().getLongitude());

        // WKTReader를 통해 WKT를 실제 타입으로 변환합니다.
        Point point = null;
        try {
            point = (Point) new WKTReader().read(pointWKT);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Toilet toilet = Toilet.builder()
                .addr(req.getAddr())
                .point(point)
                .build();

        return toiletRepository.save(toilet).getId();
    }

    @Override
    public ToiletsRes findByToilet(Pageable pageable, Location location) {

        //distance km 단위
        Double distance = 1.0;

        Location northEast = GeometryUtil
                .calculate(location.getLatitude(), location.getLongitude(), distance, Direction.NORTHEAST.getBearing());
        Location southWest = GeometryUtil
                .calculate(location.getLatitude(), location.getLongitude(), distance, Direction.SOUTHWEST.getBearing());

        double x1 = northEast.getLatitude();
        double y1 = northEast.getLongitude();
        double x2 = southWest.getLatitude();
        double y2 = southWest.getLongitude();

        String pointFormat = String.format("'LINESTRING(%f %f, %f %f)')", x1, y1, x2, y2);
        Query query = em.createNativeQuery("SELECT t.id, t.addr, t.start_time, t.ent_time, t.point FROM toilet AS t WHERE MBRContains(ST_LINESTRINGFROMTEXT(%s, t.point)".formatted(pointFormat), Toilet.class)
                .setMaxResults(10);

        List<Toilet> toilets = query.getResultList();

        List<ToiletRes> toiletResList = toilets.stream().map(toilet -> ToiletRes.builder()
                .id(toilet.getId())
                .addr(toilet.getAddr())
                .latitude(toilet.getPoint().getX())
                .longitude(toilet.getPoint().getY())
                .start_time(toilet.getStartTime().toString())
                .end_time(toilet.getEndTime().toString())
                .build()).toList();


        return new ToiletsRes(toiletResList);
    }
}
