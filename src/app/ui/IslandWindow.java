package app.ui;

import app.entity.Entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Представляет графическое отображение острова через Swing
 */
public class IslandWindow extends JFrame {
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final String islandName;
    private int population = 0;
    private final int rows;
    private final int cols;
    private final Cell[][] cells;
    private final JPanel panelMain = new JPanel();
    private final JPanel panelGrid = new JPanel();
    private final JTextArea logArea = new JTextArea();

    /**
     * Создаёт окно с заданным количеством ячеек
     */
    public IslandWindow(String islandName, int rows, int cols) {
        this.islandName = islandName;
        setTitle(calcTitle());
        cells = new Cell[cols][rows];
        this.cols = cols;
        this.rows = rows;

        init();
        initToolTipTime();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setResizable(false);

        //setLocation(new Random().nextInt(AppConfig.SCREEN_SIZE.width), new Random().nextInt(AppConfig.SCREEN_SIZE.height));
    }

    /**
     * Запуск
     */
    public void run() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    /**
     * Инициализирует менеджер подсказок
     */
    private void initToolTipTime() {
        ToolTipManager toolTipManager = ToolTipManager.sharedInstance();
        toolTipManager.setInitialDelay(0);    // Задержка перед появлением
        toolTipManager.setDismissDelay(5000); // Как долго тултип остаётся видимым
        toolTipManager.setReshowDelay(0);     // Задержка при переходе между компонентами
    }

    /**
     * Нехитрым образом вычисляет заголовок окна
     */
    private String calcTitle() {
        return islandName + ", население " + population;
    }

    /**
     * Меняет население и перевыставляет заголовок окна
     */
    public void setPopulation(int population) {
        this.population = population;
        setTitle(calcTitle());
    }

    /**
     * Устанавливает поведение окна при закрытии, вызывается с острова при создании окна
     */
    public void setOnClose(Runnable onClose) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onClose.run();
            }
        });
    }

    /**
     * Инициализация компонентов
     */
    private void init() {
        setContentPane(panelMain);
        panelMain.setLayout(new BorderLayout());

        // Лог слева
        logArea.setEditable(false);
//        logArea.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
        logArea.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(600, 0)); // ширина лога
        panelMain.add(scrollPane, BorderLayout.WEST);

        // Сетка справа — оборачиваем, чтобы не растягивалась
        panelGrid.setLayout(new GridLayout(rows, cols));
        buildGrid();

        JPanel gridWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        gridWrapper.add(panelGrid);
        panelMain.add(gridWrapper, BorderLayout.CENTER);
    }

    /**
     * Построение сетки
     */
    private void buildGrid() {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                Cell cell = new Cell();
                cells[x][y] = cell;
                panelGrid.add(cell);
            }
        }
    }

    /**
     * Потокобезопасно рисует заданную иконку на ячейке (х, у), выставляет подсказку
     */
    private void draw(ImageIcon icon, int x, int y, String toolTipText) {
        SwingUtilities.invokeLater(() -> cells[x][y].put(icon, toolTipText));
    }

    /**
     * Рисует сущность, исходя из её иконки, положения и текстового представления
     */
    public void draw(Entity entity) {
        draw(entity.getImageIcon(), entity.getPoint().x, entity.getPoint().y, entity.toString());
    }

    /**
     * Потокобезопасно очищает ячейку
     */
    public void clear(Point point) {
        SwingUtilities.invokeLater(() ->
                draw(null, point.x, point.y, null)
        );
    }

    /**
     * Выводит текст в спец окно
     */
    public void log(String text) {
        SwingUtilities.invokeLater(() -> {
            String timeString = LocalDateTime.now().format(FORMATTER);
            logArea.append("[" + timeString + "] " + text + "\n");
        });
    }
}
