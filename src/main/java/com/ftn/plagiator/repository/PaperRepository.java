package com.ftn.plagiator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.plagiator.model.Paper;

@Repository
public interface PaperRepository extends JpaRepository<Paper, Long> {
	

}
