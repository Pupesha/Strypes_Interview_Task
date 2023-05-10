package eu.strypes.helpers;

import java.util.Random;

public class MathHelper {

    public static int randomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
}
