package com.backend.poosoap.map.repository;

import com.backend.poosoap.map.entity.Doodle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DoodleRepository extends JpaRepository<Doodle, Long> {

    @Query("SELECT COUNT(d) FROM Doodle d WHERE d.toilet.id = :toiletId")
    int getToiletDoodleCount(@Param("toiletId") Long toiletId);
}
