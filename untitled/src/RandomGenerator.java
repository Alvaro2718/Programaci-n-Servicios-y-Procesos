import java.util.Random;

public class RandomGenerator {
    public static void main(String[] args) {
        Random random = new Random();

        for (int i = 0; i < 35; i++) {
            int number = random.nextInt(101); // entre 0 y 100 incluidos
            System.out.println(number);
        }
    }
}

