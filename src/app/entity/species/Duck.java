package app.entity.species;

import app.entity.Herbivorous;

public class Duck extends Herbivorous {
    protected Duck() {
        super();
        if (getGender()) {
            specieName = "Утка";
        } else {
            specieName = "Селезень";
        }
    }
}
