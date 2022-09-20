package com.raktkosh.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.raktkosh.dao.IBDCampRepo;
import com.raktkosh.exceptions.ResourceNotFoundException;
import com.raktkosh.pojos.BDCamp;
import com.raktkosh.request.dto.BDCampDto;
@Service
@Transactional
public class BDCampServiceImpl implements IBDCampService {
	@Autowired
	private IBDCampRepo bdCampRepo;
	@Autowired
	private ModelMapper mapper;

	@Override
	public List<BDCampDto> getAllCamps() {
		List<BDCamp> bdCamp=new ArrayList<BDCamp>();
			bdCamp =	bdCampRepo.findAll();
			List<BDCamp> campList=bdCamp.stream().filter(p->p.getCampDate().isAfter(LocalDate.now())).collect(Collectors.toList());
			
			List<BDCampDto> dtoList= new ArrayList<BDCampDto>();
			for(int i=0; i<campList.size();i++) {
				BDCamp camp= campList.get(i);
				BDCampDto dto= mapper.map(camp, BDCampDto.class);
				dtoList.add(dto);
				
			}
			//dtoList=campList.stream().forEach(p->(mapper.map(p, BDCamp.class))).collect(Collectors.toList());
		return dtoList;
	}

	@Override
	public BDCamp saveCamp(BDCamp camp) {
		
		return bdCampRepo.save(camp);
	}

	@Override
	public boolean deleteCampById(Long id) {
		bdCampRepo.deleteById(id);
		return true;
	}

	@Override
	public BDCamp updateCampById(Long id, BDCamp camp) {
		BDCamp cmp=bdCampRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("camp is not available with"+id));
		cmp.setCampDate(camp.getCampDate());
		cmp.setCity(camp.getCity());
		cmp.setDistrict(camp.getDistrict());
		cmp.setEndTime(camp.getEndTime());
		cmp.setStartTime(camp.getStartTime());
		cmp.setLocality(camp.getLocality());
		cmp.setName(camp.getName());
		cmp.setZipcode(camp.getZipcode());
		return bdCampRepo.save(cmp);
	}

}
