import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//        VigenereCipher vigenereCipher = new VigenereCipher();
//        vigenereCipher.setPlainText("text1.txt", false);
//        vigenereCipher.setKey("некийключдляшифрования");
//        vigenereCipher.print();
//        System.out.println("--------------------");
//        System.out.println(vigenereCipher.encrypt());

        System.out.println("Бунько-дурко!!!");
        VigenereCipher vigenereCipher = new VigenereCipher("слонийдутьнапивнич", "ключ");
        vigenereCipher.print();
        System.out.println(vigenereCipher.encrypt());

        System.out.println(VigenereCipher.decryption("ыцмдтфвкьзлчщуадтв", "ключ"));

//        String plainText = "хотяпонятиекриптоанализбыловведеносравнительнонедавнонекоторыеметодывзломабылииз";
//        int [] set = {2, 3, 3, 5, 10, 15, 20};
//        Index index = new Index(plainText, Alphabet.base.length);
//        System.out.println(index.calculateI());
//        System.out.println("------------------------------------------------");
//
//        for (int i : set) {
//            String key = VigenereCipher.randomString(i);
//            VigenereCipher vigenereCipher = new VigenereCipher(plainText, key);
//            vigenereCipher.print();
//            String temp = vigenereCipher.encrypt();
//            System.out.println("cipherText: " + temp);
//            index = new Index(temp, Alphabet.base.length);
//            System.out.println("I = " + index.calculateI());
//        }



    }

}
