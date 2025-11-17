package com.example.LMS.service.impl;

import com.example.LMS.domain.request.GenderRequestDto;
import com.example.LMS.domain.response.GenderResponseDto;
import com.example.LMS.exception.BusinessLogicException;
import com.example.LMS.mapper.GenderMapper;
import com.example.LMS.domain.model.Gender;
import com.example.LMS.repository.GenderRepository;
import com.example.LMS.service.GenderService;
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

        if(genderRepository.existsByName(request.name())){
            throw new BusinessLogicException("This genre already exists","GENDER_ALREADY_EXISTS");
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

        Gender gender = findGenderById(id);

        if(!gender.getBooks().isEmpty()){
            throw new BusinessLogicException("This gender cannot be deleted", "NOT-REMOVABLE_GENDER");
        }

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
        return genderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(format("Gender with id: %s not found", id)));
    }
}
