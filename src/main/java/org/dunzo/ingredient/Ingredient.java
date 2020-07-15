package org.dunzo.ingredient;

import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class Ingredient {
    private IngredientMeasure ingredientMeasure;

    Ingredient(IngredientMeasure ingredientMeasure) {
        this.ingredientMeasure = ingredientMeasure;
    }
}
