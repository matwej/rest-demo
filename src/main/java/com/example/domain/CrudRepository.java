package com.example.domain;

import java.util.Set;

public interface CrudRepository<T> {

	Set<T> getAll();
	T findOne(String uid);
	String add(T entity);
	T save(T entity);
	boolean remove(String uid);
}
