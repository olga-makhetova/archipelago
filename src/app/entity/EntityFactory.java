package app.entity;

import app.AppConfig;
import app.entity.species.Plant;
import app.service.IconLoader;
import app.service.Island;
import app.service.NameGenerator;

import java.lang.reflect.Constructor;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Фабрика сущностей<p>
 * Основная логика по созданию здесь, чтобы была в одном месте
 */

public class EntityFactory {

    /**
     * Пусть все животные немного друг от друга отличаются
     */
    private static double getGaussianRandom() {
        return 1 + ThreadLocalRandom.current().nextGaussian() * AppConfig.VARIANCE;
    }

    /**
     * Создаёт список сущностей, можно регулировать, каким образом
     * Пока реализовано только, что хотя бы одно растение будет
     */
    public static void spawnEntityList(Island island) {
        int countEntity = (int) (island.getRows() * island.getCols() * AppConfig.INIT_DENSITY);
        for (int i = 0; i < countEntity - 1; i++) {
            spawnEntity(island);
        }
        spawnEntity(island, Plant.class);
    }


    /**
     * Создаёт сущность случайного класса
     */
    public static Entity spawnEntity(Island island) {
        return spawnEntity(island, getRandomEntityClass());
    }

    /**
     * Создаёт сущность заданного класса, задаёт начальные значения
     */
    public static Entity spawnEntity(Island island, Class<? extends Entity> entityClass) {
        if (!island.canReceive()) return null; // защита от перенаселения

        // Для сущности
        Entity entity = createEntity(entityClass);
        entity.setIsland(island);
        entity.setImageIcon(IconLoader.getIcon(entity.getClass()));

        // Для живущих
        if (entity instanceof Living living) {
            living.setName(NameGenerator.getName());
            living.setTimeTick((int) (getGaussianRandom() * EntityProperties.getTimeTick(living.getClass())));
            living.setMaxAge(getGaussianRandom() * EntityProperties.getMaxAge(living.getClass()));
            living.setWeight(getGaussianRandom() * EntityProperties.getWeight(living.getClass()));

            // для животных
            if (living instanceof Animal animal) {
                animal.setSpontaneous(getGaussianRandom() * EntityProperties.getSpontaneous(animal.getClass()));
                animal.setStrength(getGaussianRandom() * EntityProperties.getStrength(animal.getClass()));
                animal.setName(NameGenerator.getName(animal.getGender()));
            }

            // Задаём задание
            ScheduledFuture future = island.pool.scheduleAtFixedRate(living, AppConfig.INIT_DELAY, living.getTimeTick(), TimeUnit.MILLISECONDS);
            living.setFuture(future);
        }

        // Помещаем сущность на остров
        island.receiveEntity(entity);
        return entity;
    }

    /**
     * Создаёт экземпляр указанного класса
     *
     * @param clazz элемент перечисления, определяющий тип создаваемой сущности
     * @return созданная сущность
     * @throws RuntimeException если создание экземпляра через рефлексию завершилось неудачей
     */
    private static Entity createEntity(Class<? extends Entity> clazz, Object... args) {
        try {
            // рефлексия
            Constructor<? extends Entity> constructor = clazz.getDeclaredConstructor(toParamTypes(args));
            constructor.setAccessible(true); // работаем с protected конструктором
            return constructor.newInstance(args);
        } catch (Exception e) {
            throw new RuntimeException("Не удалось создать сущность класса + " + clazz.getName(), e);
        }
    }

    /**
     * Извлекает массив типов из переданных аргументов.
     * Используется для поиска конструктора через рефлексию.
     * <p>
     * Примитивные типы не поддерживаются
     *
     * @param args аргументы, типы которых необходимо извлечь
     * @return массив {@link Class}, соответствующий типам переданных аргументов
     */
    private static Class<?>[] toParamTypes(Object... args) {
        Class<?>[] paramTypes = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            paramTypes[i] = args[i].getClass();
        }
        return paramTypes;
    }

    /**
     * Возвращает случайный класс, зарегистрированный в {@link EntityProperties}
     * Выбор потокобезопасен и равновероятен по всем значениям.
     */
    public static Class<? extends Entity> getRandomEntityClass() {
        // используем потокобезопасный рандом
        int randomIndex = ThreadLocalRandom.current().nextInt(EntityProperties.ENTITY_CLASSES.size());
        return EntityProperties.ENTITY_CLASSES.get(randomIndex);
    }
}
