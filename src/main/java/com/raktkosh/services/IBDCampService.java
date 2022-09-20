package com.raktkosh.services;

import java.util.List;

import com.raktkosh.pojos.BDCamp;
import com.raktkosh.request.dto.BDCampDto;

public interface IBDCampService {

	List<BDCampDto> getAllCamps();

	BDCamp saveCamp(BDCamp camp);

	boolean deleteCampById(Long id);

	BDCamp updateCampById(Long id, BDCamp camp);

}
