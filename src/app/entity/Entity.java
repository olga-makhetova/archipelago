package app.entity;

import app.service.Island;

import javax.swing.*;
import java.awt.*;

/**
 * Начальный класс - сущность
 */
public abstract class Entity {
    protected ImageIcon icon;
    protected Island island;
    protected Point point;
    protected String specieName;

    // обращение только из фабрики при создании или потомками
    protected Entity() {
    }

    public void setPoint(Point point) {
        this.point = point;
        //log(getFullName() + " на позиции (" + point.x + ", " + point.y + ")");
    }

    public Point getPoint() {
        return point;
    }

    @Override
    public String toString() {
        return specieName;
    }

    public String getFullName() {
        return specieName;
    }

    public Island getIsland() {
        return island;
    }

    public void log(String text) {
        island.log(text);
    }

    public ImageIcon getImageIcon() {
        return icon;
    }

    void setImageIcon(ImageIcon icon) {
        this.icon = icon;
    }

    void setIsland(Island island) {
        this.island = island;
    }
}
