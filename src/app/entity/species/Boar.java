package app.entity.species;

import app.entity.Herbivorous;

public class Boar extends Herbivorous {
    protected Boar() {
        super();
        if (getGender()) {
            specieName = "Кабаниха";
        } else {
            specieName = "Кабан";
        }
    }

}
