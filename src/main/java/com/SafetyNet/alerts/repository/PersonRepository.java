package com.SafetyNet.alerts.repository;

import java.util.List;

import com.SafetyNet.alerts.model.Person;

public interface PersonRepository {
    List<Person> getAllPersons();
    Person getPersonById(String id);
    void addPerson(Person person);
    void updatePerson(Person person);
    void deletePerson(String firstName, String lastName);
	List<Person> findByFirstNameAndLastName(String firstName, String lastName);
	List<Person> findByAddress(String address);
	List<Person> findByAddressAndNotId(String address, String id);
	List<Person> findByCity(String city);
}
