package com.backend.poosoap.map.service.impl;

import com.backend.poosoap.common.exception.NotFoundException;
import com.backend.poosoap.map.common.GeometryUtil;
import com.backend.poosoap.map.dto.req.Direction;
import com.backend.poosoap.map.dto.req.Location;
import com.backend.poosoap.map.dto.req.ModifyDoodleForm;
import com.backend.poosoap.map.dto.req.SaveDoodlesForm;
import com.backend.poosoap.map.dto.res.DoodleRes;
import com.backend.poosoap.map.dto.res.DoodlesRes;
import com.backend.poosoap.map.dto.res.FindDoodle;
import com.backend.poosoap.map.dto.res.FindDoodles;
import com.backend.poosoap.map.entity.Doodle;
import com.backend.poosoap.map.entity.Toilet;
import com.backend.poosoap.map.repository.DoodleRepository;
import com.backend.poosoap.map.repository.ToiletRepository;
import com.backend.poosoap.map.repository.ToiletRepositoryCustom;
import com.backend.poosoap.map.service.DoodleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoodleServiceImpl implements DoodleService {

    private final static String NOT_FOUND_TOILET_ERROR_MSG = "해당하는 화장실을 찾을 수 없습니다";

    private final static String NOT_FOUND_DOODLE_ERROR_MSG = "해당하는 낙서장 찾을 수 없습니다";

    private final ToiletRepository toiletRepository;

    private final ToiletRepositoryCustom gymLocationRepository;

    private final DoodleRepository doodleRepository;

    @Override
    public Long saveDoodles(SaveDoodlesForm saveDoodlesForm) {

        Toilet toilet = toiletRepository.findById(saveDoodlesForm.getToiletId())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_TOILET_ERROR_MSG));

        Doodle doodle = Doodle.builder()
                .toilet(toilet)
                .content(saveDoodlesForm.getContent())
                .writer(saveDoodlesForm.getWriter())
                .isAnonymous(saveDoodlesForm.isAnonymous())
                .build();

        return doodleRepository.save(doodle).getId();
    }

    @Override
    public DoodlesRes findByDoodles(Location location) {

        Location northEast = GeometryUtil
                .calculate(location.getLatitude(), location.getLongitude(), location.getRadius(), Direction.NORTHEAST.getBearing());
        Location southWest = GeometryUtil
                .calculate(location.getLatitude(), location.getLongitude(), location.getRadius(), Direction.SOUTHWEST.getBearing());

        double x1 = northEast.getLatitude();
        double y1 = northEast.getLongitude();
        double x2 = southWest.getLatitude();
        double y2 = southWest.getLongitude();

        List<DoodleRes> doodles = new ArrayList<>();
        List<Toilet> toilets = gymLocationRepository.findGymLocationsWithinLine(x1, y1, x2, y2);
        for (Toilet toilet : toilets) {

            int toiletDoodleCount = doodleRepository.getToiletDoodleCount(toilet.getId());
            DoodleRes doodle = DoodleRes.builder()
                    .doodlesCount(toiletDoodleCount)
                    .latitude(toilet.getPoint().getX())
                    .longitude(toilet.getPoint().getY())
                    .build();

            doodles.add(doodle);
        }

        return new DoodlesRes(doodles);
    }

    @Override
    public FindDoodles findByDoodle(Long toiletId) {

        Toilet toilet = toiletRepository.findById(toiletId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_TOILET_ERROR_MSG));

        List<Doodle> doodles = doodleRepository.findByToilet(toilet);
        List<FindDoodle> findDoodles = new ArrayList<>();
        for (Doodle doodle : doodles) {

            FindDoodle temp = FindDoodle.builder()
                    .content(doodle.getContent())
                    .writer(doodle.isAnonymous() ? "익명" : doodle.getWriter())
                    .build();

            findDoodles.add(temp);
        }

        return new FindDoodles(findDoodles);
    }

    @Override
    public Long modifyDoodles(ModifyDoodleForm modifyDoodleForm) {

        Doodle doodle = doodleRepository.findById(modifyDoodleForm.getId())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_DOODLE_ERROR_MSG));

        doodle.modify(modifyDoodleForm);

        return doodleRepository.save(doodle).getId();
    }

    @Override
    public Long deleteDoodles(Long id) {

        Doodle doodle = doodleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_DOODLE_ERROR_MSG));

        doodleRepository.delete(doodle);

        return id;
    }
}
