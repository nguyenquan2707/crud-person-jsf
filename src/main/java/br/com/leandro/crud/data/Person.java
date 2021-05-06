package br.com.leandro.crud.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
// @Getter @Setter // somente getters e setters
@Entity
@EqualsAndHashCode (callSuper = true)
public class Person extends AbstractEntity {
	
	@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long id;
	
	@Column
	private String firstName;
	
	@Column  (columnDefinition = "VARCHAR(512)")
	private String lastName;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@Column
	private Date birthday;
	
	//@OneToMany (cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "person")
	@OneToMany (cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn (name = "person_id")
	@ToString.Exclude
	@JsonManagedReference
	private List<PersonImage> personImages = new ArrayList<PersonImage>();
	
	@OneToMany (cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn (name = "person_id")
	@ToString.Exclude // Não gera toString() para entrar em loop com a outra clasee q também tem referência para cá.
	@JsonManagedReference // para evitar loop no momento q vai serializar o objeto
	private Set<PersonAddress> personAddresses = new HashSet<PersonAddress>();

}
