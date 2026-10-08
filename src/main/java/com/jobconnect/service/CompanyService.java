package com.jobconnect.service;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.jobconnect.entity.Company;
import com.jobconnect.repository.CompanyRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CompanyService {
	private final CompanyRepository companyRepository;
	
	public CompanyService(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}
	
	public Company createCompany(Company company) {
		return companyRepository.save(company);
	}
	
	public List<Company> getAllCompanies(){
		return companyRepository.findAll();
	}
	
	public Company getCompanyById(Long id) {
		return
				companyRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Company not found"));
	}
	
	public Company updateCompany(Long id, Company updatedCompany) {
		Company existingCompany = companyRepository.findById(id).orElse(null);
		if(existingCompany == null) {
			return  null;
		}
		
		existingCompany.setName(updatedCompany.getName());
		existingCompany.setLocation(updatedCompany.getLocation());
		existingCompany.setDescription(updatedCompany.getDescription());
		
		return 
				companyRepository.save(existingCompany);
	}
	
	public void deleteCompany(Long id) {
		companyRepository.deleteById(id);
	}

}
