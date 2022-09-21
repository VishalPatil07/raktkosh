package com.raktkosh.pojos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="camp_registration")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterForCamp extends BaseEntity{
@ManyToOne
@JoinColumn(name="camp_id",nullable = false)
private BDCamp camp;
@ManyToOne
@JoinColumn(name="user_id",nullable =false)
private User user;
@Column(name="is_donated",insertable = false, columnDefinition = "TINYINT NOT NULL DEFAULT 0")
private boolean isDonated;
}
