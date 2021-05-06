package br.com.leandro.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.leandro.crud.data.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
