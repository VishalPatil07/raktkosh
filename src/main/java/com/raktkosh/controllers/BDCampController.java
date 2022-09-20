package com.raktkosh.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raktkosh.pojos.BDCamp;
import com.raktkosh.request.dto.BDCampDto;
import com.raktkosh.services.IBDCampService;

@RestController
@RequestMapping("/camp")
@CrossOrigin(origins = { "${com.raktkosh.ORIGINS}" })

public class BDCampController {
	@Autowired
	private IBDCampService campService;
	
	@Autowired
	private ModelMapper mapper;

	@GetMapping("/")
	public ResponseEntity<?> getAllCamps() {
		return ResponseEntity.status(HttpStatus.OK).body(campService.getAllCamps());
	}

	@PostMapping("/add")
	public ResponseEntity<?> saveCamp(@RequestBody BDCampDto dto) {
        BDCamp camp = mapper.map(dto, BDCamp.class);
        System.out.println(dto.toString());
		return ResponseEntity.status(HttpStatus.CREATED).body(campService.saveCamp(camp));
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteCamp(@PathVariable Long id){
		return ResponseEntity.status(HttpStatus.OK).body(campService.deleteCampById(id));
	}
	
	@PostMapping("/update/{id}")
	public ResponseEntity<?> updateCamp(@PathVariable Long id,@RequestBody BDCampDto dto){
		BDCamp camp = mapper.map(dto, BDCamp.class);
		return ResponseEntity.status(HttpStatus.OK).body(campService.updateCampById(id,camp));
	}
}
