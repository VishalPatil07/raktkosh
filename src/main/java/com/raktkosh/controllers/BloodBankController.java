package com.raktkosh.controllers;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raktkosh.dao.BankRepository;
import com.raktkosh.dao.BloodBankRepository;
import com.raktkosh.dao.UserAddressRepository;
import com.raktkosh.dao.UserRepository;
import com.raktkosh.dto.BloodBankDTO;
import com.raktkosh.dto.MessageResponse;
import com.raktkosh.enums.Role;
import com.raktkosh.pojos.BloodBank;
import com.raktkosh.pojos.User;
import com.raktkosh.pojos.UserAddress;
import com.raktkosh.services.IBloodBankService;

@RestController
@RequestMapping("/bloodbank")
@CrossOrigin(origins = { "${com.raktkosh.ORIGINS}" })
public class BloodBankController {

  @Autowired
  private IBloodBankService bankService;
  
  @Autowired
	private UserRepository userRepository;
  
  @Autowired
	private PasswordEncoder passwordEncoder;
  
  @Autowired
	private ModelMapper mapper;
  
  @Autowired
	private UserAddressRepository addRepo;
  
  @Autowired
  private BankRepository bankRepo;

  @GetMapping("/list")
  public ResponseEntity<?> getAllBanks() {
    return ResponseEntity.ok(bankService.getAllBanks());
  }
  
  @GetMapping("/{id}")
  public ResponseEntity<?> getBanksDetails(@PathVariable Long id) {
    return ResponseEntity.ok(bankService.getBankDetailsById(id));
  }
  
  @PostMapping("/add")
  public ResponseEntity<?> addBanks(@RequestBody @Valid BloodBankDTO dto) {
//    return ResponseEntity.ok(bankService.saveBankDetails(bank));
	  if (userRepository.existsByUsername(dto.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Username is already taken."));
		}

		if (userRepository.existsByEmail(dto.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Email is already in use."));
		}

		dto.setPassword(passwordEncoder.encode(dto.getPassword()));
		User user = mapper.map(dto, User.class);
		user.setRole(Role.BLOOD_BANK);
		user.setActivated(true);
		

      UserAddress address = mapper.map(dto, UserAddress.class);
		BloodBank bank = mapper.map(dto, BloodBank.class);
		address.setUser(user);
		//user.setAddress(address);
		
		// camp.setId(null);
		System.out.println(user.getId());
		
		addRepo.save(address);
		User use = userRepository.save(user);
		bank.setUserId(use.getId());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(bankRepo.save(bank));
  }
  
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteBank(@PathVariable Long id) {
    System.out.println("Bank ID : " + id);
    return ResponseEntity.ok(bankService.deleteById(id));
  }
}
