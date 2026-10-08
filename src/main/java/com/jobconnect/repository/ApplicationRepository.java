package com.jobconnect.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.jobconnect.entity.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
	List<Application> findByApplicantId(Long applicantId);
	
	List<Application> findByJobId(Long jobId);
	
	boolean existsByApplicantIdAndJobId(Long applicantId, Long jobId);

}
