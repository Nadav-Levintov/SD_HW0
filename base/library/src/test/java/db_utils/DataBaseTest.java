package db_utils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by Nadav on 22-Apr-17.
 */
public class DataBaseTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getNum_of_columns() throws Exception {

        List<String> col_names = new ArrayList<>();
        col_names.add("Name");
        col_names.add("Age");

        DataBase DB = new DataBase(col_names.size(),col_names);

        assertEquals(col_names.size(),DB.getNum_of_columns().intValue());
    }

    @Test
    public void insert_line() throws Exception {
        List<String> col_names = new ArrayList<>();
        col_names.add("Name");
        col_names.add("Age");

        DataBase DB = new DataBase(col_names.size(),col_names);
        List<String> values = new ArrayList<>();
        values.add("18");
        DB.insert_line("Nadav",values);

        assertEquals(1,DB.size().intValue());
    }

    @Test
    public void write_to_disk() throws Exception {
        List<String> file = new LinkedList<String>();

        StorageInterface si = Mockito.mock(DefaultStorageImplament.class);

        Mockito.when(si.numberOfLines()).thenReturn(file.size());
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                file.add(invocationOnMock.getArgument(0));
                return null;
            }
        }).when(si).appendLine(Mockito.anyString());


        List<String> col_names = new ArrayList<>();
        col_names.add("Name");
        col_names.add("Age");

        DataBase DB = new DataBase(col_names.size(),col_names,si);

        List<String> values = new ArrayList<>();
        values.add("25");
        DB.insert_line("Nadav",values);
        List<String> values2 = new ArrayList<>();
        values2.add("27");
        DB.insert_line("Benny",values2);

        DB.write_to_disk();

        assertEquals(2,file.size());
        assertEquals("Benny,27",file.get(0));
    }

    @Test
    public void get_val_from_column() throws Exception {

        List<String> file = new LinkedList<String>();
        file.add("Nadav,25");
        file.add("Benny,27");
        file.add("Zed,30");
        file.sort(String::compareTo);
        StorageInterface si = Mockito.mock(DefaultStorageImplament.class);

        Mockito.when(si.numberOfLines()).thenReturn(file.size());
        Mockito.when(si.read(Mockito.anyInt())).thenAnswer(i -> file.get(i.getArgument(0)));
        Mockito.doNothing().when(si).appendLine(Mockito.anyString());

        List<String> col_names = new ArrayList<>();
        col_names.add("Name");
        col_names.add("Age");

        DataBase DB = new DataBase(col_names.size(),col_names,si);

        List<String> values = new ArrayList<>();
        values.add("25");
        DB.insert_line("Nadav",values);
        List<String> values2 = new ArrayList<>();
        values2.add("27");
        DB.insert_line("Benny",values2);

        DB.write_to_disk();


        assertEquals(Optional.of("25"),DB.get_val_from_column("Nadav",col_names.get(1)));
        assertEquals(Optional.of("Nadav"),DB.get_val_from_column("Nadav",col_names.get(0)));
        assertEquals(Optional.of("27"),DB.get_val_from_column("Benny",col_names.get(1)));
        assertEquals(Optional.of("Benny"),DB.get_val_from_column("Benny",col_names.get(0)));
        assertEquals(Optional.empty(),DB.get_val_from_column("Shalom",col_names.get(0)));
    }

}