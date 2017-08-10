package com.example.domain;

import java.math.BigInteger;
import java.security.SecureRandom;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Todo {

	private SecureRandom random = new SecureRandom();
	
	@XmlElement private String uid = generateUid();
	@XmlElement private String name;
	@XmlElement private boolean done;

	public Todo() {};

	public Todo(String name, boolean done) {
		this.name = name;
		this.done = done;
	}

	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isDone() {
		return done;
	}
	public void setDone(boolean done) {
		this.done = done;
	}
	
	private String generateUid() {
		return new BigInteger(130, random).toString(32);
	}

}
