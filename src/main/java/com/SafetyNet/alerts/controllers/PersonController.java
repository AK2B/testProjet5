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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/person")
@Tag (name = "person" , description = "Person API")
public class PersonController {
    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    @Operation(summary = "Get all persons")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Success", content = { @Content(schema = @Schema(implementation = Person.class), mediaType = "application/json") }),
    })
    public ResponseEntity<List<Person>> getAllPersons() {
        List<Person> persons = personService.getAllPersons();
        return ResponseEntity.ok(persons);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a person by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Success", content = { @Content(schema = @Schema(implementation = Person.class), mediaType = "application/json") }),
        @ApiResponse(responseCode = "404", description = "Person not found")
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
    @Operation(summary = "Add a new person")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Person created")
    })
    public ResponseEntity<String> addPerson(@RequestBody Person person) {
        personService.addPerson(person);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a person")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Person updated"),
        @ApiResponse(responseCode = "404", description = "Person not found")
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
    @Operation(summary = "Delete a person")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Person deleted")
    })
    public ResponseEntity<String> deletePerson(@PathVariable String firstName, @PathVariable String lastName) {
        personService.deletePerson(firstName, lastName);
        return ResponseEntity.ok().build();
    }
}