package com.raktkosh.dto;

import com.raktkosh.enums.Antigens;
import com.raktkosh.enums.BloodTypes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BloodBankRepositoryIdDTO {
  protected Long bankId;
  protected BloodTypes type;
  protected Antigens antigen;
}
