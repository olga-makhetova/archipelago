package app.entity.species;

import app.entity.Herbivorous;

public class Sheep extends Herbivorous {
    protected Sheep() {
        super();
        if (getGender()) {
            specieName = "Овца";
        } else {
            specieName = "Баран";
        }
    }
}
