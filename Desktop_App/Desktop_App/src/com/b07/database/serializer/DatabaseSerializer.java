package com.b07.database.serializer;

import java.io.Serializable;

public interface DatabaseSerializer {
  public String serialize(Serializable toSerialize, String path, String fileName);
  
  public Object deserialize(String fileName, String path);
}
