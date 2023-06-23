package com.SafetyNet.alerts.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.SafetyNet.alerts.model.Person;

@Repository
public class PersonRepositoryImpl implements PersonRepository {
    
	private final List<Person> persons;
		
	@Autowired
    public PersonRepositoryImpl(DataRepository dataRepository) throws IOException {
    	 persons = dataRepository.getData().getPersons();
    }
      
    @Override
    public List<Person> getAllPersons() {
    	return persons;
    }

    @Override
    public Person getPersonById(String id) {        
        for (Person person : persons) {
            if (person.getId().equals(id)) {
                return person;
            } 
        }
        return null;
    }
    
    @Override
    public List<Person> findByAddress(String address) {
        List<Person> matchingPersons = new ArrayList<>();

        for (Person person : persons) {
            if (person.getAddress().equals(address)) {
                matchingPersons.add(person);
            }
        }

        return matchingPersons;
    }
    
    @Override
    public List<Person> findByCity(String city) {
        List<Person> matchingCity = new ArrayList<>();

        for (Person person : persons) {
            if (person.getCity().equals(city)) {
                matchingCity.add(person);
            }
        }

        return matchingCity;
    }
    
    @Override
    public void addPerson(Person person) {
        persons.add(person);
    }

    @Override
    public void updatePerson(Person person) {
        for (int i = 0; i < persons.size(); i++) {
            if (persons.get(i).getId().equals(person.getId())) {
                persons.set(i, person);
                return;
            }
        }
    }

    @Override
    public void deletePerson(String firstName, String lastName) {
        persons.removeIf(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName));
    }

    @Override
    public List<Person> findByFirstNameAndLastName(String firstName, String lastName) {
        List<Person> foundPersons = new ArrayList<>();

        for (Person person : persons) {
            if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
                foundPersons.add(person);
            }
        }

        return foundPersons;
    }

    @Override
    public List<Person> findByAddressAndNotId(String address, String id) {
        List<Person> matchingPersons = new ArrayList<>();

        for (Person person : persons) {
            if (person.getAddress().equals(address) && !person.getId().equals(id)) {
                matchingPersons.add(person);
            }
        }
        return matchingPersons;
    }
}