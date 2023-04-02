package com.backend.poosoap.map.repository;

import com.backend.poosoap.map.entity.Toilet;

import java.util.List;

public interface ToiletRepositoryCustom {

    List<Toilet> findGymLocationsWithinLine(double x1, double y1, double x2, double y2);
}
