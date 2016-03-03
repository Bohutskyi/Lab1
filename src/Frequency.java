import com.google.common.collect.Sets;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Frequency {

    private String textWithSpaces, textWithoutSpaces;
    private Map<Character, Float> frequencySymbolsMap = new HashMap<>();
    private Map<String, Float> frequencyBigramMap = new HashMap<>();
    private float H1, H2;

    private static HashSet<Character> base = Sets.newHashSet('а','б','в','г','д','е','ж','з','и','й','к','л','м','н','о','п','р','с','т',
            'у','ф','х','ц','ч','ш','щ','ъ','ы','ь','э','ю','я');

    public Frequency(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            StringBuilder temp = new StringBuilder();
            String result;
            while ((result = reader.readLine()) != null) {
                int count = 0;
                for (int i = 0; i < result.length(); i++) {
                    if (result.charAt(i) == ' ') {
                        count++;
                        if (count == 1) {
                            temp.append(" ");
                        }
                    } else if (base.contains(result.charAt(i))) {
                        count = 0;
                        temp.append(result.charAt(i));
                    } else if (result.charAt(i) == 'ё') {
                        temp.append('е');
                    }
                }
            }
            textWithSpaces = temp.toString();
            reader.close();
            calculateSymbolFrequency();
            calculateBigramWithoutSpaces(textWithSpaces);

        } catch (FileNotFoundException e) {
            System.out.println("Error. File not found");
        } catch (IOException e) {
            System.out.println("Error. Reading");
        }
    }

    public void printText() {
        System.out.println(textWithSpaces);
        System.out.println(textWithoutSpaces);
    }

    public void calculateSymbolFrequency() {
        for (int i = 0; i < textWithSpaces.length(); i++) {
            char temp = textWithSpaces.charAt(i);
            if (frequencySymbolsMap.containsKey(temp)) {
                frequencySymbolsMap.put(temp, frequencySymbolsMap.get(temp) + 1);
            } else {
                frequencySymbolsMap.put(temp, (float) 1.0);
            }
        }
        frequencySymbolsMap.remove("");
        for (Map.Entry<Character, Float> temp : frequencySymbolsMap.entrySet()) {
            frequencySymbolsMap.put(temp.getKey(), (float) (temp.getValue() / textWithSpaces.length()));
        }
        frequencySymbolsMap.remove("");
    }

    public float getSymbolFrequency(char c) {
        if (frequencySymbolsMap.containsKey(c)) {
            return frequencySymbolsMap.get(c);
        } else {
            return 0;
        }
    }

    public void calculateH1() {
        float s = (float) 0.;
        for (Map.Entry<Character, Float> temp : frequencySymbolsMap.entrySet()) {
            s -= (float) (temp.getValue() * (Math.log(temp.getValue()) / Math.log(2)));
        }
        H1 = s;
    }

    public float getH1() {
        return H1;
    }


    public void calculateBigramWithoutSpaces(String text) {
        for (char c : base) {
            for (char ch : base) {
                StringBuilder temp = new StringBuilder();
                temp.append(c);
                temp.append(ch);
                frequencyBigramMap.put(temp.toString(),(float) 0.0);
            }
        }
        for (int i = 0; i < text.length() - 2; i++) {
            StringBuilder temp = new StringBuilder();
            temp.append(text.charAt(i));
            temp.append(text.charAt(i+1));
            if (frequencyBigramMap.containsKey(temp.toString())) {
                frequencyBigramMap.put(temp.toString(), frequencyBigramMap.get(temp.toString()) + 1);
            }
        }
        for (Map.Entry<String, Float> temp : frequencyBigramMap.entrySet()) {
            frequencyBigramMap.put(temp.getKey(), (float) temp.getValue() / (text.length() - 1));
        }

        for (Map.Entry<String, Float> temp : frequencyBigramMap.entrySet()) {
            System.out.println(temp.getKey() + " " + temp.getValue());
        }
    }

    public float getBigramFrequency(String t, String text) {
        if (frequencyBigramMap.containsKey(t)) {
            return (frequencyBigramMap.get(t) / (text.length() - 1));
        } else {
            return 0;
        }
    }

    public static void main(String[] args) {
        Frequency main = new Frequency("text.txt");
        main.printText();

//        System.out.println(main.getSymbolFrequency('п'));
//        System.out.println(main.getH1());
//        System.out.println(g);
        /*
        * 0.0
4.959813*/

    }

}
