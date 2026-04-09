package app.entity;

import java.util.concurrent.ScheduledFuture;

/**
 * Живая сущность
 */
public abstract class Living extends Entity implements Runnable {
    protected boolean alive = true;
    protected double age;
    protected double maxAge;
    protected int timeTick;
    protected double weight;
    protected double health; // процент здоровья
    protected String name;
    protected String regnum = ""; // Царство: животное или растение

    private ScheduledFuture future;

    /**
     * Появляется полностью здоровое
     */
    public Living() {
        super();
        health = 100;
    }

    /**
     * Действие в такт - старение
     */
    @Override
    public void run() {
        age += timeTick * 0.001; // такты в миллисекундах, а возраст в секундах
        if (age >= maxAge) dead("старости");
    }

    /**
     * Получение урона
     */
    public void getHurt(double damage) {
        health -= damage;
        if (health <= 0) dead("ран");
    }

    /**
     * Смерть
     */
    public void dead(String reason) {
        alive = false;
        future.cancel(false);
        island.clear(this);
        log("✝ %s %s умерло от %s в возрасте %d секунд".formatted(regnum, getFullName(), reason, (int) age));
    }

    @Override
    public String getFullName() {
        return specieName + " " + name;
    }


    @Override
    public String toString() {
        return "%s вес %.2f, возраст %d".formatted(getFullName(), weight, (int) age);
    }

    public String getName() {
        return name;
    }


    public double getWeight() {
        return weight;
    }


    // обращение только из фабрики при создании - package-private
    void setWeight(double weight) {
        this.weight = weight;
    }

    void setTimeTick(int timeTick) {
        this.timeTick = timeTick;
    }

    void setName(String name) {
        this.name = name;
    }

    void setFuture(ScheduledFuture future) {
        this.future = future;
    }

    void setMaxAge(double maxAge) {
        this.maxAge = maxAge;
    }

    int getTimeTick() {
        return timeTick;
    }

}
