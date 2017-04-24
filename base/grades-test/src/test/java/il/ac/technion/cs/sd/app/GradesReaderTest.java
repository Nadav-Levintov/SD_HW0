package il.ac.technion.cs.sd.app;

import db_utils.DataBase;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.*;
import static org.junit.Assert.*;

/**
 * Created by benny on 24/04/2017.
 */
public class GradesReaderTest {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10);

    @Test
    public void getGrade() throws Exception {

        DataBase DB = Mockito.mock((DataBase.class));
        Map<String,String> map = new TreeMap<String,String>();
        List<String> names = new ArrayList<String>();
        names.add("ID");
        names.add("Grade");

        map.put("Nadav","85");
        map.put("Benny","100");
        map.put("Shalom","55");

        Mockito.when(DB.getNames_of_columns()).thenReturn(names);

        Mockito.when(DB.get_val_from_column_by_name(Mockito.anyString(),Mockito.anyString()))
                .thenAnswer(new Answer<Object>() {
                    @Override
                    public Object answer(InvocationOnMock i) throws Throwable {
                        String res = map.get(i.getArgument(0).toString());
                        if (res == null) {
                             return Optional.empty();
                         } else {
                             return Optional.of(res);
                         }
                     }

                     ;
                 });

        GradesReader reader = new GradesReader();

        assertEquals(OptionalInt.of(100),reader.getGrade("Benny",DB));
        assertEquals(OptionalInt.of(85),reader.getGrade("Nadav",DB));
        assertEquals(OptionalInt.empty(),reader.getGrade("Zed",DB));

    }

}