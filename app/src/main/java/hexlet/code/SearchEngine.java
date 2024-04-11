package hexlet.code;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Comparator;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.regex.MatchResult;

public class SearchEngine {
    public static Map<String, Map<String, Double>> invertedIndex = new HashMap<>();
    public static Map<String, Integer> docWordCount = new HashMap<>();

    public static List<String> search(List<Map<String, String>> docs, String str) {
        buildInvertedIndex(docs);

        var map = new HashMap<String, Double>();
        var terms = str.split(" ");

        for (var term : terms) {
            var normalizeTerm = Pattern.compile("\\w+")
                    .matcher(term)
                    .results()
                    .map(MatchResult::group)
                    .collect(Collectors.joining());
            if (invertedIndex.containsKey(normalizeTerm)) {
                for (var entry : invertedIndex.get(normalizeTerm).entrySet()) {
                    var docId = entry.getKey();
                    var tfidf = entry.getValue();
                    map.put(docId, map.getOrDefault(docId, 0.0) + tfidf);
                }
            }
        }

        return map.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(Map.Entry::getKey))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

    }

    private static void buildInvertedIndex(List<Map<String, String>> docs) {
        for (var doc : docs) {
            var text = doc.get("text");
            var docId = doc.get("id");

            var terms = text.split("\\s+");

            for (var term : terms) {
                var normalizeTerm = Pattern.compile("\\w+")
                        .matcher(term)
                        .results()
                        .map(MatchResult::group)
                        .collect(Collectors.joining());

                invertedIndex.computeIfAbsent(normalizeTerm, k -> new HashMap<>())
                        .merge(docId, 1.0 / terms.length, Double::sum);

                docWordCount.put(docId, docWordCount.getOrDefault(docId, 0) + 1);
            }
        }

        // Вычисляем TF-IDF для каждого термина в каждом документе
        for (var termEntry : invertedIndex.entrySet()) {
            var idf = Math.log((double) docs.size() / termEntry.getValue().size());
            for (var docEntry : termEntry.getValue().entrySet()) {
                var tf = docEntry.getValue();
                var tfidf = tf * idf;
                termEntry.getValue().put(docEntry.getKey(), tfidf);
            }
        }

    }
}
