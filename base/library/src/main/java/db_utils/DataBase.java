package db_utils;

import il.ac.technion.cs.sd.grades.ext.LineStorage;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import javax.sound.sampled.Line;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;


public class DataBase {
    protected Integer num_of_columns;
    protected List<?> names_of_columns;
    protected Map<String, List<String>> DB = new TreeMap<String,List<String>>();
    protected StorageInterface storage_implament;

    public DataBase(Integer num_of_columns,List<?> names_of_columns) {

        this.num_of_columns = num_of_columns;
        this.names_of_columns = names_of_columns;
        this.storage_implament = new DefaultStorageImplament();
    }
    public DataBase(Integer num_of_columns,List<?> names_of_columns,StorageInterface storage_implament) {

        this.num_of_columns = num_of_columns;
        this.names_of_columns = names_of_columns;
        this.storage_implament = storage_implament;
    }


    public Integer getNum_of_columns() {
        return num_of_columns;
    }



    public void insert_line(String key,List<String> values)
    {
        if(values.size() != num_of_columns-1)
        {
            throw new IllegalArgumentException();
        }

        DB.put(key,values);
    }

    public void write_to_disk()
    {
        for(Map.Entry<String,List<String>> entry : DB.entrySet())
        {
            String key = entry.getKey();
            List<String> val = entry.getValue();

            String output = key;
            for (String str : val ) {
                output+="," + str;
            }

            storage_implament.appendLine(output);
        }
    }

    public Optional<String> get_val_from_column(String key, String column) throws InterruptedException {
        Integer low=0,high;
        String curr_line;
        high = storage_implament.numberOfLines()-1;

        while (low <= high) {

            Integer mid = low + (high - low) / 2;
            curr_line = storage_implament.read(mid);

            String[] values = curr_line.split(",");
            Integer compare=key.compareTo(values[0]);
            if      (compare < 0) high = mid - 1;
            else if (compare > 0) low = mid + 1;
            else return Optional.of(values[names_of_columns.indexOf(column)]);
        }
        return Optional.empty();
    }

    public Integer size()
    {
        return DB.size();
    }

}
