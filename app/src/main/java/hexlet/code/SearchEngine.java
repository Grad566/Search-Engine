package hexlet.code;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.regex.MatchResult;

public class SearchEngine {
    public static Map<String, List<String>> invertedIndex = new HashMap<>();

    public static List<String> search(List<Map<String, String>> docs, String str) {

        buildInvertedIndex(docs);

        var map = new HashMap<String, Integer>();
        var terms = str.split(" ");

        for (var term : terms) {
            var normalizeTerm = Pattern.compile("\\w+")
                    .matcher(term)
                    .results()
                    .map(MatchResult::group)
                    .collect(Collectors.joining());
            if (invertedIndex.containsKey(normalizeTerm)) {
                for (var docId : invertedIndex.get(normalizeTerm)) {
                    map.put(docId, map.getOrDefault(docId, 0) + 1);
                }
            }
        }

        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
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

                invertedIndex.computeIfAbsent(normalizeTerm, k -> new ArrayList<>()).add(docId);
            }
        }

        System.out.println(invertedIndex);
    }
}

