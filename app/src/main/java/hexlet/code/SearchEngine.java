package hexlet.code;

import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.regex.MatchResult;

public class SearchEngine {
    public static List<String> search(List<Map<String, String>> docs, String str) {
        var map = new HashMap<String, Integer>();
        var terms = str.split(" ");

        for (var doc : docs) {
            var text = doc.get("text");
            var count = 0;

            for (var term : terms) {
                var normalizeTerm = Pattern.compile("\\w+")
                        .matcher(term)
                        .results()
                        .map(MatchResult::group)
                        .collect(Collectors.joining());
                Pattern pattern = Pattern.compile(normalizeTerm + "(?=[!?.,\\s]|$)");
                Matcher matcher = pattern.matcher(text);
                while (matcher.find()) {
                    count++;
                }
            }

            if (count > 0) {
                map.put(doc.get("id"), count);
            }

        }

        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

    }

}

