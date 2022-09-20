package com.raktkosh.pojos;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.raktkosh.enums.Antigens;
import com.raktkosh.enums.BloodTypes;
import com.raktkosh.enums.PostCategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "post")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "userId")
public class Post extends BaseEntity {
	@Enumerated(value = EnumType.STRING)
	@Column(length = 2)
	private BloodTypes type;
	@Enumerated(value = EnumType.STRING)
	@Column(length = 10)
	private Antigens antigen;
	@Enumerated(value = EnumType.STRING)
	@Column(length = 20)
	private PostCategory postCategory;
	
	@Column(insertable = false, updatable = false, columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP", name = "created_on")
	private LocalDateTime createdOn;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User userId;
}
