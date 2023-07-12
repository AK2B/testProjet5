package com.SafetyNet.alerts.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SafetyNet.alerts.models.Person;
import com.SafetyNet.alerts.services.PersonService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/person")
@Api(tags = "Person API")
public class PersonController {
    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    @ApiOperation("Get all persons")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Success", response = Person.class, responseContainer = "List")
    })
    public ResponseEntity<List<Person>> getAllPersons() {
        List<Person> persons = personService.getAllPersons();
        return ResponseEntity.ok(persons);
    }

    @GetMapping("/{id}")
    @ApiOperation("Get a person by ID")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Success", response = Person.class),
        @ApiResponse(code = 404, message = "Person not found")
    })
    public ResponseEntity<Person> getPersonById(@PathVariable String id) {       
        Person person = personService.getPersonById(id);
        if (person != null) {
            return ResponseEntity.ok(person);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ApiOperation("Add a new person")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Person created")
    })
    public ResponseEntity<String> addPerson(@RequestBody Person person) {
        personService.addPerson(person);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    @ApiOperation("Update a person")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Person updated"),
        @ApiResponse(code = 404, message = "Person not found")
    })
    public ResponseEntity<String> updatePerson(@PathVariable String id, @RequestBody Person person) {
        Person existingPerson = personService.getPersonById(id);
        if (existingPerson != null) {
            person.setId(id);
            personService.updatePerson(person);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{firstName}/{lastName}")
    @ApiOperation("Delete a person")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Person deleted")
    })
    public ResponseEntity<String> deletePerson(@PathVariable String firstName, @PathVariable String lastName) {
        personService.deletePerson(firstName, lastName);
        return ResponseEntity.ok().build();
    }
}