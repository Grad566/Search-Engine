package hexlet.code;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static hexlet.code.SearchEngine.search;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchEngineTest {
    private static List<Map<String, String>> docs;

    @BeforeAll
    public static void setInput() {
        String doc1 = "I can't shoot straight unless I've had a pint!";
        String doc2 = "Don't shoot shoot shoot that thing at me.";
        String doc3 = "I'm your shooter.";
        docs = List.of(
                Map.of("id", "doc1", "text", doc1),
                Map.of("id", "doc2", "text", doc2),
                Map.of("id", "doc3", "text", doc3)
        );
    }

    @Test
    public void testSearchCorrectWork() {
        var actual = search(docs, "shoot");
        var expected = new ArrayList<String>(List.of("doc2", "doc1"));

        assertEquals(expected, actual);
    }

    @Test
    public void testSearchCorrectWorkPunctuation() {
        var actual = search(docs, "pint!");
        var expected = new ArrayList<String>(List.of("doc1"));

        assertEquals(expected, actual);
    }

    @Test
    public void testSearchOddSearch() {
        var actual = search(docs, "shoot at me");
        var expected = new ArrayList<String>(List.of("doc2", "doc1"));

        assertEquals(expected, actual);
    }

}
