package com.backend.poosoap.map.service.impl;

import com.backend.poosoap.common.exception.NotFoundException;
import com.backend.poosoap.map.common.GeometryUtil;
import com.backend.poosoap.map.dto.req.Direction;
import com.backend.poosoap.map.dto.req.Location;
import com.backend.poosoap.map.dto.req.ModifyToiletForm;
import com.backend.poosoap.map.dto.req.SaveToiletForm;
import com.backend.poosoap.map.dto.res.ToiletRes;
import com.backend.poosoap.map.dto.res.ToiletsRes;
import com.backend.poosoap.map.entity.Toilet;
import com.backend.poosoap.map.repository.ToiletRepository;
import com.backend.poosoap.map.repository.impl.ToiletRepositoryCustomImpl;
import com.backend.poosoap.map.service.ToiletService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ToiletServiceImpl implements ToiletService {

    private final static String NOT_FOUND_ERROR_MSG = "해당하는 화장실을 찾을 수 없습니다";

    private final ToiletRepository toiletRepository;

    private final ToiletRepositoryCustomImpl gymLocationRepository;

    @Override
    public Long saveToilet(SaveToiletForm req) {

        String pointWKT = String.format("POINT(%s %s)", req.getLocation().getLatitude(), req.getLocation().getLongitude());

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
    public ToiletsRes findByToilet(Location location) {

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

        List<Toilet> toilets = gymLocationRepository.findGymLocationsWithinLine(x1, y1, x2, y2);;
        List<ToiletRes> toiletResList = toilets.stream().map(toilet -> ToiletRes.builder()
                .id(toilet.getId())
                .addr(toilet.getAddr())
                .latitude(toilet.getPoint().getX())
                .longitude(toilet.getPoint().getY())
//                .start_time(toilet.getStartTime().toString())
//                .end_time(toilet.getEndTime().toString())
                .build()).toList();

        return new ToiletsRes(toiletResList);
    }

    @Override
    @Transactional
    public Long modifyToilet(ModifyToiletForm modifyToiletForm) {

        Toilet toilet = toiletRepository.findById(modifyToiletForm.getId())
                                    .orElseThrow(() -> new NotFoundException(NOT_FOUND_ERROR_MSG));

        toilet.modify(modifyToiletForm);

        return toiletRepository.save(toilet).getId();
    }

    @Override
    public Long deleteToilet(Long id) {

        Toilet toilet = toiletRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_ERROR_MSG));
        toiletRepository.deleteById(toilet.getId());

        return toilet.getId();
    }
}
