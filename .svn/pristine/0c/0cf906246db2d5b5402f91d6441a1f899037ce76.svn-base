package com.b07.database.serializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class DatabaseSerializerImpl implements DatabaseSerializer {

  @Override
  public String serialize(Serializable toSerialize, String path, String fileName) {
    try {
      String filePath = "";
      
      if (path.trim().length() == 0) {
        filePath = System.getProperty("user.dir") + File.separator 
            + "serializedDB" + File.separator;
      } else {
        filePath = path;
      }
      
      filePath += fileName;
      
      FileOutputStream fileOut = new FileOutputStream(filePath);
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(toSerialize);
      out.close();
      fileOut.close();
      return filePath;
    } catch (IOException i) {
      return "";
    }
  }

  @Override
  public Object deserialize(String fileName, String path) {
    String filePath = "";
    
    if (path.trim().length() == 0) {
      filePath = System.getProperty("user.dir") + File.separator 
          + "serializedDB" + File.separator;
    } else {
      filePath = path;
    }
    
    filePath += fileName;
    
    try {
      FileInputStream fileIn = new FileInputStream(filePath);
      ObjectInputStream in = new ObjectInputStream(fileIn);
      Object database = in.readObject();
      in.close();
      fileIn.close();
      return database;
    } catch (IOException i) {
      i.printStackTrace();
      return null;
    } catch (ClassNotFoundException c) {
      System.out.println("At least one of the classes was not found");
      c.printStackTrace();
      return null;
    }
  }
}
