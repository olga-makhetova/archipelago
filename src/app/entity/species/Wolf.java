package app.entity.species;

import app.entity.Predator;

public class Wolf extends Predator {
    protected Wolf() {
        super();
        if (getGender()) {
            specieName = "Волчица";
        } else {
            specieName = "Волк";
        }
    }
}
