package com.b07.builders;

import com.b07.exceptions.UserSetException;
import com.b07.users.User;

public interface UserBuilder {
  public UserBuilder id(int id) throws UserSetException;
  
  public UserBuilder name(String name) throws UserSetException;
  
  public UserBuilder age(int age) throws UserSetException;
  
  public UserBuilder address(String address) throws UserSetException;
  
  public User build();
}
