package app.entity.species;

import app.entity.Herbivorous;

public class Buffalo extends Herbivorous {
    protected Buffalo() {
        super();
        if (getGender()) {
            specieName = "Корова";
        } else {
            specieName = "Бык";
        }
    }
}
