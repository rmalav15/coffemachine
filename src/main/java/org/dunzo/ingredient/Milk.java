package org.dunzo.ingredient;

public class Milk extends Ingredient {
    private static final IngredientMeasure ingredientMeasure = IngredientMeasure.ML;

    Milk(IngredientMeasure ingredientMeasure) {
        super(ingredientMeasure);
    }
}
