package app;

import app.service.Island;
import app.ui.StartWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * Основной класс приложения
 */
public class Archipelago {
    // список островов
    private static final List<Island> islandList = new ArrayList<>();

    /**
     * Создаёт остров по заданным параметрам
     */
    public static int addIsland(String title, int rows, int cols) {
        Island island = new Island(title, rows, cols);
        islandList.add(island);
        return islandList.size();
    }

    /**
     * Возвращает случайный остров из нашего списка (используется для телепорта)
     */
    public static Island getRandomIsland() {
        return islandList.get((int) (Math.random() * islandList.size()));
    }

    /**
     * Удаляет остров из списка
     */
    public static void removeIsland(Island island) {
        islandList.remove(island);
    }

    /**
     * Запуск приложения
     */
    static void main(String[] ignoredArgs) {
        StartWindow startWindow = new StartWindow();
        startWindow.run();
    }
}