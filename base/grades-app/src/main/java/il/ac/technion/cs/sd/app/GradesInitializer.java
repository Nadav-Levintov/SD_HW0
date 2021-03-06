package il.ac.technion.cs.sd.app;


import db_utils.DataBase;
import db_utils.StorageInterface;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/** This class will be instantiated once per test. */
public class GradesInitializer {
  /**
   * Saves the csvData persistently, so that it could be run using GradesRunner.
   * The format of each line of the data is $id,$grade.
   */

  public void setup(String csvData) {
      /**
       * No need for data check, promised by instructor that all data is legal
       */
      List<String> names = new ArrayList<String>();
      names.add("ID");
      names.add("Grade");
      DataBase DB = new DataBase(names);
      DB.build_db(csvData);

  }

    public void setup(String csvData, DataBase DB) {
        /**
         * No need for data check, promised by instructor that all data is legal
         */
        DB.build_db(csvData);

    }






 /* public void setup(String csvData) {
    List<String> names = new ArrayList<String>();
    names.add("ID");
    names.add("Grade");
    DataBase DB = new DataBase(2,names);
    String[] lines= csvData.split((System.getProperty("line.separator")));
      for (String line: lines) {
          String[] values =line.split(",");
          List<String> valuesList = new ArrayList<>();
          valuesList.add(values[1]);
          DB.insert_line(values[0],valuesList);
      }

    DB.write_to_disk();
  }
  */
}
