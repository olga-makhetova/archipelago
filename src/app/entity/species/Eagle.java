package app.entity.species;

import app.entity.Predator;

public class Eagle extends Predator {
    protected Eagle() {
        super();
        if (getGender()) {
            specieName = "Орлица";
        } else {
            specieName = "Орёл";
        }
    }
}
