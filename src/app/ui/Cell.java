package app.ui;

import javax.swing.*;
import java.awt.*;

import app.AppConfig;

/**
 * Класс представляет ячейку на форме
 */
public class Cell extends JLabel {
    public Cell() {
        super();
        setPreferredSize(new Dimension(app.AppConfig.CELL_SIZE, AppConfig.CELL_SIZE));
        setMaximumSize(new Dimension(app.AppConfig.CELL_SIZE, AppConfig.CELL_SIZE));
        setMinimumSize(new Dimension(app.AppConfig.CELL_SIZE, AppConfig.CELL_SIZE));
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
    }

    /**
     * Устанавливает иконку и текст подсказки
     */
    public void put(Icon icon, String toolTipText) {
        setIcon(icon);
        this.setToolTipText(toolTipText);
    }

}
