package com.jobconnect.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.jobconnect.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long>{

}
