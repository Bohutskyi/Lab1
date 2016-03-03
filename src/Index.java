import java.util.HashMap;
import java.util.Map;

public class Index extends Alphabet {

    private String text;
    private int n;
    private Map<Character, Double> frequencySymbolsMap = new HashMap<>();

    public Index(String text) {
        this(text, Alphabet.base.length);
    }

    public Index(String text, int n) {
        this.text = text;
        this.n = n;
    }

    public String getText() {
        return text;
    }

    public int getN() {
        return n;
    }

    public double calculateI() {
        getFrequency();
        double s = 0.0;
        for (char c : base) {
            s += (frequencySymbolsMap.get(c) * (frequencySymbolsMap.get(c) - 1.));
        }
        s = s / (n * (n - 1));
        return s;
    }

    public void getFrequency() {
        for (int i = 0; i < text.length(); i++) {
            char temp = text.charAt(i);
            if (frequencySymbolsMap.containsKey(temp)) {
                frequencySymbolsMap.put(temp, frequencySymbolsMap.get(temp) + 1.);
            } else {
                frequencySymbolsMap.put(temp, 1.);
            }
        }
        for (char c : base) {
            if (!frequencySymbolsMap.containsKey(c)) {
                frequencySymbolsMap.put(c, 0.);
            }
        }
    }

    public Map<Character, Double> getFrequencySymbolsMap() {
        return this.frequencySymbolsMap;
    }

    @Override
    public String toString() {
        return text;
    }
}
