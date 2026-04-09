package app.entity;

import app.AppConfig;
import app.model.Direction;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Представление животного
 */
public abstract class Animal extends Living {
    private final boolean gender;
    private double spontaneous;
    private boolean isPregnant = false;
    private Direction direction = Direction.getRandomDirection();

    protected double strength;
    protected double fullness = 1; // процент насыщения


    protected Animal() {
        super();
        gender = randomBoolean(EntityProperties.getGenderProbability(getClass()));
        regnum = "Животное";
    }

    /**
     * Поведение каждый такт: увеличивается голод, в зависимости от спонтанности может смениться направление
     */
    @Override
    public void run() {
        super.run();
        if (!alive) return;

        fullness -= EntityProperties.getHungryTick(getClass());
        if (fullness <= 0.0) {
            dead("голода");
            return;
        }

        if (randomBoolean(spontaneous)) {
            changeDirection();
        }
        requestStep();
    }

    /**
     * Ест еду, пока не насытится или пока еда не закончится
     *
     * @return сколько съело
     */
    public double eat(double foodWeight) {
        if (foodWeight < AppConfig.VERY_SMALL // еды слишком мало
                || 1.0 - fullness <= AppConfig.VERY_SMALL // животное достаточно сыто
        ) return 0.0;

        double maxFoodWeight = EntityProperties.getFoodWeight(this.getClass()); // максимальное количество еды, которое животное может съесть
        double actualEat = Math.min(foodWeight, maxFoodWeight); // сколько съест с учётом имеющегося веса еды

        fullness += (1.0 - fullness) * actualEat / maxFoodWeight;
        log("♨ Животное %s съело %.2f еды, его сытость теперь: %d%%".formatted(getFullName(), actualEat, (int) (100 * fullness)));
        return actualEat;
    }

    /**
     * Рождение потомства
     */
    public void giveBirth() {
        if (!gender) return;
        Animal newAnimal = (Animal) EntityFactory.spawnEntity(island, this.getClass());
        String offspring = newAnimal.gender ? "дочь" : "сына";
        island.log("☻ " + this.getFullName() + " родила " + offspring + " по имени " + newAnimal.getName());
        isPregnant = false;
    }

    /**
     * Попытка забеременеть с проверкой на пол и уже существующую беременность
     */
    public void getPregnant() {
        if (!gender) return;
        if (isPregnant) return;
        if (randomBoolean(EntityProperties.getFertility(getClass())) && island.canReceive()) {
            isPregnant = true;
            log("☯ " + this.getFullName() + " собирается стать мамой!");
            int delay = (int) (EntityProperties.ALL_DESCRIPTION_MAP.get(this.getClass()).pregnancyTime() * 1000);
            island.pool.schedule(this::giveBirth, delay, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * Попытка сделать шаг, остров решает, разрешить или нет.
     * Если не разрешает - меняем направление, иначе - остров сам передвинет и поменяет точку
     */
    public void requestStep() {
        Point newPoint = (Point) point.clone();
        switch (direction) {
            case UP -> newPoint.y++;
            case DOWN -> newPoint.y--;
            case LEFT -> newPoint.x++;
            case RIGHT -> newPoint.x--;
        }
        if (!island.resolveMove(this, newPoint)) {
            changeDirection();
        }

    }

    private boolean randomBoolean(double probability) {
        return ThreadLocalRandom.current().nextDouble() < probability;
    }

    private void changeDirection() {
        direction = Direction.getRandomDirection();
    }

    @Override
    public String toString() {
        return super.toString() + ", сытость " + (int) (fullness * 100) + "%";
    }

    @Override
    public String getFullName() {
        return specieName + (gender ? "♀" : "♂") + getName();
    }


    public boolean getGender() {
        return gender;
    }

    public boolean isPregnant() {
        return isPregnant;
    }

    public double getStrength() {
        return strength;
    }

    // обращение только из фабрики при создании - package-private
    void setSpontaneous(double spontaneous) {
        this.spontaneous = spontaneous;
    }

    void setStrength(double strength) {
        this.strength = strength;
    }

}
