package hexlet.code;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.regex.MatchResult;

public class SearchEngine {
    public static List<String> search(List<Map<String, String>> docs, String str) {
        var result = new ArrayList<String>();
        var term = Pattern.compile("\\w+")
                .matcher(str)
                .results()
                .map(MatchResult::group)
                .collect(Collectors.joining());

        Pattern pattern = Pattern.compile(term + "(?=[!?.,\\s]|$)");

        for (var doc : docs) {
            var text = doc.get("text");
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                result.add(doc.get("id"));
            }
        }

        return result;

    }

}

