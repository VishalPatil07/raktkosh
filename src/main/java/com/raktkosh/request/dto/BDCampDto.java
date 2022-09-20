package com.raktkosh.request.dto;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class BDCampDto {
	private Long id;
	@NotBlank
	private String name;
	@NotBlank
	@Future
	private LocalDate campDate;
	@NotBlank
	private String city;
	@NotBlank
	private String locality;
	@NotBlank
	private String district;
	@NotBlank
	private String zipcode;
	@NotBlank
	private String startTime;
	@NotBlank
	private String endTime;
	
}
