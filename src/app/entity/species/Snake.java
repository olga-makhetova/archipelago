package app.entity.species;

import app.entity.Predator;

public class Snake extends Predator {
    protected Snake() {
        super();
        if (getGender()) {
            specieName = "Змея";
        } else {
            specieName = "Змей";
        }
    }
}
