package app.entity.species;

import app.entity.Herbivorous;


public class Rabbit extends Herbivorous {
    protected Rabbit() {
        super();
        if (getGender()) {
            specieName = "Крольчиха";
        } else {
            specieName = "Кролик";
        }
    }
}
