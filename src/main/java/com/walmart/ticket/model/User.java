package com.walmart.ticket.model;

public class User {
	private String Name;
	private String email;
	
	
	
	public String getName() {
		return Name;
	}


	public void setName(String name) {
		Name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public User(String email) {
		super();
		this.email = email;
	}

		
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (Name != null)
			builder.append(Name).append(", ");
		if (email != null)
			builder.append(email);
		return builder.toString();
	}
	
	
	
}
