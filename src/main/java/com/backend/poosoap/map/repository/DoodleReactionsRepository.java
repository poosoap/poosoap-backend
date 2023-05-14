package com.backend.poosoap.map.repository;

import com.backend.poosoap.map.entity.Doodle;
import com.backend.poosoap.map.entity.DoodleReactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoodleReactionsRepository extends JpaRepository<DoodleReactions, Long> {

    List<DoodleReactions> findByDoodle(Doodle doodle);
}
