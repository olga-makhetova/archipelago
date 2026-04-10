package app.service;

import app.AppConfig;
import app.Archipelago;
import app.entity.Animal;
import app.entity.Entity;
import app.entity.EntityFactory;
import app.ui.IslandWindow;

import java.awt.*;
import java.util.concurrent.*;

/**
 * Представление острова
 */

public class Island {
    private final int rows, cols;
    private final String name;
    private final Entity[][] field;
    private final IslandWindow islandWindow;

    public final ScheduledExecutorService pool = Executors.newScheduledThreadPool(AppConfig.THREAD_POOL_SIZE);
    private int population;

    /**
     * Создаёт и инициализирует остров, запускает окно и населяет сущностями
     */
    public Island(String name, int rows, int cols) {
        this.cols = cols;
        this.rows = rows;
        this.name = name;

        // Создание и инициализация окна
        islandWindow = new IslandWindow(name, rows, cols);
        islandWindow.setOnClose(this::close); // Задаём действие при закрытии окна

        // Население
        field = new Entity[cols][rows];
        EntityFactory.spawnEntityList(this);

        log("Остров создан!");
        islandWindow.run();
    }

    /**
     * Проверка, не перенаселён ли остров
     */
    public synchronized boolean canReceive() {
        return (double) population / (double) rows / (double) cols <= AppConfig.MAX_DENSITY;
    }

    /**
     * Потокобезопасно принимает на себя сущность
     */
    public synchronized void receiveEntity(Entity entity) {
        if (!canReceive()) return;

        // В цикле ищет свободную точку
        while (true) {
            Point point = getRandomPoint();
            if (field[point.x][point.y] == null) {
                setToPosition(entity, point);
                break;
            }
        }

        islandWindow.setPopulation(population++);
    }

    /**
     * Потокобезопасно генерирует случайную точку на острове
     *
     * @return возвращает созданную точку
     *
     */
    private Point getRandomPoint() {
        return new Point(ThreadLocalRandom.current().nextInt(cols), ThreadLocalRandom.current().nextInt(rows));
    }

    /**
     * Потокобезопасно решает, можно ли животному совершить передвижение в новую точку.
     * Возвращает false, если нельзя, а так же вызывает менеджер коллизий.
     */
    public synchronized boolean resolveMove(Animal animal, Point newPoint) {
        if (newPoint.x >= cols || newPoint.y >= rows || newPoint.x < 0 || newPoint.y < 0) {
            return false;
        }

        Entity entity = field[newPoint.x][newPoint.y];
        if (entity != null) {
            CollisionResolver.resolve(animal, entity);
            return false;
        }

        moveToPosition(animal, newPoint);
        return true;

    }

    /**
     * Потокобезопасно передвигает сущность на новую точку
     */
    private synchronized void moveToPosition(Entity entity, Point newPoint) {
        clearPoint(entity.getPoint()); // если где-то уже стоит - убираем
        setToPosition(entity, newPoint);
    }

    /**
     * Выставляет сущность на позицию
     */
    public synchronized void setToPosition(Entity entity, Point point) {
        entity.setPoint(point);
        field[point.x][point.y] = entity;
        islandWindow.draw(entity);
    }

    /**
     * Очищает ячейку от сущности
     */
    public synchronized void clearPoint(Point point) {
        if (point == null) return;
        field[point.x][point.y] = null;
        islandWindow.clear(point);
    }

    /**
     * Очищает ячейку от сущности и уменьшает население
     */
    public synchronized void clear(Entity entity) {
        clearPoint(entity.getPoint());
        population--;
        islandWindow.setPopulation(population);
    }

    /**
     * При закрытии острова - закрывает пул и удаляется из списка островов Архипелага
     */
    public void close() {
        pool.shutdown();
        Archipelago.removeIsland(this);
    }

    /**
     * Выводит текст на окно
     */
    public void log(String text) {
        islandWindow.log(text);
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public String getName() {
        return name;
    }

}
