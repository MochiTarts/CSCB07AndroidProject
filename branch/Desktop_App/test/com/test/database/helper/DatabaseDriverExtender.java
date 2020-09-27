package com.test.database.helper;

import com.b07.database.DatabaseDriver;
import com.b07.exceptions.ConnectionFailedException;

import java.sql.Connection;

public class DatabaseDriverExtender extends DatabaseDriver {
  protected static Connection connectOrCreateDataBase() {
    return DatabaseDriver.connectOrCreateDataBase();
  }
  
  protected static Connection initialize(Connection connection) throws ConnectionFailedException {
    return DatabaseDriver.initialize(connection);
  }
}
