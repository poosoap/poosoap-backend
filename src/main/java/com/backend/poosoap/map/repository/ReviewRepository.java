package com.backend.poosoap.map.repository;

import com.backend.poosoap.map.entity.Review;
import com.backend.poosoap.map.entity.Toilet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByToilet(Toilet toilet, Pageable pageable);

}
