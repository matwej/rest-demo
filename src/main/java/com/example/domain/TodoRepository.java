package com.example.domain;

import java.util.HashSet;
import java.util.Set;

public class TodoRepository implements CrudRepository<Todo> {

	private static TodoRepository instance = null;
	private Set<Todo> todos = new HashSet<Todo>();

	private TodoRepository() {
		Todo first = new Todo("I have to do something",false);
		todos.add(first);
	};

	public static TodoRepository getInstance() {
		if(instance == null) instance = new TodoRepository();
		return instance;
	}

	public Set<Todo> getAll() {
		return todos;
	}

	public Todo findOne(String uid) {
		for (Todo todo : todos) {
			if(todo.getUid().equals(uid)) return todo;
		}
		return null;
	}

	public String add(Todo todo) {
		todos.add(todo);
		return todo.getUid();
	}

	public boolean remove(String uid) {
		return todos.remove(findOne(uid));
	}

	public Todo save(Todo entity) {
		todos.remove(findOne(entity.getUid()));
		todos.add(entity);
		return null;
	}

}
