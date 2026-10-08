package com.jobconnect.dto;
import java.util.List;

public class PageResponseDto {
	private List<JobResponseDto> content;
	public List<JobResponseDto> getContent() {
		return content;
	}
	public void setContent(List<JobResponseDto> content) {
		this.content = content;
	}
	
	private int pageNumber;
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	private int pageSize;
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	private long totalElements;
	public long getTotalElements() {
		return totalElements;
	}
	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}
	private int totalPages;
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	
	public PageResponseDto(List<JobResponseDto> content, int pageNumber, int pageSize, long totalElements, int totalPages) {
		this.content = content;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalElements = totalElements;
		this.totalPages = totalPages;
	}

}
