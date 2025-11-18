package com.example.LMS.service.impl;

import com.example.LMS.domain.Enum.Role;
import com.example.LMS.domain.model.Admin;
import com.example.LMS.domain.request.AdminRequestDto;
import com.example.LMS.domain.response.AdminResponseDto;
import com.example.LMS.exception.BusinessLogicException;
import com.example.LMS.exception.UserNotFoundException;
import com.example.LMS.mapper.AdminMapper;
import com.example.LMS.repository.AdminRepository;
import com.example.LMS.repository.StudentRepository;
import com.example.LMS.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;
    private final AdminMapper adminMapper;

    @Override
    public AdminResponseDto save(AdminRequestDto request) {

        if(adminRepository.existsByEmail(request.email()) || studentRepository.existsByEmail(request.email())){
            throw new BusinessLogicException("this email already exists","EMAIL_ALREADY_EXISTS");
        }
        if(adminRepository.existsByPhone(request.phone()) || adminRepository.existsByPhone(request.phone())){
            throw new BusinessLogicException("this phone number already exists", "PHONE_ALREADY_EXISTS");
        }
        if(adminRepository.existsByStaffId(request.staffId())){
            throw new BusinessLogicException("this staff ID already exists", "STAFF_ID_ALREADY_EXISTS");
        }

        Admin admin = adminMapper.toEntity(request);

        admin.setRole(Role.ADMIN);

        Admin savedAdmin = adminRepository.save(admin);

        return adminMapper.toDto(savedAdmin);
    }

    @Override
    public AdminResponseDto getById(Long id) {

        return adminMapper.toDto(findById(id));
    }

    @Override
    public List<AdminResponseDto> getAll() {

        return adminMapper.toDtoList(adminRepository.findAll());
    }

    @Override
    public AdminResponseDto update(Long id, AdminRequestDto request) {

        Admin admin = findById(id);

        admin.setFirstname(request.firstname() == null || request.firstname().isBlank() ? admin.getFirstname(): request.firstname());
        admin.setLastname(request.lastname() == null || request.lastname().isBlank() ? admin.getLastname(): request.lastname());
        admin.setBirthday(request.birthday() == null ? admin.getBirthday(): request.birthday());
        admin.setPhone(request.phone() == null || request.phone().isBlank() ? admin.getPhone() : request.phone());
        admin.setPosition(request.position() == null || request.position().isBlank() ? admin.getPosition() : request.position());
        admin.setStaffId(request.staffId() == null || request.staffId().isBlank() ? admin.getStaffId() : request.staffId());

        return adminMapper.toDto(adminRepository.save(admin));
    }

    @Override
    public void delete(Long id) {

        adminRepository.deleteById(id);
    }

    private Admin findById(Long id){
        return adminRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
}
