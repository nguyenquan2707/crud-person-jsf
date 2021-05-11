package br.com.leandro.crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.leandro.crud.data.Person;
import br.com.leandro.crud.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
public class PersonRESTController {

	@Autowired
	private PersonService personService;

	@Operation (summary = "Return all registered people" )
	@GetMapping("/REST/person")
	public List<Person> findAll() {
		return personService.findAll();
	}

	@Operation (summary = "Save or update a person" )
	@PostMapping("/REST/person")
	@ResponseStatus(value = HttpStatus.CREATED)
	public Person save(@RequestBody Person person) {
		return personService.save(person);
	}

	@Operation (summary = "Return a person by Id" )
	@GetMapping("/REST/person/{id}")
	public Person findById(@PathVariable Long id) {
		return personService.findById(id);
	}

	@Operation (summary = "Replace a person by Id" )
	@PutMapping("/REST/person/{id}")
	public Person replacePerson(@RequestBody Person newPerson, @PathVariable Long id) {
		return personService.replace(newPerson, id);
	}
	
	@Operation (summary = "Delete a person by Id" )
	@DeleteMapping("/REST/person/{id}")
	public void delete(@PathVariable Long id) {
		personService.deleteById(id);
	}
}