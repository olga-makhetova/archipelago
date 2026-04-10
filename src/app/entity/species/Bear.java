package app.entity.species;

import app.entity.Predator;

public class Bear extends Predator {
    protected Bear() {
        super();
        if (getGender()) {
            specieName = "Медведица";
        } else {
            specieName = "Медведь";
        }
    }
}
