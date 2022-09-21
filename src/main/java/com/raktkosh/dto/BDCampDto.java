package com.raktkosh.dto;

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

public class BDCampDto extends SignupDTO{
	private Long id;
	@NotBlank
	private String name;
//	@NotBlank
	@Future
	private LocalDate campDate;
	//@NotBlank
	private String city;
	//@NotBlank
	private String locality;
	//@NotBlank
	private String district;
//	@NotBlank
	private String zip;
	//@NotBlank
	private String startTime;
//	@NotBlank
	private String endTime;
	
}
