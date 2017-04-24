package il.ac.technion.cs.sd.app;

import db_utils.DataBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

/**
 * This class will only be instantiated after
 * {@link il.ac.technion.cs.sd.app.GradesInitializer#setup(java.lang.String) has been called}.
 */
public class GradesReader {
  /** Returns the grade associated with the ID, or empty. */
  public OptionalInt getGrade(String id) throws InterruptedException {
    List<String> names = new ArrayList<String>();
    names.add("ID");
    names.add("Grade");
    DataBase DB = new DataBase(names);

    Optional<String> res = DB.get_val_from_column_by_name(id,names.get(1));
    if(res.equals(Optional.empty()))
      return OptionalInt.empty();
    else
    {
      Integer res_int = Integer.parseInt(res.get());
      return OptionalInt.of(res_int);
    }
  }
}
