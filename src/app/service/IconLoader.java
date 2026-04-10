package app.service;

import app.AppConfig;
import app.entity.EntityProperties;
import app.entity.Entity;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class IconLoader {
    public static Map<Class<? extends Entity>, ImageIcon> ENTITY_DESCRIPTION_MAP = new HashMap<>();

    // Загружает иконки для каждого класса по его названию
    static {
        for (var clazz : EntityProperties.ENTITY_CLASSES) {
            ENTITY_DESCRIPTION_MAP.put(clazz, loadIcon(clazz.getSimpleName()));
        }
    }

    /**
     * Ищет файл с переданным названием и возвращает иконку, хранящуюся в нём
     * Если файл по названию не найден - возвращает иконку broken_image
     * Если и это не получилось, возвращает null
     */
    public static ImageIcon loadIcon(String name) {
        var res = AppConfig.class.getResource("/icons/" + name.toLowerCase() + ".png");
        if (res == null) {
            res = AppConfig.class.getResource("/icons/broken_image.png");
        }

        if (res == null) return null;
        ImageIcon icon = new ImageIcon(res);
        Image scaled = icon.getImage().getScaledInstance(AppConfig.ICON_SIZE, AppConfig.ICON_SIZE, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    public static ImageIcon getIcon(Class<? extends Entity> clazz) {
        return ENTITY_DESCRIPTION_MAP.get(clazz);
    }

}
