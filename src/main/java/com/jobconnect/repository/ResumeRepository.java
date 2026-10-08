package com.jobconnect.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.jobconnect.entity.Resume;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
	Optional<Resume> findByUserId(Long userId);

}
