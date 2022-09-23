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
import com.raktkosh.dto.BloodBankDTO;
import com.raktkosh.dto.JwtDto;
import com.raktkosh.dto.MessageResponse;
import com.raktkosh.dto.SigninDTO;
import com.raktkosh.exceptions.ResourceNotFoundException;
import com.raktkosh.pojos.BDCamp;
import com.raktkosh.pojos.BloodBank;
import com.raktkosh.pojos.User;
import com.raktkosh.security.UserDetailsImpl;
import com.raktkosh.services.IBDCampService;
import com.raktkosh.services.IVerificationService;
import com.raktkosh.services.UserDetailsServiceImpl;
import com.raktkosh.utils.JWTUtils;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/account")
@CrossOrigin(origins = { "${com.raktkosh.ORIGINS}" })
@Slf4j
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
  public ResponseEntity<?> signup(@RequestBody @Valid BloodBankDTO signupRequest) {
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
    	//bank.setRegID("10"+use.getId().toString());
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
    //
	  Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(signinRequest.getUsername(), signinRequest.getPassword()));
    //
    SecurityContextHolder.getContext().setAuthentication(authentication);
    //
    String jwt = jwtUtils.generateJWTToken(authentication);
    //
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    userDetails.setToken(jwt);
   // userDetails.getId();
   // ;

    return ResponseEntity.ok(userDetails);
  }
  
  @GetMapping("/verify/{token}")
  public ResponseEntity<?> verify(@PathVariable String token) {
    return ResponseEntity.ok(verification.verifyEmail(token));
  }
  
  @GetMapping("profile/{token}")
  public ResponseEntity<?> getUserInfowithjwt(@PathVariable String token) {
	  //token="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY2Mzg3MzAxOSwiaXNzIjoiUkFLVEtPU0hfU0VSVkVSIiwiZXhwIjoxNjYzOTU5NDE5fQ.cDYQfySFjyrMNoMx5iHra-PmjI421fq4ZilMF00lY06hUL-Do9AYOV8g_mLGLE9zxwa3WB07WwTJbxkPyK4DZA";
    if (token != null && jwtUtils.isValidToken(token)) {
      String username = jwtUtils.getUsernameFromToken(token);
      UserDetailsImpl userDetails = userService.loadUserByUsername(username);
      
     // System.out.println(userDetails.getUsername());
    // System.out.println(userDetails.toString());
     // System.out.println(userDetails.getId().toString());
//      if(userDetails.getId()==null) {
//    	  System.out.println("id is null");
//      }
      //log.info(userDetails.getAuthorities().toString());
     // log.info(username);
   //  log.info("username"+userDetails.getUsername());
     User user= userRepository.findByUsername(userDetails.getUsername()).orElseThrow(()-> new ResourceNotFoundException("no such entity with this username"));
     //log.info(user.toString());
      return ResponseEntity.ok(user);
    }
    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new MessageResponse("Invalid Token."));
  }
  
  @GetMapping("/info/{token}")
  public ResponseEntity<?> getUserInfo(@PathVariable String token) {
	  //token="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY2Mzg3MzAxOSwiaXNzIjoiUkFLVEtPU0hfU0VSVkVSIiwiZXhwIjoxNjYzOTU5NDE5fQ.cDYQfySFjyrMNoMx5iHra-PmjI421fq4ZilMF00lY06hUL-Do9AYOV8g_mLGLE9zxwa3WB07WwTJbxkPyK4DZA";
    if (token != null && jwtUtils.isValidToken(token)) {
      String username = jwtUtils.getUsernameFromToken(token);
      UserDetails userDetails = userService.loadUserByUsername(username);
      System.out.println(userDetails.getUsername());
      System.out.println(userDetails.toString());
     
      return ResponseEntity.ok(userDetails);
    }
    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new MessageResponse("Invalid Token."));
  }
  
//  @GetMapping("/profile/{id}")
//  public ResponseEntity<?> getProfile(@PathVariable Long id) {
//    return ResponseEntity.ok(userRepository.findById(id));
//  } 
}
