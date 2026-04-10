package app.entity.species;

import app.AppConfig;
import app.entity.Living;
import app.entity.EntityProperties;
import app.entity.EntityFactory;

/**
 * Растение
 */
public class Plant extends Living {
    protected Plant() {
        regnum = "Растение";
        specieName = "Растение";
    }

    /**
     * Действие каждый такт: рост плюс периодическое размножение
     */
    @Override
    public void run() {
        super.run();
        weight += EntityProperties.getFoodWeight(Plant.class); // чуток подросло
        island.setToPosition(this, point); // обновим подсказку
        double pregnancyTime = EntityProperties.getPregnancyTime(Plant.class);
        if (age > pregnancyTime // растение достаточно взрослое
                && ((int) age % (int) pregnancyTime) == 0 // раз в pregnancyTime тактов
                && island.canReceive() // если остров не перенаселён
        ) {
            EntityFactory.spawnEntity(island, Plant.class);
            island.log("☘ " + this.getFullName() + " произвёло потомство!");
        }
    }

    /**
     * Кто-то отъел от растения
     */
    public void eaten(double weight) {
        this.weight -= weight;
        if (Math.abs(this.weight) <= AppConfig.VERY_SMALL) {
            dead("поедания");
        }
    }
}
