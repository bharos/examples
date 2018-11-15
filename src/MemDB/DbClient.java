package MemDB;

public class DbClient {

  public static void main (String[] args) throws Exception {
    MemDb db = new MemDb();
//    db.openTransaction();
//    db.put("a", "x");
//    db.openTransaction();
//    db.put("a", "y");
//    db.commitTransaction();
//    System.out.println(db.get("a"));
//
//
//    db.put("b", "x");
//    System.out.println(db.get("b"));
//    db.openTransaction();
//    System.out.println(db.get("b"));
//    db.put("b", "y");
//    System.out.println(db.get("b"));
//    db.rollbackTransaction();
//    System.out.println(db.get("b"));
//
//
//    db.put("c", "x");
//    db.openTransaction();
//    db.delete("c");
//    System.out.println(db.get("c"));
//    db.openTransaction();
//    db.put("c", "y");
//    db.openTransaction();
//    System.out.println(db.get("c"));
//    db.delete("c");
//    db.commitTransaction();
//    System.out.println(db.get("c"));

    db.put("d","x");
    db.openTransaction();
    db.put("e","x");
    db.openTransaction();
    System.out.println(db.count("x")); // 2
    db.put("d","y");
    db.openTransaction();
    System.out.println(db.count("x")); // 1
    db.rollbackTransaction();
    db.openTransaction();
    System.out.println(db.count("x")); // 1
    db.openTransaction();
    db.delete("d");
    System.out.println(db.count("x")); // 1


  }
}
