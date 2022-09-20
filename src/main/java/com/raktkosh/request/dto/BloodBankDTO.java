package com.raktkosh.request.dto;

import java.time.LocalTime;

import com.raktkosh.pojos.BankAddress;
import com.raktkosh.pojos.BloodBank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BloodBankDTO {
  private String regID;
  private String name;
  private String email;
  private String mobile;
  private LocalTime openAt;
  private LocalTime closeAt;
 
  private String state;
  private String district;
  private String city;
  private String zip;
  private String locality;
  
  public BloodBank buildBloodBank() {   
    BloodBank bank = new BloodBank(regID, name, email, mobile, openAt, closeAt, null, null);
    return bank;
  }
  
  public BankAddress buildBankAddress() {
    BankAddress address = new BankAddress();
    address.setCity(city);
    address.setState(state);
    address.setDistrict(district);
    address.setLocality(locality);
    address.setZip(zip);
    return address;
  }
}
