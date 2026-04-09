package app.entity;

import app.AppConfig;
import app.entity.species.*;
import app.model.EntityDescription;

import java.util.*;

/**
 * Здесь хранятся все классы, которые могут быть помещены на карту, и их свойства в виде карты
 */
public class EntityProperties {
    // @formatter:off
    // Other
    private static final Map<Class<? extends Entity>, EntityDescription> OTHER_DESCRIPTION_MAP = Map.of(
        Gate.class, new EntityDescription(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    );

    // Plant
    private static final Map<Class<? extends Living>, EntityDescription> PLANT_DESCRIPTION_MAP = Map.of(
        //  maxAge weight  fooWeight   stepCount   spont   pregTime    strength fertility genderProbability hungryTick
        Plant.class,       new EntityDescription(9999, 1, 0.01, 0, 0, 50, 0, 0.5, 0.5, 0.001)
    );

    // Animal
    // Predator
    private static final Map<Class<? extends Predator>, EntityDescription> PREDATOR_DESCRIPTION_MAP = Map.of(
        //  maxAge weight  fooWeight   stepCount   spont   pregTime    strength fertility genderProbability hungryTick
        Bear.class,        new EntityDescription(1200, 500, 80, 1, 0.01, 8, 10, 0.5, 0.5, 0.001),
        Eagle.class,       new EntityDescription(700,  6,   1,  1, 0.03, 8, 10, 0.5, 0.5, 0.001),
        Fox.class,         new EntityDescription(750,  8,   2,  1, 0.03, 8, 10, 0.5, 0.5, 0.001),
        Snake.class,       new EntityDescription(250,  15,  3,  1, 0.03, 8, 10, 0.5, 0.5, 0.001),
        Wolf.class,        new EntityDescription(850,  50,  8,  1, 0.03, 8, 10, 0.5, 0.5, 0.001)
    );

    // Herbivorous
    private static final Map<Class<? extends Herbivorous>, EntityDescription> HERBIVOROUS_DESCRIPTION_MAP = Map.of(
        //  maxAge weight  fooWeight   stepCount   spont   pregTime    strength fertility genderProbability hungryTick
        Boar.class,        new EntityDescription(250,  400,  50,   1, 0.03, 8, 10, 0.5, 0.5, 0.001),
        Buffalo.class,     new EntityDescription(250,  700,  100,  1, 0.03, 8, 10, 0.5, 0.5, 0.001),
        Caterpillar.class, new EntityDescription(50,   0.05, 0.01, 1, 0.03, 8, 10, 0.5, 0.5, 0.001),
        Deer.class,        new EntityDescription(700,  300,  50,   1, 0.03, 8, 10, 0.5, 0.5, 0.001),
        Duck.class,        new EntityDescription(100,  1,    0.15, 1, 0.03, 8, 10, 0.5, 0.5, 0.001),
        Goat.class,        new EntityDescription(500,  60,   10,   1, 0.03, 8, 10, 0.5, 0.5, 0.001),
        Horse.class,       new EntityDescription(750,  400,  60,   1, 0.03, 8, 10, 0.5, 0.5, 0.001),
        Mouse.class,       new EntityDescription(250,  0.05, 0.01, 1, 0.03, 8, 10, 0.5, 0.5, 0.001),
        Rabbit.class,      new EntityDescription(550,  2,    0.45, 5, 0.07, 8, 10, 0.5, 0.5, 0.001),
        Sheep.class,       new EntityDescription(750,  70,   15,   1, 0.03, 8, 10, 0.5, 0.5, 0.001)
    );
    // @formatter:on

    // животные
    private static final Map<Class<? extends Entity>, EntityDescription> ANIMAL_DESCRIPTION_MAP = new HashMap<>();

    static {
        ANIMAL_DESCRIPTION_MAP.putAll(PREDATOR_DESCRIPTION_MAP);
        ANIMAL_DESCRIPTION_MAP.putAll(HERBIVOROUS_DESCRIPTION_MAP);
    }

    // живущие
    private static final Map<Class<? extends Entity>, EntityDescription> LIVING_DESCRIPTION_MAP = new HashMap<>();

    static {
        LIVING_DESCRIPTION_MAP.putAll(ANIMAL_DESCRIPTION_MAP);
        LIVING_DESCRIPTION_MAP.putAll(PLANT_DESCRIPTION_MAP);
    }

    // все
    public static final Map<Class<? extends Entity>, EntityDescription> ALL_DESCRIPTION_MAP = new HashMap<>();

    static {
        ALL_DESCRIPTION_MAP.putAll(LIVING_DESCRIPTION_MAP);
        ALL_DESCRIPTION_MAP.putAll(OTHER_DESCRIPTION_MAP);
    }

    /**
     * Список поддерживаемых классов
     */
    public static final List<Class<? extends Entity>> ENTITY_CLASSES = new ArrayList<>(ALL_DESCRIPTION_MAP.keySet());

    // геттеры

    // Для живущих
    public static double getMaxAge(Class<? extends Living> clazz) {
        return LIVING_DESCRIPTION_MAP.get(clazz).maxAge();
    }

    public static double getWeight(Class<? extends Living> clazz) {
        return LIVING_DESCRIPTION_MAP.get(clazz).weight();
    }

    public static double getFoodWeight(Class<? extends Living> clazz) {
        return LIVING_DESCRIPTION_MAP.get(clazz).foodWeight();
    }

    public static double getPregnancyTime(Class<? extends Living> clazz) {
        return LIVING_DESCRIPTION_MAP.get(clazz).pregnancyTime();
    }

    public static int getTimeTick(Class<? extends Living> clazz) {
        return AppConfig.TIME_TICK / Math.max(LIVING_DESCRIPTION_MAP.get(clazz).stepCount(), 1);
    }

    // Для животных
    public static double getSpontaneous(Class<? extends Animal> clazz) {
        return ANIMAL_DESCRIPTION_MAP.get(clazz).spontaneous();
    }

    public static double getStrength(Class<? extends Animal> clazz) {
        return ANIMAL_DESCRIPTION_MAP.get(clazz).strength();
    }

    public static double getFertility(Class<? extends Animal> clazz) {
        return ANIMAL_DESCRIPTION_MAP.get(clazz).fertility();
    }

    public static double getGenderProbability(Class<? extends Animal> clazz) {
        return ANIMAL_DESCRIPTION_MAP.get(clazz).genderProbability();
    }

    public static double getHungryTick(Class<? extends Animal> clazz) {
        return ANIMAL_DESCRIPTION_MAP.get(clazz).hungryTick();
    }
}
