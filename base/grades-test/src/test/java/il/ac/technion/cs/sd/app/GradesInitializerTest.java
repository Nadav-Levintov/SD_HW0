package il.ac.technion.cs.sd.app;

import db_utils.DataBase;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by benny on 24/04/2017.
 */
public class GradesInitializerTest {
    @Test
    public void setup() throws Exception {
        GradesInitializer GradeI = new GradesInitializer();
        String csvData = "Nadav,25\n" +
                "Benny,27\n" +
                "Zed,65\n";
        List<String> DataCheck = new LinkedList<>();
        DataBase MockedDB = Mockito.mock(DataBase.class);
        Mockito.doAnswer(invocationOnMock -> {
            DataCheck.add((invocationOnMock.getArgument(0).toString()) );
            return null;
        }).when(MockedDB).build_db(Mockito.anyString());
        GradeI.setup(csvData,MockedDB);

        assertEquals(csvData,DataCheck.get(0));
    }

}