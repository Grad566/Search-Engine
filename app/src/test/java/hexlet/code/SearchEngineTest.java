package hexlet.code;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchEngineTest {
    private static String doc1;
    private static String doc2;
    private static String doc3;
    private static List<Map<String, String>> docs;

    @BeforeAll
    public static void setInput() {
        doc1 = "I can't shoot straight unless I've had a pint!";
        doc2 = "Don't shoot shoot shoot that thing at me.";
        doc3 = "I'm your shooter.";
        docs = List.of(
                Map.of("id", "doc1", "text", doc1),
                Map.of("id", "doc2", "text", doc2),
                Map.of("id", "doc3", "text", doc3)
        );
    }

    @Test
    public void testSearchCorrectWork() {
        var actual = SearchEngine.search(docs, "shoot");
        var expected = new ArrayList<String>(List.of("doc1", "doc2"));

        assertEquals(expected, actual);
    }

}
