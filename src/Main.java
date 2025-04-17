import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class Main {

    private static final Set<String> dictionary = new HashSet<>();
    private static final Set<String> validRoots = Set.of("I", "A");
    private static final Map<String, Boolean> memo = new HashMap<>();

    public static void main(String[] args) throws Exception {
        loadDictionary();
        long start = System.currentTimeMillis();
        List<String> result = new ArrayList<>();

        for (String word : dictionary) {
            if (word.length() == 9 && isReducible(word)) {
                result.add(word);
            }
        }

        System.out.println("Reducible 9-letter words:");
        result.forEach(System.out::println);
        System.out.println("Total: " + result.size());
        System.out.println("Time to execute " + (System.currentTimeMillis() - start) + " ms");
    }

    private static void loadDictionary() throws Exception {
        URL url = new URL("https://raw.githubusercontent.com/nikiiv/JavaCodingTestOne/master/scrabble-words.txt");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                dictionary.add(line.trim().toUpperCase());

            }
            dictionary.add("I");
            dictionary.add("A");
        }
    }

    private static boolean isReducible(String word) {
        if (memo.containsKey(word)) return memo.get(word);

        if (!dictionary.contains(word)) {
            memo.put(word, false);
            return false;
        }

        if (word.length() == 1) {
            boolean isRoot = validRoots.contains(word);
            memo.put(word, isRoot);
            return isRoot;
        }

        for (int i = 0; i < word.length(); i++) {
            String reduced = word.substring(0, i) + word.substring(i + 1);
            if (dictionary.contains(reduced) && isReducible(reduced)) {
                memo.put(word, true);
                return true;
            }
        }

        memo.put(word, false);
        return false;
    }
}
