package app.entity.species;

import app.entity.Herbivorous;

public class Horse extends Herbivorous {
    protected Horse() {
        super();
        if (getGender()) {
            specieName = "Кобыла";
        } else {
            specieName = "Конь";
        }
    }
}
