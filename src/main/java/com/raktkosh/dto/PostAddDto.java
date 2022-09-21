package com.raktkosh.dto;


import java.time.LocalDateTime;

import com.raktkosh.enums.Antigens;
import com.raktkosh.enums.BloodTypes;
import com.raktkosh.enums.PostCategory;
import com.raktkosh.pojos.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@ToString(exclude="userId")
public class PostAddDto {
	// post
	private Long id;

	private Antigens antigen;

	private LocalDateTime createdOn;

	private PostCategory postCategory;

	private BloodTypes type;

	private User userId;

	private String state;

	private String district;

	private String city;

	private String locality;
	
	private String zip;

	@Override
	public String toString() {
		return "PostAddDto [id=" + id + ", antigen=" + antigen + ", createdOn=" + createdOn + ", postCategory="
				+ postCategory + ", type=" + type + ", state=" + state + ", district=" + district + ", city=" + city
				+ ", locality=" + locality + ", zip=" + zip + "]";
	}

	
}
