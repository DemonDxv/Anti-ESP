package dev.demon.utils.math;

import java.util.Random;

public class MathUtil {

    public static final Random random = new Random();

    public static int getRandomInteger(int maximum, int minimum) {
        return ((int) (Math.random() * (maximum - minimum))) + minimum;
    }

    public static double getRandomDouble(double maximum, double minimum) {
        return minimum + (maximum - minimum) * random.nextDouble();
    }

    public static float getRandomFloat(float maximum, float minimum) {
        return minimum + (maximum - minimum) * random.nextFloat();
    }
}
