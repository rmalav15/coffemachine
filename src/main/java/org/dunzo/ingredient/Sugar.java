package org.dunzo.ingredient;

import lombok.Getter;

@Getter
public class Sugar extends  Ingredient {

    private static final IngredientMeasure ingredientMeasure = IngredientMeasure.CUBE;
    private SugarType type;

    Sugar(SugarType type) {
        super(ingredientMeasure);
        this.type = type;
    }
}
