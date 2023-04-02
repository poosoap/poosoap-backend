package com.backend.poosoap.map.repository.impl;

import com.backend.poosoap.map.entity.Toilet;
import com.backend.poosoap.map.repository.ToiletRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ToiletRepositoryCustomImpl implements ToiletRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Toilet> findGymLocationsWithinLine(double x1, double y1, double x2, double y2) {
        String pointFormat = String.format("'LINESTRING(%f %f, %f %f)')", x1, y1, x2, y2);
        Query query = entityManager.createNativeQuery("SELECT t.id, t.addr, t.start_time, t.end_time, t.point FROM toilet AS t WHERE MBRContains(ST_LINESTRINGFROMTEXT(%s, t.point)"
                        .formatted(pointFormat), Toilet.class)
                .setMaxResults(10);

        return query.getResultList();
    }
}
