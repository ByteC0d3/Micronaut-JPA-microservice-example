package ru.kazbo.micronaut.jpa.service;

import java.util.List;
import io.micronaut.transaction.annotation.ReadOnly;
import io.micronaut.transaction.annotation.TransactionalAdvice;

public interface ModelsFunctions<T> {
	/**
	 *	Save object into database
	 *	@param obj The object for saving to database
	 */
	public void save(T obj);
	
	/**
	 *	Remove object from database by id
	 *	@param id The ID of entity in database
	 */
	public void remove(Long id);
	
	/**
	 *	find object by id
	 *	@param id The id of entity in database
	 *	@return The entity that selected from database
	 */
	public T findById(Long id);
	
	/**
	 *	Get all objects from database
	 *	@return The List of entities selected from database
	 */
	public List<T> findAll();
	
	/**
	 *	Update object
	 */
	public T update(T obj);
}