/**
 *	The model of User for mapping into database
 */
package ru.kazbo.micronaut.jpa.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

@Entity(name = "users")
@Table(name = "users")
public class User {
	
	/*
	 *	User id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/*
	 *	Name of user
	 */
	@NotBlank(message = "Name cant be emty or null")
	@Column(unique = true, nullable = false)
	private String name;
	
	/*
	 *	Age of user
	 */
	@NotNull(message = "Age can't be null")
	@Column(nullable = false)
	private Integer age;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setAge(Integer age) {
		this.age = age;
	}
	
	public Integer getAge() {
		return age;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
}