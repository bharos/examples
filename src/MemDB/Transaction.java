package MemDB;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Transaction {
  private Map<String, String> transactionMap;
  private Map<String, Integer> countMap;
  Set<String> deleted;
  public static final String DELETED_TOKEN = "$$$";

  public Transaction() {
    transactionMap = new HashMap<String, String>();
    deleted = new HashSet<>();
    countMap = new HashMap<>();
  }
  String get(String key) {
   // If marked as deleted, return DELETED_TOKEN indicating deleted
    if (deleted.contains(key)) {
      return DELETED_TOKEN;
    }

    return transactionMap.getOrDefault(key, null);
  }
  void put(String key, String val) {
    transactionMap.put(key, val);
    countMap.put(val, countMap.getOrDefault(val, 0)+1);

    // If this key was previously marked as deleted, remove it
    if (deleted.contains(key)) {
      deleted.remove(key);
    }
  }
  void delete(String key) {
    // If transaction contains key, remove it and reduce count
    // else add it in deleted set to remove it from memDB while commit
    if (transactionMap.containsKey(key)) {
    String val = transactionMap.remove(key);
    if (countMap.containsKey(val)) {
      int count = countMap.get(val);
      if (count == 1) {
        countMap.remove(val);
      } else {
        countMap.put(val, count - 1);
      }
    }
    } else {
      deleted.add(key);
    }
  }
  public Map<String, Integer> getCountMap() {
    return countMap;
  }

  public Map<String, String> getTransactionMap() {
    return transactionMap;
  }

  int count(String val) {
    return countMap.getOrDefault(val, 0);
  }
}
