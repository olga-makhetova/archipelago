package app;

/**
 * Здесь хранятся разные константы
 */
public class AppConfig {
    public static final int CELL_SIZE = 30;
    public static final int ICON_SIZE = 24;
    //public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    public static final double INIT_DENSITY = 0.1;
    public static final double MAX_DENSITY = 0.8;
    public static final double VERY_SMALL = 0.01;  // для сравнения double с нулем
    public static final double VARIANCE = 0.05;  // Определяет, насколько разными получаются животные


    public static final int TIME_TICK = 500;
    public static final int INIT_DELAY = 500;
    public static final int THREAD_POOL_SIZE = 3;


}
