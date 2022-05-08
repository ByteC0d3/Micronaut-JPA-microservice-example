package ru.kazbo.micronaut.jpa.service;

import ru.kazbo.micronaut.jpa.model.User;
import io.micronaut.transaction.annotation.TransactionalAdvice;
import io.micronaut.transaction.annotation.ReadOnly;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import javax.persistence.EntityManager;
import jakarta.inject.Inject;
import java.util.List;

@Singleton
public class UserService implements ModelsFunctions<User> {
	
	@Inject
	private EntityManager manager;
	
	@Override
	@TransactionalAdvice
	public void save(User user) {
		manager.persist(user);
	}
	
	@Override
	@TransactionalAdvice
	public void remove(Long id) {
		var removingUser = findById(id);
		manager.remove(removingUser);
	}
	
	@Override
	@ReadOnly
	public User findById(Long id) {
		return manager.find(User.class, id);
	}
	
	@ReadOnly
	public User findByName(String name) {
		return (User) manager.createQuery("SELECT u FROM users u WHERE u.name=:name")
					.setParameter("name", name)
					.getSingleResult();
	}
	
	@Override
	@ReadOnly
	public List<User> findAll() {
		return manager.createQuery("SELECT u FROM users u").getResultList();
	}
	
	@Override
	@TransactionalAdvice
	public User update(User user) {
		var updatingUser = findById(user.getId());
		updatingUser.setName(user.getName());
		updatingUser.setAge(user.getAge());
		manager.persist(updatingUser);
		// can change to: return findById(updatingUser.getId());
		return updatingUser;
	}
}