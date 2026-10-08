package com.jobconnect.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.jobconnect.entity.Job;
import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
	List<Job> findByTitleContainingIgnoreCase(String title);
	
	List<Job> findByLocationContainingIgnoreCase(String location);
	
	List<Job> findByRecruiterId(Long recruiterId);

}
