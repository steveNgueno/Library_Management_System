package com.example.LMS.service.impl;

import com.example.LMS.domain.request.LoanRequestDto;
import com.example.LMS.domain.response.LoanResponseDto;
import com.example.LMS.exception.BookNotFoundException;
import com.example.LMS.exception.BusinessLogicException;
import com.example.LMS.exception.UserNotFoundException;
import com.example.LMS.mapper.LoanMapper;
import com.example.LMS.domain.model.Book;
import com.example.LMS.domain.model.Loan;
import com.example.LMS.domain.model.Student;
import com.example.LMS.repository.BookRepository;
import com.example.LMS.repository.LoanRepository;
import com.example.LMS.repository.StudentRepository;
import com.example.LMS.service.LoanService;
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
            throw new BusinessLogicException(format("Book with this title : %s not available", request.bookTitle()), "BOOK_NOT_AVAILABLE");
        }

        //Checking if the student has an outstanding loan
        for(Loan loan : student.getLoans()){
            if(loan.isActive()){
                throw new BusinessLogicException(format("Student with this email: %s has an outstanding loan  ", request.studentEmail()),"STUDENT_NOT_ELIGIBLE");
            }
        }

        //Updating of the number of copies of the book and saving the book in the DB
        book.setAvailableCopies(copies - 1);
        bookRepository.save(book);

        //Building and saving the current loan in DB
        Loan loan = loanMapper.toEntity(request);
        loan.setLoanDate(LocalDate.now());
        loan.setExpectedReturnDate(LocalDate.now().plusDays(30));
        loan.setActive(true);
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

        Loan loan = loanRepository.findByStudentAndBookAndActive(student,book, true).orElseThrow(() -> new BusinessLogicException("This student doesn't have an outstanding loan for this book","ACTIVE_LOAN_NOT_FOUND"));

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
        return loanRepository.findById(id).orElseThrow(()-> new BusinessLogicException("Loan not found", "LOAN_NOT_FOUND"));
    }

    private Student findStudentByEmail(String email){
        return studentRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

    }

    private Book findBookByTitle(String title){
        return bookRepository.findByTitle(title).orElseThrow(()-> new BookNotFoundException(title));
    }
}
