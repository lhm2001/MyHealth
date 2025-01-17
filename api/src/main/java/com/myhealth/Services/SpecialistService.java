package com.myhealth.Services;

import javax.transaction.Transactional;

import com.myhealth.Common.EntityDtoConverter;
import com.myhealth.Dto.Requests.SpecialistDtoRequest;
import com.myhealth.Dto.Responses.SpecialistDtoResponse;
import com.myhealth.Entities.Profile;
import com.myhealth.Entities.Specialist;
import com.myhealth.Repositories.ProfileRepository;
import com.myhealth.Repositories.SpecialistRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class SpecialistService {
	@Autowired
	SpecialistRepository specialistRepository;

	@Autowired
	ProfileRepository profileRepository;

	@Autowired
	EntityDtoConverter entityDtoConverter;

	public SpecialistDtoResponse getSpecialist(Long id) {
		Specialist specialist = specialistRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Specialist not found"));
		return entityDtoConverter.convertSpecialistToDto(specialist);
	}

	public SpecialistDtoResponse postSpecialist(SpecialistDtoRequest specialistDtoRequest) {
		Profile profile = profileRepository.findById(specialistDtoRequest.getProfileId())
				.orElseThrow(() -> new RuntimeException("Profile id specified not found"));
		Specialist specialist = new Specialist(profile, specialistDtoRequest);
		Specialist specialist2 = specialistRepository.save(specialist);
		return entityDtoConverter.convertSpecialistToDto(specialist2);
	}

}
