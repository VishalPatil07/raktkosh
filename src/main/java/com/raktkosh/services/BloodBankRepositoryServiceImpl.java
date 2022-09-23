package com.raktkosh.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.raktkosh.dao.BankRepository;
import com.raktkosh.dao.BloodBankRepository;
import com.raktkosh.dao.UserRepository;
import com.raktkosh.dto.BloodBankRepositoryDTO;
import com.raktkosh.dto.BloodBankRepositoryIdDTO;
import com.raktkosh.exceptions.BloodRepositoryException;
import com.raktkosh.exceptions.ResourceNotFoundException;
import com.raktkosh.pojos.BloodBank;
import com.raktkosh.pojos.BloodRepository;
import com.raktkosh.pojos.BloodRepositoryID;
import com.raktkosh.pojos.User;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class BloodBankRepositoryServiceImpl implements IBloodBankRepositoryService {
  
  @Autowired
  private BloodBankRepository bloodBankRepo;
  
  @Autowired
  private BankRepository bankRepo;
  
  @Value("${com.raktkosh.blood.repository.NOT_FOUND}")
  private String repoNotFound;
  
  @Autowired
  private UserRepository userRepo;

  @Override
  public BloodRepository addRepository(BloodBankRepositoryDTO repository) {
    return bloodBankRepo.save(BloodRepository.build(repository));
  }

  @Override
  public boolean deleteRepositoryById(BloodBankRepositoryIdDTO dto) {
    bloodBankRepo.deleteById(BloodRepositoryID.build(dto));
    return true;
  }

  @Override
  public BloodRepository updateRepositoryById(BloodBankRepositoryIdDTO id, int quantity) {
    BloodRepository bloodRepo = bloodBankRepo.findById(BloodRepositoryID.build(id))
                                  .orElseThrow(() -> new BloodRepositoryException(repoNotFound));
    bloodRepo.setAvailability(quantity);
    return bloodBankRepo.save(bloodRepo);
  }

  @Override
  public List<BloodRepository> findByBloodBank(Long id) {
	  User user = userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("no such entity found"));
	 
    BloodBank bank =bankRepo.findById(user.getId()).orElseThrow(()->new ResourceNotFoundException("no such entity found"));
//    bank.setId(id);
    log.info(bank.toString());
    return bloodBankRepo.findByIdBank(bank);
  }

  @Override
  public List<BloodRepository> findByBloodTypeAndAntigen(BloodBankRepositoryIdDTO id) {
    return bloodBankRepo.findByIdTypeAndIdAntigen(id.getType(), id.getAntigen());
  }
}
