package com.jobconnect.controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.jobconnect.entity.Company;
import com.jobconnect.service.CompanyService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
public class CompanyController {
	private final CompanyService companyService;
	public CompanyController(CompanyService companyService) {
		this.companyService = companyService;
	}
	
	@PostMapping("/companies")
	public Company createCompany(@RequestBody Company company) {
		return
				companyService.createCompany(company);
	}
	
	@GetMapping("/companies")
	public List<Company> getAllCompanies(){
		return 
				companyService.getAllCompanies();
	}
	
	@GetMapping("/companies/{id}")
	public Company getCompanyById(@PathVariable Long id) {
		return
				companyService.getCompanyById(id);
	}
	
	@PutMapping("/companies/{id}")
	public Company updateCompany(@PathVariable Long id, @RequestBody Company company) {
		return companyService.updateCompany(id, company);
	}
	
	@DeleteMapping("/companies/{id}")
	public String deleteCompany(@PathVariable Long id) {
		companyService.deleteCompany(id);
		return
				"Company deleted successfully";
	}

}
