package com.SafetyNet.alerts.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.SafetyNet.alerts.config.DataConfig;
import com.SafetyNet.alerts.models.Person;

@Repository
public class PersonDAO {

    private final List<Person> persons;
    @Autowired
    public PersonDAO(DataConfig dataConfig) {
        this.persons = dataConfig.getPersons();
    }

    // CRUD operations only

    public List<Person> getAllPersons() {
        return persons;
    }

    public void addPerson(Person person) {
        persons.add(person);
    }

    public void updatePerson(Person person) {
        persons.stream()
                .filter(p -> p.getId().equals(person.getId()))
                .findFirst()
                .ifPresent(p -> {
                    int index = persons.indexOf(p);
                    persons.set(index, person);
                });
    }

    public void deletePerson(String firstName, String lastName) {
        persons.removeIf(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName));
    }
}
