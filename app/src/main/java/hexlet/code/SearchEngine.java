package hexlet.code;

import java.util.List;
import java.util.Map;

import static hexlet.code.ReverseIndex.doReverseIndex;
import static hexlet.code.ScoreDQ.scoreDQ;
import static hexlet.code.TFIDF2.getSortedDocList;

public class SearchEngine {
    public static List<String> search(List<Map<String, String>> docs, String sentence) {

        Map<String, Long> reversIndex = doReverseIndex(docs);
        List<String> tfidf = getSortedDocList(docs, reversIndex, sentence);
        List<String> scoredq = scoreDQ(docs, reversIndex, sentence);
        return tfidf;
    }
}
