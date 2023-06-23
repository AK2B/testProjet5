package com.SafetyNet.alerts.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SafetyNet.alerts.model.Person;
import com.SafetyNet.alerts.repository.PersonRepository;

@Service
public class PersonService {

    private final PersonRepository personRepository;  
    
    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;	
    }

    public List<Person> getAllPersons() {
        return personRepository.getAllPersons();
    }

    public Person getPersonById(String id) {
        return personRepository.getPersonById(id);
    }

    public void addPerson(Person person) {
        personRepository.addPerson(person);
    }

    public void updatePerson(Person person) {
        personRepository.updatePerson(person);
    }

    public void deletePerson(String firstName, String lastName) {
        personRepository.deletePerson(firstName, lastName);
    }
    
}