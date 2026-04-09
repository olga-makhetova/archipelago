package app.model;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Направления движения
 */
public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    private static final Direction[] VALUES = Direction.values();

    /**
     * Потокобезопасно возвращает случайное направление
     */
    public static Direction getRandomDirection() {
        int randomIndex = ThreadLocalRandom.current().nextInt(VALUES.length);
        return VALUES[randomIndex];
    }
}
