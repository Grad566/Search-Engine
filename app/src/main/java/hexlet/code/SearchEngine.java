package hexlet.code;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchEngine {
    public static List<String> search(List<Map<String, String>> docs, String str) {
        var result = new ArrayList<String>();

        for (var doc : docs) {
            var text = doc.get("text");
            var textAsArray = text.split(" ");
            for (var word : textAsArray) {
                if (word.equals(str)) {
                    result.add(doc.get("id"));
                    break;
                }
            }
        }
        return  result;

    }

}

