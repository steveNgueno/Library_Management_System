package com.example.LMS.service.impl;

import com.example.LMS.domain.Enum.Action;
import com.example.LMS.domain.Enum.Status;
import com.example.LMS.domain.request.GenderRequestDto;
import com.example.LMS.domain.response.GenderResponseDto;
import com.example.LMS.exception.BusinessLogicException;
import com.example.LMS.mapper.GenderMapper;
import com.example.LMS.domain.model.Gender;
import com.example.LMS.repository.GenderRepository;
import com.example.LMS.service.GenderService;
import com.example.LMS.service.HistoryService;
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
    private final HistoryService historyService;

    @Override
    public GenderResponseDto save(GenderRequestDto request) {

        if(genderRepository.existsByName(request.name())){
            historyService.create(Status.FAILED, Action.ADD_GENDER,"Failed to add a new gender");
            throw new BusinessLogicException("This genre already exists","GENDER_ALREADY_EXISTS");
        }

        Gender savedGender = genderRepository.save(genderMapper.toEntity(request));
        historyService.create(Status.SUCCESS, Action.ADD_GENDER,String.format("The gender %s has been added",request.name()));

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
            historyService.create(Status.FAILED, Action.REMOVE_GENDER,String.format("Failed to remove the gender %s",gender.getName()));
            throw new BusinessLogicException("This gender cannot be deleted", "NOT-REMOVABLE_GENDER");
        }

        genderRepository.deleteById(id);
        historyService.create(Status.SUCCESS, Action.REMOVE_GENDER,String.format("The gender %s has been removed",gender.getName()));
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
