package br.com.leandro.crud.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@EqualsAndHashCode (callSuper = true)
public class PersonImage extends AbstractEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Lob
	//@Basic(fetch = FetchType.EAGER)
	@Column (columnDefinition = "BLOB")
	// @Transient  -- desativa o atributo para o hibernate
	private byte[] image;
	
	@Column
	private String contentType;	
	
	@Column
	private String nameImage;	
	
	@ManyToOne (fetch = FetchType.EAGER)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@JsonBackReference
	private Person person;
}
