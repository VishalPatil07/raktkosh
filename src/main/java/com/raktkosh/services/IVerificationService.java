package com.raktkosh.services;

import javax.mail.MessagingException;

import com.raktkosh.pojos.User;

public interface IVerificationService {
  void sendVerificationMail(String email) throws MessagingException;
  User verifyEmail(String email);
}
