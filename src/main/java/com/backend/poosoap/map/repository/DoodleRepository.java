package com.backend.poosoap.map.repository;

import com.backend.poosoap.map.entity.Doodle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoodleRepository extends JpaRepository<Doodle, Long> {

}
