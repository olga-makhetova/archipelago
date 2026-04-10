package app.ui;

import app.Archipelago;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Представляет начальное окно приложения, отсюда создаются острова
 */
public class StartWindow extends JFrame {
    private final JPanel mainPanel = new JPanel(new GridLayout(5, 0));
    JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
    private final JButton btCreate = new JButton("Создать");
    private final JTextField txtIslandName = new JTextField();
    private final JSpinner spIslandRows = new JSpinner();
    private final JSpinner spIslandCols = new JSpinner();

    /**
     * Действие на кнопку создания острова
     */
    public void btCreateActionPerformed(ActionEvent e) {
        final int islandNumber = Archipelago.addIsland(
                txtIslandName.getText(),
                (Integer) spIslandRows.getValue(),
                (Integer) spIslandCols.getValue());
        txtIslandName.setText("Остров " + islandNumber);
    }

    /**
     * конструктор
     */
    public StartWindow() {
        setTitle("Archipelago");

        init();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Запускает форму
     */
    public void run() {
        SwingUtilities.invokeLater(() -> this.setVisible(true));
    }

    /**
     * Инициализация компонентов
     */
    private void init() {

        /*--------------- настройка layout ---------------*/
        setContentPane(mainPanel);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainPanel.add(this.txtIslandName);

        bottomPanel.add(spIslandRows);
        bottomPanel.add(new JLabel("x"));
        bottomPanel.add(spIslandCols);
        bottomPanel.add(btCreate);

        mainPanel.add(Box.createVerticalStrut(8)); // отступ между строками
        mainPanel.add(bottomPanel);

        /*--------------- настройка компонентов ---------------*/
        // настраиваем спиннеры
        spIslandRows.setModel(new SpinnerNumberModel(15, 1, 100, 1));
        spIslandCols.setModel(new SpinnerNumberModel(15, 1, 100, 1));

        // текстовое поле
        txtIslandName.setText("Остров 0");

        // кнопка
        btCreate.addActionListener(this::btCreateActionPerformed);
    }


}
