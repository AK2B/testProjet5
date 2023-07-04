package com.SafetyNet.alerts.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SafetyNet.alerts.dao.PersonDAO;
import com.SafetyNet.alerts.models.Person;

@Service
public class PersonService {

	private final PersonDAO personDAO;
	
	@Autowired
	public PersonService(PersonDAO personDAO) {
		this.personDAO = personDAO;
	}

	// Business logic

	public List<Person> getAllPersons() {
		return personDAO.getAllPersons();
	}

	public Person getPersonById(String id) {
		return getAllPersons().stream().filter(person -> person.getId().equals(id)).findFirst()
				.orElse(null);
	}

	public List<Person> getPersonByAddress(String address) {
		return getAllPersons().stream().filter(person -> person.getAddress().equals(address))
				.collect(Collectors.toList());
	}

	public List<Person> getPersonByCity(String city) {
		return getAllPersons().stream().filter(person -> person.getCity().equals(city))
				.collect(Collectors.toList());
	}

	public void addPerson(Person person) {
		personDAO.addPerson(person);
	}

	public void updatePerson(Person person) {
		personDAO.updatePerson(person);
	}

	public void deletePerson(String firstName, String lastName) {
		personDAO.deletePerson(firstName, lastName);
	}
}
