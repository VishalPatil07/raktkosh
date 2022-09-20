package com.raktkosh.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raktkosh.request.dto.BloodBankDTO;
import com.raktkosh.services.IBloodBankService;

@RestController
@RequestMapping("/bloodbank")
@CrossOrigin(origins = { "${com.raktkosh.ORIGINS}" })
public class BloodBankCoontroller {

  @Autowired
  private IBloodBankService bankService;

  @GetMapping("/list")
  public ResponseEntity<?> getAllBanks() {
    return ResponseEntity.ok(bankService.getAllBanks());
  }
  
  @GetMapping("/{id}")
  public ResponseEntity<?> getBanksDetails(@PathVariable Long id) {
    return ResponseEntity.ok(bankService.getBankDetailsById(id));
  }
  
  @PostMapping("/add")
  public ResponseEntity<?> addBanks(@RequestBody @Valid BloodBankDTO bank) {
    return ResponseEntity.ok(bankService.saveBankDetails(bank));
  }
  
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteBank(@PathVariable Long id) {
    System.out.println("Bank ID : " + id);
    return ResponseEntity.ok(bankService.deleteById(id));
  }
}
