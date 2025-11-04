package com.example.LMS.services.impl;

import com.example.LMS.dtos.LoanRequestDto;
import com.example.LMS.dtos.LoanResponseDto;
import com.example.LMS.exceptions.BusinessLogicException;
import com.example.LMS.mappers.LoanMapper;
import com.example.LMS.models.Loan;
import com.example.LMS.models.Student;
import com.example.LMS.repositories.BookRepository;
import com.example.LMS.repositories.LoanRepository;
import com.example.LMS.repositories.StudentRepository;
import com.example.LMS.services.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Transactional
public class LoanServiceImpl implements LoanService {

    private final LoanMapper loanMapper;
    private final LoanRepository loanRepository;
    private final StudentRepository studentRepository;
    private final BookRepository bookRepository;

    @Override
    public LoanResponseDto makeABorrow(LoanRequestDto request) throws BusinessLogicException {

        Student student = studentRepository.findByEmail(request.studentEmail()).orElseThrow((((() -> new IllegalArgumentException(format("Student with this email: %s not found", request.studentEmail()))))));

        if(!bookRepository.existsByTitle(request.bookTitle())){
            throw new IllegalArgumentException(format("Book with this title: %s not found", request.bookTitle()));
        }

        for(Loan loan : student.getLoans()){
            if(loan.isActive()){
                throw new BusinessLogicException(format("Student with this email: %s has an outstanding loan  ", request.studentEmail()));
            }
        }

        Loan loan = Loan
                .builder()
                .loanDate(LocalDate.now())
                .returnDate(null)
                .isActive(true)
                .book(bookRepository.findByTitle(request.bookTitle()))
                .student(student)
                .build();

        return loanMapper.toDto(loanRepository.save(loan));
    }

    @Override
    public LoanResponseDto getLoanById(Long id) {
        return loanMapper.toDto(findLoanById(id));
    }

    @Override
    public List<LoanResponseDto> getAllLoans() {
        return loanMapper.toDtoList(loanRepository.findAll());
    }

    @Override
    public LoanResponseDto makeAReturn(Long id) {

        Loan loan = findLoanById(id);

        loan.setReturnDate(LocalDate.now());

        return loanMapper.toDto(loan);
    }

    @Override
    public List<LoanResponseDto> getAllLoansByStudent(String email) {

        Student student = studentRepository.findByEmail(email).orElseThrow((((() -> new IllegalArgumentException(format("Student with this email: %s not found", email))))));

        List <Loan> loans = loanRepository.getAllLoansByStudent(student);

        return loanMapper.toDtoList(loans);
    }

    @Override
    public void deleteById(Long id) {

    }

    private Loan findLoanById(Long id){
        return loanRepository.findById(id).orElseThrow(()-> new RuntimeException("Loan not found"));
    }
}
