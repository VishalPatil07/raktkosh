package com.raktkosh.pojos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.raktkosh.dto.SignupDTO;
import com.raktkosh.enums.Antigens;
import com.raktkosh.enums.BloodTypes;
import com.raktkosh.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude="address")
public class User extends BaseEntity {
  
  @Column(unique = true, length = 20, nullable = false)
  private String username;
  
  @Column(nullable = false)
  private String password;
  
  @Column(length = 50, nullable = false)
  private String name;
  
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @Column
  private LocalDate dob;
  
  @Column(length = 50, unique = true, nullable = false)
  private String email;
  
  @Pattern(regexp = "^[6789]\\d{9}$")
  @Column(length = 10)
  private String mobile;
  
  @Enumerated(value = EnumType.STRING)
  @Column(length = 10)
  private Role role;
  
  @Enumerated(value = EnumType.STRING)
  @Column(name = "blood_type", length = 2)
  private BloodTypes bloodType;
  
  @Enumerated(value = EnumType.STRING)
  @Column(length = 10)
  private Antigens antigen;
  
  @Column(insertable = false, updatable = false, columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP", name = "registered_on")
  private LocalDateTime registerdOn;
 
  @OneToOne(mappedBy = "user",  cascade = CascadeType.ALL)
  @JsonIgnore
  private UserAddress address;
  
  @Column(insertable = false, columnDefinition = "TINYINT NOT NULL DEFAULT 0")
  private boolean activated;
  
  @Column(name="recent_donation_date")
  private LocalDate donationDate;
  
  
  @JsonIgnore
  @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
  private List<Post> userId;



}
