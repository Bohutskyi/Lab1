import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class VigenereCipher {

    private String plainText;
    private String key;
    private String cipherText;
    private static char[] base = {'а','б','в','г','д','е','ж','з','и','й','к','л','м','н','о','п','р','с','т',
            'у','ф','х','ц','ч','ш','щ','ъ','ы','ь','э','ю','я'};
    private static Map<Character, Integer> map = new HashMap<>();

    static {
        map.put('а', 0);
        map.put('б', 1);
        map.put('в', 2);
        map.put('г', 3);
        map.put('д', 4);
        map.put('е', 5);
        map.put('ж', 6);
        map.put('з', 7);
        map.put('и', 8);
        map.put('й', 9);
        map.put('к', 10);
        map.put('л', 11);
        map.put('м', 12);
        map.put('н', 13);
        map.put('о', 14);
        map.put('п', 15);
        map.put('р', 16);
        map.put('с', 17);
        map.put('т', 18);
        map.put('у', 19);
        map.put('ф', 20);
        map.put('х', 21);
        map.put('ц', 22);
        map.put('ч', 23);
        map.put('ш', 24);
        map.put('щ', 25);
        map.put('ъ', 26);
        map.put('ы', 27);
        map.put('ь', 28);
        map.put('э', 29);
        map.put('ю', 30);
        map.put('я', 31);
    }

    public static String decryption(String cipherText, String key) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < cipherText.length(); i++) {
            int temp = map.get(cipherText.charAt(i)) - map.get(key.charAt(i % key.length()));
            if (temp < 0) {
                temp += map.size();
            }
            result.append(base[temp]);
        }
        return result.toString();
    }

    public VigenereCipher(String plaintext, String key) {
        StringBuilder result = new StringBuilder();
        int count = 0;
        for (int i = 0; i < plaintext.length(); i++) {
            if (map.containsKey(plaintext.charAt(i))) {
                result.append(plaintext.charAt(i));
                count = 0;
            } else {
                count++;
                if (count == 1) {
                    result.append(" ");
                }
            }
        }
        this.plainText = result.toString();
        this.key = key;
    }

    public VigenereCipher() {

    }

    public void setKey(String key) {
        this.key = key;
    }

    public void print() {
        System.out.println("plainText: " + plainText);
        System.out.println("key: " + key);
        System.out.println("cipherText: " + cipherText);
    }

    public String encrypt() {
        int r = key.length();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < plainText.length(); i++) {
            if (plainText.charAt(i) != ' ') {
//                int j = i % r;
//                int t = map.get(plainText.charAt(i));
//                t += map.get(key.charAt(j));
//                t %= map.size();
//                result.append(base[t]);
                result.append(base[(map.get(plainText.charAt(i)) + map.get(key.charAt(i % r))) % map.size()]);
            }
//            result.append(base[(map.get(plainText.charAt(i)) + map.get(key.charAt(i % r))) % map.size()]);
        }
        return result.toString();
    }

    public static String randomString(int n) {
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < n; i++) {
            result.append(base[random.nextInt(map.size())]);
        }
        return result.toString();
    }

    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }

    public void setPlainText(String fileName, boolean b) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String temp;
            StringBuilder result = new StringBuilder();
            int count = 0;
            while ((temp = reader.readLine()) != null) {
                for (int i = 0; i < temp.length(); i++) {
                    if (map.containsKey(temp.charAt(i))) {
                        result.append(temp.charAt(i));
                        count = 0;
                    } else {
                        count++;
                        if (count == 1) {
                            result.append(" ");
                        }
                    }
                }
            }
            reader.close();
            this.plainText = result.toString();
        } catch (FileNotFoundException e) {
            System.out.println("Error. File not found");
        } catch (IOException e) {
            System.out.println("Error. Reading");
        }
    }

    public String getCipherText() {
        return cipherText;
    }

    public void setCipherText(String cipherText) {
        this.cipherText = cipherText;
    }

    public void setCipherText(String fileName, boolean b) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String temp;
            StringBuilder result = new StringBuilder();
            while ((temp = reader.readLine()) != null) {
                result.append(temp);
            }
            reader.close();
            this.cipherText = result.toString();
        } catch (FileNotFoundException e) {
            System.out.println("Error. File not found");
        } catch (IOException e) {
            System.out.println("Error. Reading");
        }
    }

    public String getKey() {
        return key;
    }

    public void decryptFirstWay() {
        System.out.println("I = " + 0.055);
        System.out.println("I0 = " + (1. / Alphabet.base.length));
        System.out.println(cipherText);
        for (int i = 10; i <= 20; i++) {

            //1) Розбивка шифртексту на блоки
            //Визначення к-ті блоків (size) та розміру кожного блоку (blockSize)
            int size, blockSize;
            if (cipherText.length() % i == 0) {
                size = cipherText.length() / i;
                blockSize = cipherText.length() / size;
            } else {
                size = (cipherText.length() / i) + 1;
                blockSize = (cipherText.length() / size) + 1;
            }

            //Знаходження вмісту блоків
            Index[] temp = new Index[size];
            for (int j = 0; j < size - 1; j++) {
                temp[j] = new Index(cipherText.substring(j * blockSize, (j + 1) * blockSize));
            }
            temp[size - 1] = new Index(cipherText.substring(blockSize * (size - 1)));

            //Обчислення значення ідексу відповідності для кожного з кандидатів
            System.out.println("i = " + i);
            System.out.println("I = " + 0.055);
            System.out.println("I0 = " + (1. / Alphabet.base.length));
            double s = 0.;
            for (Index index : temp) {
//                index.calculateI();
                System.out.println(index.calculateI());
                s += index.calculateI();
            }
            s /= temp.length;
            System.out.println("s = " + s);
            System.out.println("-----------------------------------------------------------");



//            for (Index index : temp) {
//                System.out.print(index);
//            }
//            System.out.println();
        }
    }

    public void d() {
        for (int i = 10; i < 20; i++) {
            decryption(i);
        }
    }

    public void decryption(int i) {
        System.out.println("I = " + 0.055);
        System.out.println("I0 = " + (1. / Alphabet.base.length));
        System.out.println(cipherText);

        //1) Розбивка шифртексту на блоки
        //Визначення к-ті блоків (size) та розміру кожного блоку (blockSize)
        int size, blockSize;
        if (cipherText.length() % i == 0) {
            size = cipherText.length() / i;
            blockSize = cipherText.length() / size;
        } else {
            size = (cipherText.length() / i) + 1;
            blockSize = (cipherText.length() / size) + 1;
        }

        //Знаходження вмісту блоків
        Index[] temp = new Index[size];
        for (int j = 0; j < size - 1; j++) {
            temp[j] = new Index(cipherText.substring(j * blockSize, (j + 1) * blockSize));
        }
        temp[size - 1] = new Index(cipherText.substring(blockSize * (size - 1)));

        //Обчислення значення ідексу відповідності для кожного з кандидатів
        double s = 0.;
        for (Index index : temp) {
            double tempDouble = index.calculateI();
//            System.out.println(tempDouble);
            s += tempDouble;
        }
        s /= temp.length;
        System.out.println("s = " + s);
        System.out.println("i: " + i);
        System.out.println("-----------------------------------------------------------");
    }

    public void decryptSecondWay() {
        String key;
        String cText = VigenereCipher.randomString(cipherText.length());
        for (int i = 2; i <= 30; i++) {
            System.out.println("i: " + i);
            VigenereCipher vigenereCipher = new VigenereCipher(cText, VigenereCipher.randomString(i));
            vigenereCipher.print();
            Index index = new Index(vigenereCipher.encrypt());
            System.out.println("I = " + index.calculateI());
        }
        System.out.println("=====================================================================");
        System.out.println(new Index(this.cipherText).calculateI());
    }

    public void guessingKey(int period) {
//        Index index = new Index(this.cipherText);
//        index.getFrequency();
//        for (Map.Entry<Character, Double> temp : index.getFrequencySymbolsMap().entrySet()) {
//            System.out.println(temp.getKey() + " " + temp.getValue());
//        }
//        List<Map.Entry<Character, Double>> entries = new ArrayList<Map.Entry<Character, Double>>(index.getFrequencySymbolsMap().entrySet());
//        Collections.sort(entries, new Comparator<Map.Entry<Character, Double>>() {
//            @Override
//            public int compare(Map.Entry<Character, Double> o1, Map.Entry<Character, Double> o2) {
//                return (int) (o2.getValue() - o1.getValue());
//            }
//        });
//        System.out.println("---------------------");
//        for (Map.Entry<Character, Double> entry : entries) {
//            System.out.println(entry.getKey() + " " + entry.getValue());
//        }

        /*String text = cipherText.substring(0, period);
        System.out.println(text);
        Index index = new Index(text);
        index.getFrequency();
        List<Map.Entry<Character, Double>> entries = new ArrayList<Map.Entry<Character, Double>>(index.getFrequencySymbolsMap().entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<Character, Double>>() {
            @Override
            public int compare(Map.Entry<Character, Double> o1, Map.Entry<Character, Double> o2) {
                return (int) (o2.getValue() - o1.getValue());
            }
        });
        for (Map.Entry<Character, Double> entry : entries) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }*/
        StringBuilder[] temp = new StringBuilder[period];
        for (int i = 0; i < period; i++) {
            StringBuilder s = new StringBuilder();
            for (int j = 0; j < cipherText.length() / period; j++) {
                s.append(cipherText.charAt(period * j + i));
            }
            temp[i] = s;
        }
        if ((cipherText.length() % period) != 0) {
            for (int j = 0; j < cipherText.length() % period; j++) {
                temp[j].append(cipherText.charAt(cipherText.length() + j - cipherText.length() % period));
            }
        }
        StringBuilder key = new StringBuilder();
        List<Map.Entry<Character, Double>> entries = null;
        for (StringBuilder s : temp) {
            System.out.println(s);
            Index index = new Index(s.toString());
            index.getFrequency();
            entries = new ArrayList<>(index.getFrequencySymbolsMap().entrySet());
            Collections.sort(entries, new Comparator<Map.Entry<Character, Double>>() {
                @Override
                public int compare(Map.Entry<Character, Double> o1, Map.Entry<Character, Double> o2) {
                    return (int) (o2.getValue() - o1.getValue());
                }
            });
            for (Map.Entry<Character, Double> entry : entries) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }
            int k = (- map.get('о') + map.get(entries.get(0).getKey())) % map.size();
            if (k < 0) {
                k += map.size();
            }
            key.append(base[k]);
//            key.append(base[(- map.get('о') + map.get(entries.get(0).getKey())) % map.size()]);
        }
        System.out.println("*****************************************");
        System.out.println(key);
        System.out.println(VigenereCipher.decryption(cipherText, key.toString()));
//        key.setCharAt(1, 'е');
        key.setCharAt(1, setChar('а', entries.get(1).getKey()));
        System.out.println(key);
        System.out.println(VigenereCipher.decryption(cipherText, key.toString()));
        key.setCharAt(1, setChar('и', entries.get(1).getKey()));
        System.out.println(key);
        System.out.println(VigenereCipher.decryption(cipherText, key.toString()));

        key.setCharAt(12, 'к');
        System.out.println(key);
        System.out.println(VigenereCipher.decryption(cipherText, key.toString()));

        key.setCharAt(4, 'а');
        key.setCharAt(5, 'я');
        System.out.println(key);
        System.out.println(VigenereCipher.decryption(cipherText, key.toString()));

        key.setCharAt(1, 'к');
        System.out.println(key);
        System.out.println(VigenereCipher.decryption(cipherText, key.toString()));
    }

    private static char setChar(char c, char entry) {
        int k = (- map.get(c) + map.get(entry)) % map.size();
        if (k < 0) {
            k += map.size();
        }
        return base[k];
    }

    public static void main(String[] args) {
        VigenereCipher vigenereCipher = new VigenereCipher();
        vigenereCipher.setCipherText("text.txt", false);
//        vigenereCipher.print();
//        vigenereCipher.decryption(14);
//        vigenereCipher.d();
        vigenereCipher.guessingKey(14);
    }
}
