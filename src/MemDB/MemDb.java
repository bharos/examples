package MemDB;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class MemDb implements DbOperations {
  private Map<String, String> db;
  private Map<String, Integer> countMap;
  private LinkedList<Transaction> transactions;

  public MemDb() {
    db = new HashMap<String, String>();
    transactions = new LinkedList<Transaction>();
    countMap = new HashMap<>();
  }

  @Override
  public String get(String key) {
    Iterator<Transaction> iterator = transactions.iterator();
    while (iterator.hasNext()) {
      Transaction transaction = iterator.next();
      String val = transaction.get(key);

      // If we find the key is deleted, return null
      if (val == Transaction.DELETED_TOKEN) {
        return null;
      }
      //Return if we find a valid value
      if (val != null) {
        return val;
      }
    }

    // if the key is not present in any transaction, return if
    // it is present in db, else return null.
    return db.getOrDefault(key, null);
  }

  @Override
  public void put(String key, String val) {
    // If there is an ongoing transaction, put key,val to that transaction
    // Else put key,val to the db itself.

    String currentValue = get(key);
    if (transactions.size() > 0) {
      Transaction transaction = transactions.getFirst();
      transaction.put(key, val, currentValue);
    } else {
      db.put (key, val);
      countMap.put (val, countMap.getOrDefault(val, 0) + 1);
    }
  }

  @Override
  public void delete(String key) {
    String currentValue = get(key);
    if (transactions.size() > 0) {
      Transaction transaction = transactions.getFirst();
      transaction.delete(key, currentValue);
    } else {
      db.remove(key);
    }
  }

  @Override
  public int count(String val) {
      int sum = countMap.getOrDefault(val, 0);
    Iterator<Transaction> iterator = transactions.iterator();
    while (iterator.hasNext()) {
      Transaction transaction = iterator.next();
      sum += transaction.getCountMap().getOrDefault(val, 0);
    }
    return sum;
  }

  @Override
  public void openTransaction() {
      transactions.addFirst(new Transaction());
  }

  @Override
  public void commitTransaction() throws Exception {
    if (transactions.size() == 0) {
      throw new Exception("Error. No transaction to commit..");
    }
    while (transactions.size() > 0) {
      Transaction transaction = transactions.removeLast();
      applyTransaction(transaction);
    }
  }

  private void applyTransaction(Transaction transaction) {
    db.putAll(transaction.getTransactionMap());
    for (String val : transaction.getCountMap().keySet()) {
      countMap.put(val, countMap.getOrDefault(val, 0)+transaction.getCountMap().get(val));
    }

    // Remove all deleted entries from DB
    for (String key : transaction.deleted) {
      db.remove(key);
    }
  }

  @Override
  public void rollbackTransaction() throws Exception {
    if (transactions.size() > 0) {
      transactions.removeFirst();
    }
    else {
      throw new Exception("Error. No transaction to rollback..");
    }
  }
}
