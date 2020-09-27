package com.b07.database.serializer;

import com.b07.database.adapter.DatabaseInsertAdapter;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.DatabaseInsertHelperException;
import java.sql.SQLException;

public class DatabasePasswordInsertExtender extends DatabaseInsertAdapter {
  protected static int insertNewUser(String name, int age, String address, String password,
      boolean passwordHashed)
      throws DatabaseInsertException, SQLException, DatabaseInsertHelperException {
    return DatabaseInsertAdapter.insertNewUser(name, age, address, password, passwordHashed);
  }
}
