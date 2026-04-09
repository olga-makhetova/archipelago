package app.entity.species;

import app.entity.Herbivorous;

public class Deer extends Herbivorous {
    protected Deer() {
        super();
        if (getGender()) {
            specieName = "Олениха";
        } else {
            specieName = "Олень";
        }
    }
}
