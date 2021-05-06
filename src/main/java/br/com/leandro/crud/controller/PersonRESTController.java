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

@RestController
public class PersonRESTController {

	@Autowired
	private PersonService personService;

	@GetMapping("/REST/person")
	public List<Person> findAll() {
		return personService.findAll();
	}

	@PostMapping("/REST/person")
	@ResponseStatus(value = HttpStatus.CREATED)
	public Person save(@RequestBody Person person) {
		return personService.save(person);
	}

	@GetMapping("/REST/person/{id}")
	public Person findById(@PathVariable Long id) {
		return personService.findById(id);
	}

	@PutMapping("/REST/person/{id}")
	public Person replacePerson(@RequestBody Person newPerson, @PathVariable Long id) {
		return personService.replace(newPerson, id);
	}
	
	@DeleteMapping("/REST/person/{id}")
	public void delete(@PathVariable Long id) {
		personService.deleteById(id);
	}
}