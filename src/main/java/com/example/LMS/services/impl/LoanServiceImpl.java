package com.example.LMS.services.impl;

import com.example.LMS.dtos.LoanRequestDto;
import com.example.LMS.dtos.LoanResponseDto;
import com.example.LMS.exceptions.BusinessLogicException;
import com.example.LMS.mappers.LoanMapper;
import com.example.LMS.models.Book;
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

        //Getting the student by his email in the DB
        Student student = findStudentByEmail(request.studentEmail());

        //Getting the book by his title in the DB
        Book book = findBookByTitle(request.bookTitle());

        //Number of copies of the book
        int copies = book.getNumOfCopies();

        //Checking if it has available copy of this book
        if(copies == 0){
            throw new BusinessLogicException(format("Book with this title : %s not available", request.bookTitle()));
        }

        //Checking if the student has an outstanding loan
        for(Loan loan : student.getLoans()){
            if(loan.isActive()){
                throw new BusinessLogicException(format("Student with this email: %s has an outstanding loan  ", request.studentEmail()));
            }
        }

        //Updating of the number of copies of the book and saving the book in the DB
        book.setNumOfCopies(copies - 1);
        bookRepository.save(book);

        //Building and saving the current loan in DB
        LoanResponseDto response = new LoanResponseDto(LocalDate.now(),null,true,request.bookTitle(),request.studentEmail());

        Loan loan = loanMapper.toEntity(response);

        loan.setStudent(student);
        loan.setBook(book);

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
    public LoanResponseDto makeAReturn(LoanRequestDto request) {

        Student student = findStudentByEmail(request.studentEmail());

        Book book = findBookByTitle(request.bookTitle());

        Loan loan = loanRepository.findByStudentAndBookAndActive(student,book, true).orElseThrow(()-> new BusinessLogicException("This Student does not have outstanding loan for this book"));

       book.setNumOfCopies(book.getNumOfCopies() + 1);
       bookRepository.save(book);

        loan.setReturnDate(LocalDate.now());
        loan.setActive(false);

        return loanMapper.toDto(loanRepository.save(loan));
    }

    @Override
    public List<LoanResponseDto> getAllLoansByStudent(String email) {

        List <Loan> loans = loanRepository.getAllLoansByStudent(findStudentByEmail(email));

        return loanMapper.toDtoList(loans);
    }

    @Override
    public void deleteById(Long id) {

    }

    private Loan findLoanById(Long id){
        return loanRepository.findById(id).orElseThrow(()-> new RuntimeException("Loan not found"));
    }

    private Student findStudentByEmail(String email){
        return studentRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException(format("Student with this email: %s not found", email)));

    }

    private Book findBookByTitle(String title){
        return bookRepository.findByTitle(title).orElseThrow(()-> new IllegalArgumentException(format("Book with this title: %s not found",title)));
    }
}
