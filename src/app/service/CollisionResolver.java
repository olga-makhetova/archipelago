package app.service;

import app.Archipelago;
import app.entity.Animal;
import app.entity.Entity;
import app.entity.Herbivorous;
import app.entity.Predator;
import app.entity.species.Caterpillar;
import app.entity.species.Duck;
import app.entity.species.Gate;
import app.entity.species.Plant;

/**
 * Разрешает коллизии
 */
public class CollisionResolver {

    /**
     * Столкновение животного с сущностью
     */
    public static void resolve(Animal animal, Entity entity) {

        // Портал телепортирует
        if (entity instanceof Gate) {
            teleport(animal);
            return;
        }

        // поедание растений
        if (entity instanceof Plant plant && animal instanceof Herbivorous herbivorous) {
            double weight = herbivorous.eat(plant.getWeight());
            plant.eaten(weight);
        }

        // два животных
        if (entity instanceof Animal animal2) {
            if (animal.getClass().equals(animal2.getClass())) { // одного вида
                makeLove(animal, animal2); // возможность размножения
            } else {
                clash(animal, animal2); // стычка
            }
        }
    }

    /**
     * Переносит на случайную точку этого или другого острова
     */
    private static void teleport(Animal animal) {
        Island newIsland = Archipelago.getRandomIsland();
        if (!newIsland.canReceive()) return;
        Island oldIsland = animal.getIsland();

        oldIsland.clear(animal);
        newIsland.receiveEntity(animal);

        String text = "✈ Животное %s перенеслось с острова %s на остров %s".formatted(
                animal.getFullName(), oldIsland.getName(), newIsland.getName());
        oldIsland.log(text);
        newIsland.log(text);
    }

    /**
     * Возможность размножения
     */
    private static void makeLove(Animal animal1, Animal animal2) {
        Island island = animal1.getIsland();
        if (animal2.getGender() != animal1.getGender() // разного пола
                && !animal1.isPregnant() && !animal2.isPregnant() //не беременные
                && island.canReceive() // остров не перенаселён
        ) {
            island.log("❤ %s и %s встретились!".formatted(animal1.getFullName(), animal2.getFullName()));
            // Спокойно! Проверка на пол внутри метода
            animal1.getPregnant();
            animal2.getPregnant();
        }
    }

    /**
     * Первое животное ест второе
     */
    private static void oneEatAnother(Animal one, Animal another) {
        if (one.eat(another.getWeight()) > 0.0) {
            another.dead("поедания");
            one.log("☠ Животное " + one.getFullName() + " съело животное " + another.getFullName());
        }
    }

    /**
     * Стычка двух животных
     */
    private static void clash(Animal animal1, Animal animal2) {
        // исключительный случай: утка ест гусеницу
        if (animal1 instanceof Caterpillar caterpillar && animal2 instanceof Duck duck) {
            oneEatAnother(duck, caterpillar);
            return;
        }
        if (animal2 instanceof Caterpillar caterpillar && animal1 instanceof Duck duck) {
            oneEatAnother(duck, caterpillar);
            return;
        }

        // остальные травоядные не нападают друг на друга
        if (animal1 instanceof Herbivorous && animal2 instanceof Herbivorous) {
            return;
        }

        // хищник нападает на травоядное
        if (animal1 instanceof Herbivorous herbivorous && animal2 instanceof Predator predator) {
            oneEatAnother(predator, herbivorous);
            return;
        }
        if (animal2 instanceof Herbivorous herbivorous && animal1 instanceof Predator predator) {
            oneEatAnother(predator, herbivorous);
            return;
        }

        // два хищника тоже могут подраться
        if (animal1 instanceof Predator predator1 && animal2 instanceof Predator predator2) {
            combat(predator1, predator2);
        }
    }

    /**
     * Драка двух хищников
     */
    private static void combat(Predator predator1, Predator predator2) {
        if (predator1.getStrength() > predator2.getStrength()) {
            oneHurtAnother(predator1, predator2);
        } else {
            oneHurtAnother(predator2, predator1);
        }
    }

    /**
     * Сильнейшее ранило более слабое
     */
    private static void oneHurtAnother(Animal strongest, Animal weakest) {
        strongest.log("⚔ Животное " + strongest.getFullName() + " напало на животное " + weakest.getFullName());
        double damage = strongest.getStrength() - weakest.getStrength();
        strongest.log("⚕ Животное %s получило урона: %.2f ".formatted(weakest.getFullName(), damage));
        weakest.getHurt(damage);

    }

}
