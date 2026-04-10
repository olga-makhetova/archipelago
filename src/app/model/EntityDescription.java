package app.model;

/**
 * Конфигурация сущности (общая для всех типов)
 */
public record EntityDescription(
        double maxAge,
        double weight,
        double foodWeight,
        int stepCount,
        double spontaneous,
        double pregnancyTime,
        double strength,
        double fertility, // С какой вероятностью самка забеременеет после встречи
        double genderProbability, // с какой вероятностью животное будет женщиной
        double hungryTick // насколько уменьшается сытость в каждый такт
) {
}
