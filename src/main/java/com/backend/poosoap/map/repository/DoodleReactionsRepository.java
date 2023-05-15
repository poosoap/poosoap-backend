package com.backend.poosoap.map.repository;

import com.backend.poosoap.map.entity.Doodle;
import com.backend.poosoap.map.entity.DoodleReaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoodleReactionsRepository extends JpaRepository<DoodleReaction, Long> {

    List<DoodleReaction> findByDoodle(Doodle doodle);
}
