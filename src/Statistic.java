import java.io.*;
import java.util.*;

public class Statistic extends Alphabet {

    private static final Set<Character> ALPHABET = new HashSet<>();

    static {
        for (char c : base) {
            ALPHABET.add(c);
        }
    }

    private String textWithSpaces, textWithoutSpaces;
    private Map<Character, Integer> frequency;
    private Map<String, Integer> bigramsWithSpacesNotCrossing, bigramsWithSpacesWithCrossing;
    private Map<String, Integer> bigramsWithoutSpacesNotCrossing, bigramsWithoutSpacesWithCrossing;

    public Statistic(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String temp;
            StringBuilder result = new StringBuilder();
            while ((temp = reader.readLine()) != null) {
                int count = 0;
                for (int i = 0; i < temp.length(); i++) {
                    if (ALPHABET.contains(temp.charAt(i))) {
                        result.append(temp.charAt(i));
                        count = 0;
                    } else if (temp.charAt(i) == ' ') {
                        if (count == 1) {
                            result.append(' ');
                        }
                        count++;
                    }
                }
            }
            reader.close();
            textWithSpaces = result.toString();
            result = null;
            result = new StringBuilder();
            for (int i = 0; i < textWithSpaces.length(); i++) {
                if (textWithSpaces.charAt(i) != ' ') {
                    result.append(textWithSpaces.charAt(i));
                }
            }
            textWithoutSpaces = result.toString();

            initializeMaps();

            calculateFrequency();

            calculateBigrams(1, textWithSpaces, bigramsWithSpacesNotCrossing);
            calculateBigrams(2, textWithSpaces, bigramsWithSpacesWithCrossing);

            calculateBigrams(1, textWithoutSpaces, bigramsWithoutSpacesNotCrossing);
            calculateBigrams(2, textWithoutSpaces, bigramsWithoutSpacesWithCrossing);


        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void initializeMaps() {
        frequency = new HashMap<>();
        bigramsWithoutSpacesNotCrossing = new HashMap<>();
        bigramsWithoutSpacesWithCrossing = new HashMap<>();
        bigramsWithSpacesNotCrossing = new HashMap<>();
        bigramsWithSpacesWithCrossing = new HashMap<>();
        for (char c1 : base) {
            frequency.put(c1, 0);
            for (char c2 : base) {
                StringBuilder temp = new StringBuilder();
                temp.append(c1);
                temp.append(c2);
                bigramsWithoutSpacesWithCrossing.put(temp.toString(), 0);
                bigramsWithoutSpacesNotCrossing.put(temp.toString(), 0);
                bigramsWithSpacesNotCrossing.put(temp.toString(), 0);
                bigramsWithSpacesWithCrossing.put(temp.toString(), 0);
            }
            StringBuilder temp = new StringBuilder();
            temp.append(" ");
            temp.append(c1);
            bigramsWithSpacesWithCrossing.put(temp.toString(), 0);
            bigramsWithSpacesNotCrossing.put(temp.toString(), 0);
            temp = null;
            temp = new StringBuilder();
            temp.append(c1);
            temp.append(' ');
            bigramsWithSpacesWithCrossing.put(temp.toString(), 0);
            bigramsWithSpacesNotCrossing.put(temp.toString(), 0);
        }
    }

    public void calculateBigrams(int step, String text, Map<String, Integer> map) {
        System.out.println("map size: " + map.size());
        for (int i = 0; i < text.length() - step; i = i + step) {
            StringBuilder temp = new StringBuilder();
            temp.append(text.charAt(i));
            temp.append(text.charAt(i + 1));
            map.put(temp.toString(), map.get(temp.toString()) + 1);
        }
    }

    public void calculateFrequency() {
        for (int i = 0; i < textWithoutSpaces.length(); i++) {
            frequency.put(textWithoutSpaces.charAt(i), frequency.get(textWithoutSpaces.charAt(i)) + 1);
        }
    }

    public double calculateH1() {
        double H1 = 0.;
        for (Map.Entry<Character, Integer> pair : frequency.entrySet()) {
            double p = ( 1. * pair.getValue())/ textWithoutSpaces.length();
            H1 -= p * (Math.log(p) / Math.log(2));
        }
        return H1;
    }

//    public void calculateH2(Map<String, Integer> map, int n) {
//        double H2 = 0.;
//        for (Map.Entry<String, Integer> pair : map.entrySet()) {
//            H2 -= ;
//        }
//    }

    public void print() {
        System.out.println(textWithSpaces);
        System.out.println(textWithSpaces.length());
        System.out.println(textWithoutSpaces);
        System.out.println(textWithoutSpaces.length());
        System.out.println(frequency);
        System.out.println("bigramsWithoutSpacesNotCrossing: " + bigramsWithoutSpacesNotCrossing);
        System.out.println("bigramsWithoutSpacesWithCrossing: " +bigramsWithoutSpacesWithCrossing);
        System.out.println("bigramsWithSpacesNotCrossing: " + bigramsWithSpacesNotCrossing);
        System.out.println("bigramsWithSpacesWithCrossing: " + bigramsWithSpacesWithCrossing);
        sum(bigramsWithoutSpacesNotCrossing);
        sum(bigramsWithoutSpacesWithCrossing);
        sum(bigramsWithSpacesNotCrossing);
        sum(bigramsWithSpacesWithCrossing);
        System.out.println("********************************");
        System.out.println(calculateH1());
    }

    public void printMap(Map<String, Integer> map) {
        for (char c : base ){
            System.out.print(c + "  ");
        }
        for (char c1 : base) {
            System.out.println(c1);
            for (char c2 : base) {
//                System.out.print(c2 + " ");
                StringBuilder temp = new StringBuilder();
                temp.append(c1);
                temp.append(c2);
                System.out.print(map.get(temp.toString()) + " ");
            }
            System.out.println();
        }
    }

    public void printMapToFile(String fileName, Map<String, Integer> map) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(map);
            fileOutputStream.close();
            objectOutputStream.close();
            System.out.println("Saved successfully");
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sum(Map<String, Integer> map) {
        int s = 0;
        for (Map.Entry<String, Integer> pair : map.entrySet()) {
            s += pair.getValue();
        }
        System.out.println("s = " + s);
    }

    public static void main(String[] args) {
        Statistic statistic = new Statistic("text1.txt");
        statistic.print();
        statistic.printMap(statistic.bigramsWithoutSpacesWithCrossing);
//        statistic.printMapToFile("bigramsWithoutSpacesWithCrossing.txt", statistic.bigramsWithoutSpacesWithCrossing);
    }

}