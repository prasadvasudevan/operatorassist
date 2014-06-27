package com.frontpaw.forecast.util;



public class User {

private String password;
private String firstName;
private String lastName;
private String searchMessage;
private String email;
private String userId;
private String description;



public void setPassword(String password){
	this.password = password;
}
public void setFirstName(String firstName){
	this.firstName = firstName;
}
public void setLastName(String lastName){
	this.lastName = lastName;
}
public void setSearchMessage(String searchMessage){
	this.searchMessage = searchMessage;
}

public void setEmail(String email){
	this.email = email;
}

public void setUserId(String userId){
	this.userId = userId;
}

public String getPassword(){
	return password;
}

public String getFirstName(){
	return firstName;
}
public String getLastName(){
	return lastName;
}
public String getSearchMessage(){
	return searchMessage;
}
public String getEmail(){
	return email;
}

public String getUserId(){
	return userId;
}
/**
 * @return the description
 */
public String getDescription() {
	return description;
}
/**
 * @param description the description to set
 */
public void setDescription(String description) {
	this.description = description;
}


}