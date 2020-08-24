package com.example.demo.controller;

import com.example.demo.dto.DatePersonDto;
import com.example.demo.dto.ExceptionDto;
import com.example.demo.entity.PersonEntity;
import com.example.demo.exception.ErrorTokenException;
import com.example.demo.repository.ExceptionRepository;
import com.example.demo.repository.PersonRepository;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    private final ExceptionRepository exceptionRepository;
    private final PersonService personService;
    private final PersonRepository personRepository;

    @Autowired
    public PersonController(ExceptionRepository exceptionRepository, PersonService personService, PersonRepository personRepository) {
        this.exceptionRepository = exceptionRepository;
        this.personService = personService;
        this.personRepository = personRepository;
    }

    @RequestMapping(value = "/profiles/set", method = RequestMethod.POST)
    public long create(@RequestBody DatePersonDto datePersonDto, @RequestHeader("Authorization") String authHeader) {
        if (getTokenFromAuthHeader(authHeader).equals("secret")) {
            return personService.create(datePersonDto);
        } else throw new ErrorTokenException();

    }

    @RequestMapping(value = "/profiles", method = RequestMethod.GET)
    public List<PersonEntity> profiles(@RequestHeader("Authorization") String authHeader) {
        if (getTokenFromAuthHeader(authHeader).equals("secret")) {
            return personRepository.findAll();
        } else throw new ErrorTokenException();
    }

    @GetMapping("/profiles/{id}")
    public PersonEntity getByID(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
        if (getTokenFromAuthHeader(authHeader).equals("secret")) {
            return personService.getPersonById(id);
        } else throw new ErrorTokenException();
    }

    @RequestMapping(value = "/profiles/last", method = RequestMethod.GET)
    public PersonEntity last(@RequestHeader("Authorization") String authHeader) {
        if (getTokenFromAuthHeader(authHeader).equals("secret")) {
            return personRepository.findFirstByOrderByIdDesc();
        } else throw new ErrorTokenException();
    }

    @RequestMapping(value = "/profiles/get", method = RequestMethod.POST)
    public PersonEntity getByEmail(@RequestBody String email, @RequestHeader("Authorization") String authHeader) {
        if (getTokenFromAuthHeader(authHeader).equals("secret")) {
            return personService.getPersonByEmail(email);
        } else throw new ErrorTokenException();
    }

    @RequestMapping(value = "/error/last", method = RequestMethod.GET)
    public ExceptionDto getLastException(@RequestHeader("Authorization") String authHeader) {
        if (getTokenFromAuthHeader(authHeader).equals("secret")) {
            return personService.getMsg();
        } else throw new ErrorTokenException();
    }

    private String getTokenFromAuthHeader(String authHeader) {
        return authHeader.substring(authHeader.indexOf(" ") + 1);
    }

}
