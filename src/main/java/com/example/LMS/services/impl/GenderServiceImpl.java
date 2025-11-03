package com.example.LMS.services.impl;

import com.example.LMS.dtos.GenderRequestDto;
import com.example.LMS.dtos.GenderResponseDto;
import com.example.LMS.mappers.GenderMapper;
import com.example.LMS.models.Gender;
import com.example.LMS.repositories.GenderRepository;
import com.example.LMS.services.GenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Transactional
public class GenderServiceImpl implements GenderService {

    private final GenderRepository genderRepository;
    private final GenderMapper genderMapper;


    @Override
    public GenderResponseDto save(GenderRequestDto request) {

        if(request.name() == null || request.name().isEmpty()){
            throw new IllegalArgumentException("gender's name must not be null or empty");
        }

        if(genderRepository.existsByName(request.name())){
            throw new IllegalArgumentException("This genre already exists");
        }
        Gender savedGender = genderRepository.save(genderMapper.toEntity(request));

       return genderMapper.toDto(savedGender);
    }

    @Override
    public GenderResponseDto getById(Long id) {

        Gender gender = findGenderById(id);

        return genderMapper.toDto(gender);
    }

    @Override
    public void deleteById(Long id) {

        findGenderById(id);

        genderRepository.deleteById(id);

    }

    @Override
    public GenderResponseDto updateById(Long id, GenderResponseDto genderResponseDto) {
        return null;
    }

    @Override
    public List<GenderResponseDto> getAll() {
        return genderMapper.toDtoList(genderRepository.findAll());
    }

    private Gender findGenderById(Long id){
        return genderRepository.findById(id).orElseThrow(() -> new RuntimeException(format("Gender with this id: %s not found", id)));
    }
}
