package com.raktkosh.services;

import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raktkosh.dao.BankRepository;
import com.raktkosh.dao.UserAddressRepository;
import com.raktkosh.dto.BloodBankDTO;
import com.raktkosh.pojos.BloodBank;
import com.raktkosh.pojos.User;
import com.raktkosh.pojos.UserAddress;

@Service
@Transactional
public class BloodBankServiceImpl implements IBloodBankService {

	@Autowired
	private BankRepository bankRepo;
	
//	@Autowired
//	private BankAddressRepository addRepo;
	@Autowired
	private UserAddressRepository addRepo;
	
	@Autowired
	private ModelMapper mapper;

	// add blood bank
	@Override
	public BloodBank saveBankDetails(BloodBankDTO bloodBank) {

	
				//bankRepo.save(bloodBank.buildBloodBank());
		User user = mapper.map(bloodBank, User.class);
		UserAddress address = mapper.map(bloodBank, UserAddress.class);
		BloodBank bank = mapper.map(bloodBank, BloodBank.class);
		
		address.setUser(user);
		
		//bloodBank.buildBankAddress();
		//address.setBank(bank);
		//addRepo.save(address);
		return bank;
	}

	// get blood bank details by bankId
	@Override
	public BloodBank getBankDetailsById(Long bankId) {

		return bankRepo.findById(bankId).orElseThrow();
	}

	// get blood bank details by bankName
	@Override
	public BloodBank getBankDetailsByBankName(String bankName) {

		return bankRepo.findByName(bankName).orElseThrow();
	}

	// delete blood bank details by bankId
	@Override
	public boolean deleteBankDetails(Long bankId) {
		bankRepo.deleteById(bankId);
		return true;
	}

	// update bank details
	@Override
	public BloodBank updateBankDetails(BloodBank bloodBank) {
		return bankRepo.save(bloodBank);
	}

  @Override
  public List<BloodBank> getAllBanks() {
    return bankRepo.findAll();
  }

  @Override
  public boolean deleteById(Long id) {
    bankRepo.deleteById(id);
    return true;
  }

}
