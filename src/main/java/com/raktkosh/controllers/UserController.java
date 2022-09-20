package com.raktkosh.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raktkosh.dao.UserAddressRepository;
import com.raktkosh.dao.UserRepository;
import com.raktkosh.pojos.User;
import com.raktkosh.pojos.UserAddress;
import com.raktkosh.services.IUserAddressService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = { "${com.raktkosh.ORIGINS}" })
public class UserController {
  
  @Autowired
  private UserRepository userRepository;
  
  @Autowired
  private UserAddressRepository addressRepository;
  
  @Autowired
  private IUserAddressService addressService;
  
  @PostMapping("/update/{id}")
  public ResponseEntity<?> updateProfile(@PathVariable Long id, @RequestBody User user) {
    user.setId(id);
    return ResponseEntity.ok(userRepository.save(user));
  }
  
  @PostMapping("/update/address/{id}")
  public ResponseEntity<?> updateAddress(@PathVariable Long id, @RequestBody UserAddress address) {
   // User user = userRepository.getById(id);
   // user.setId(id);
   // address.setUser(user);
    return ResponseEntity.ok(addressService.saveAddress(id, address));
  }
  
  @GetMapping("/address/{id}")
  public ResponseEntity<?> updateAddress(@PathVariable Long id) {
    return ResponseEntity.ok(addressRepository.findById(id));
  }
}
