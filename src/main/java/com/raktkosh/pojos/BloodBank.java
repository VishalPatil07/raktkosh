package com.raktkosh.pojos;

import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "blood_banks")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BloodBank extends BaseEntity {
  
  @Column(length = 30, nullable = false, unique = true)
  private String regID;
  
  @Column(length = 50, nullable = false)
  private String name;
  
  @Column(length = 50, unique = true, nullable = false)
  private String email;
  
  @Pattern(regexp = "^[6789]\\d{9}$")
  @Column(length = 10)
  private String mobile;
  
  @DateTimeFormat(pattern = "hh:mm:ss a")
  private LocalTime openAt;
  
  @DateTimeFormat(pattern = "hh:mm:ss a")
  private LocalTime closeAt;
  
  @Column(insertable = false, updatable = false, columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP", name = "registered_on")
  private LocalDateTime registerdOn;
  
  @OneToOne(mappedBy = "bank", cascade = CascadeType.ALL)
  private BankAddress address;
}
