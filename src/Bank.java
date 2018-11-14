import java.util.HashMap;
import java.util.Map;

public class Bank {

  Map<Long, Long> accounts;
  long totalBalance;
  public Bank() {
    accounts = new HashMap<Long, Long>();
  }

  boolean deposit(long accountId, int amount) {
return true;
  }

  boolean withdraw(long accountId, int amount) {
return true;
  }

  long getTotalBalance() {
return 0;
  }

  long getAccountBalance(long accountId) {
return 0;
  }
}
