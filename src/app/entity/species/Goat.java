package app.entity.species;

import app.entity.Herbivorous;

public class Goat extends Herbivorous {
    protected Goat() {
        super();
        if (getGender()) {
            specieName = "Коза";
        } else {
            specieName = "Козёл";
        }
    }
}
