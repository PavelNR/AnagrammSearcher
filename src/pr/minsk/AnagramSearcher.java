package pr.minsk;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by pavel.rubatski
 * pavel.rubatski@gmail.com
 * on 6/23/2019.
 */
public class AnagramSearcher {

    public Collection collectAnagrams(String[] dictionary, String searchAnagramsFor) {
        if (searchAnagramsFor == null || searchAnagramsFor.length() < 1 || dictionary == null) {
            return Collections.EMPTY_LIST;
        }
        Map<Integer, Integer> countedSearchWord = countSymbols(searchAnagramsFor);
        return Arrays
                .stream(dictionary)
                .parallel()
                .filter(el -> isAnagram(el, searchAnagramsFor, countedSearchWord))
                .collect(Collectors.toList());
    }

    private boolean isAnagram(String s1, String s2, Map<Integer, Integer> countedSearchWord) {
        if (s2.equals(s1)) {
            return true;
        }
        else {
            if (s1 == null || s1.length() != s2.length()) {
                return false;
            }

            Map<Integer, Integer> countedPossibleAnagram = countSymbols(s1);
            for (Map.Entry entry : countedSearchWord.entrySet()) {
                if (!countedPossibleAnagram.containsKey(entry.getKey()) ||
                        !countedPossibleAnagram.get(entry.getKey()).equals(entry.getValue())) {
                    return false;
                }
            }
            return true;
        }
    }

    private Map<Integer, Integer> countSymbols(String word) {
        Map<Integer, Integer> result = new HashMap<>(word.length());
        word.chars()
                .forEach(c -> result.merge(c, 1, (oldCount, newVal) -> oldCount + newVal));
        return result;
    }
}
