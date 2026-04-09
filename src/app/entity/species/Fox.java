package app.entity.species;

import app.entity.Predator;

public class Fox extends Predator {
    protected Fox() {
        super();
        if (getGender()) {
            specieName = "Лиса";
        } else {
            specieName = "Лис";
        }
    }
}

