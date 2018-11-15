package MemDB;

public interface DbOperations {
  String get(String key);
  void put(String key, String val);
  void delete(String key);
  int count(String val);
  void openTransaction();
  void commitTransaction() throws Exception;
  void rollbackTransaction() throws Exception;
}
