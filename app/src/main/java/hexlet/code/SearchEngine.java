package hexlet.code;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.regex.MatchResult;

public class SearchEngine {
    public static List<String> search(List<Map<String, String>> docs, String str) {
        var map = new HashMap<String, Integer>();
        var term = Pattern.compile("\\w+")
                .matcher(str)
                .results()
                .map(MatchResult::group)
                .collect(Collectors.joining());

        Pattern pattern = Pattern.compile(term + "(?=[!?.,\\s]|$)");

        for (var doc : docs) {
            var text = doc.get("text");
            var words = text.split(" ");
            var count = 0;
            for (var word : words) {
                Matcher matcher = pattern.matcher(word);
                if (matcher.find()) {
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

