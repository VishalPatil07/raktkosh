package com.raktkosh.services;

import java.time.LocalDateTime;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.raktkosh.components.EMailSender;
import com.raktkosh.dao.EmailActivationRepository;
import com.raktkosh.dao.UserRepository;
import com.raktkosh.exceptions.ActivationError;
import com.raktkosh.pojos.EmailActivationCodes;
import com.raktkosh.pojos.User;

import net.bytebuddy.utility.RandomString;

@Service
@Transactional
public class VerificationService implements IVerificationService {

  @Autowired
  private EmailActivationRepository emailRepo;
  
  @Autowired
  private UserRepository userRepo;
  
  @Autowired
  private PasswordEncoder hash;
  
  @Value("${com.raktkosh.JWT_VALIDITY}")
  private int validity;
  
  @Autowired
  private EMailSender emailSender;
  
  @Override
  public void sendVerificationMail(String email) throws MessagingException {
    String code = hash.encode(RandomString.make(64));
    LocalDateTime expiry = LocalDateTime.now();
    emailRepo.save(new EmailActivationCodes(email, code, expiry));
    emailSender.sendVerificationEmail(email, code);
  }

  @Override
  public User verifyEmail(String token) {
    EmailActivationCodes code = emailRepo.findByCode(token).orElseThrow(() -> new ActivationError("Invalid Code"));
    User user = userRepo.findByEmail(code.getEmail()).orElseThrow(() -> new ActivationError("User not found"));
    user.setActivated(true);
    userRepo.save(user);
    emailRepo.deleteById(code.getId());
    return user;
  }
}
