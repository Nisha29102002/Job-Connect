package com.jobconnect.dto;
import jakarta.validation.constraints.NotBlank;

public class JobRequestDto {
	
	@NotBlank(message = "Title cannot be blank")
	private String title;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	private Long companyId;
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompany(Long companyId) {
		this.companyId = companyId;
	}
	private String location;
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	private String description;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	private String employmentType;
	public String getEmploymentType() {
		return employmentType;
	}
	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}
	private Double salary;
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}

}
