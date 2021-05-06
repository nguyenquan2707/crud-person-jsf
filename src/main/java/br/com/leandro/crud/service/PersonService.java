package br.com.leandro.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.leandro.crud.controller.ResourceNotFoundException;
import br.com.leandro.crud.data.Person;
import br.com.leandro.crud.repository.PersonRepository;

@Service
@Transactional
public class PersonService {
	
	@Autowired
	private PersonRepository personRepository;

	public List<Person> findAll() {
		return personRepository.findAll();
	}
	
	public Person save(Person person) {
		return personRepository.save(person);
	}
	
	public void delete(Person person) {
		personRepository.delete(person);
	}
	
	public void deleteById(Long id) {
		personRepository.deleteById(id);
	}

	public Person findById(Long id) {
		return personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Could not find person id = "+id));
	}
	
	public Person replace(Person newPerson, Long id) {
		return personRepository.findById(id).map(person -> {			
			person.setFirstName(newPerson.getFirstName());
			person.setLastName(newPerson.getLastName());
			person.setBirthday(newPerson.getBirthday());
			person.setGender(newPerson.getGender());
			person.setPersonAddresses(newPerson.getPersonAddresses());
			person.setPersonImages(newPerson.getPersonImages());			
			return personRepository.save(person);
		}).orElseGet(() -> {
			newPerson.setId(id);
			return personRepository.save(newPerson);
		});
	}
	
}
