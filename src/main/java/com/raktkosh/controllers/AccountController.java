package com.raktkosh.controllers;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raktkosh.dao.BankRepository;
import com.raktkosh.dao.IBDCampRepo;
import com.raktkosh.dao.UserRepository;
import com.raktkosh.dto.MessageResponse;
import com.raktkosh.dto.SigninDTO;
import com.raktkosh.dto.SignupDTO;
import com.raktkosh.enums.Role;
import com.raktkosh.pojos.BDCamp;
import com.raktkosh.pojos.BloodBank;
import com.raktkosh.pojos.User;
import com.raktkosh.security.UserDetailsImpl;
import com.raktkosh.services.IBDCampService;
import com.raktkosh.services.IVerificationService;
import com.raktkosh.services.UserDetailsServiceImpl;
import com.raktkosh.utils.JWTUtils;

@RestController
@RequestMapping("/account")
@CrossOrigin(origins = { "${com.raktkosh.ORIGINS}" })
public class AccountController {
  
  private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
  
  @Autowired
  private IBDCampService campService;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserRepository userRepository;
  
  @Autowired
  private UserDetailsServiceImpl userService;

  @Autowired
  private PasswordEncoder passwordEncoder;
  
  @Autowired
  private ModelMapper mapper;
  
  @Autowired
  private BankRepository bankRepo;
  
  @Autowired
  private IBDCampRepo campRepo;
  
  @Autowired
  private IVerificationService verification;

  @Autowired
  private JWTUtils jwtUtils;

  @PostMapping("/signup")
  public ResponseEntity<?> signup(@RequestBody @Valid SignupDTO signupRequest) {
    if (userRepository.existsByUsername(signupRequest.getUsername())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Username is already taken."));
    }

    if (userRepository.existsByEmail(signupRequest.getEmail())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Email is already in use."));
    }

    signupRequest.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
    User user = mapper.map(signupRequest,User.class);
    User use=userRepository.save(user);

    if(signupRequest.getRole().equals("BLOOD_BANK")) {
    	BloodBank bank = mapper.map(signupRequest, BloodBank.class);
    	//bank.setRegID(use.getId().toString());
    	bank.setUserId(use.getId());
    	bankRepo.save(bank);
    }
    if(signupRequest.getRole().equals("CAMP")) {
    	BDCamp camp = mapper.map(signupRequest, BDCamp.class);
    	camp.setUserId(use.getId());
    	//camp.setId(null);
    	campRepo.save(camp);
    }
    
    //User user = User.build(signupRequest);
    //user.setRole(Role.USER);

    
    try {
      verification.sendVerificationMail(user.getEmail());
    } catch (MessagingException e) {
      logger.error("Failed to send verification email. " + e.getMessage());
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Registeration successfull."));
  }

  @PostMapping("/signin")
  public ResponseEntity<?> signup(@RequestBody @Valid SigninDTO signinRequest) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(signinRequest.getUsername(), signinRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJWTToken(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    userDetails.setToken(jwt);

    return ResponseEntity.ok(userDetails);
  }
  
  @GetMapping("/verify/{token}")
  public ResponseEntity<?> verify(@PathVariable String token) {
    return ResponseEntity.ok(verification.verifyEmail(token));
  }
  
  @GetMapping("/info/{token}")
  public ResponseEntity<?> getUserInfo(@PathVariable String token) {
    if (token != null && jwtUtils.isValidToken(token)) {
      String username = jwtUtils.getUsernameFromToken(token);
      UserDetails userDetails = userService.loadUserByUsername(username);
      return ResponseEntity.ok(userDetails);
    }
    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new MessageResponse("Invalid Token."));
  }
  
  @GetMapping("/profile/{id}")
  public ResponseEntity<?> getProfile(@PathVariable Long id) {
    return ResponseEntity.ok(userRepository.findById(id));
  } 
}
