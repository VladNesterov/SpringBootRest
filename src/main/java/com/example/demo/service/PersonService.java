package com.example.demo.service;

import com.example.demo.dto.DatePersonDto;
import com.example.demo.dto.ExceptionDto;
import com.example.demo.entity.ExceptionEntity;
import com.example.demo.entity.PersonEntity;
import com.example.demo.exception.EmailExistsException;
import com.example.demo.exception.PersonNotFountException;
import com.example.demo.exception.WrongEmailException;
import com.example.demo.repository.ExceptionRepository;
import com.example.demo.repository.PersonRepository;
import com.example.demo.validation.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonService {

    private final ExceptionRepository exceptionRepository;
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(ExceptionRepository exceptionRepository, PersonRepository personRepository) {
        this.exceptionRepository = exceptionRepository;
        this.personRepository = personRepository;
    }


    public long create(DatePersonDto datePersonDto) {
        EmailValidator emailValidator = new EmailValidator();

        if (personRepository.findByEmailContainingIgnoreCase(datePersonDto.getEmail()) != null) {
            saveException("Email exists");
            throw new EmailExistsException();
        }

        if (!emailValidator.validate(datePersonDto.getEmail())) {
            saveException("Wrong email");
            throw new WrongEmailException();
        }

        PersonEntity personEntity = new PersonEntity();
        personEntity.setAge(datePersonDto.getAge());
        personEntity.setEmail(datePersonDto.getEmail());
        personEntity.setName(datePersonDto.getName());
        personEntity.setCreated(new Date());
        personRepository.save(personEntity);

        return personEntity.getId();
    }

    public PersonEntity getPersonById(long id) {
        PersonEntity personEntity = personRepository.findPersonById(id);
        if (personEntity == null) {
            saveException("Person not found");
            throw new PersonNotFountException();
        }
        return personEntity;

    }

    public PersonEntity getPersonByEmail(String email) {
        PersonEntity personEntity = personRepository.findByEmailContainingIgnoreCase(email);
        if (personEntity == null) {
            saveException("Person not found");
            throw new PersonNotFountException();
        }
        return personEntity;

    }

    public ExceptionDto getMsg() {
        ExceptionEntity exceptionEntity = exceptionRepository.findFirstByOrderByIdDesc();
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setCreated(exceptionEntity.getCreated());
        exceptionDto.setMsg(exceptionEntity.getMsg());
        return exceptionDto;
    }

    private void saveException(String msg) {
        ExceptionEntity exceptionEntity = new ExceptionEntity();
        exceptionEntity.setMsg("Person not found");
        exceptionEntity.setCreated(new Date());
        exceptionRepository.save(exceptionEntity);
    }


}
