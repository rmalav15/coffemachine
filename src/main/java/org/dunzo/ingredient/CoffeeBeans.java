package org.dunzo.ingredient;

import lombok.Getter;

@Getter
public  class CoffeeBeans extends Ingredient {

    private static final IngredientMeasure ingredientMeasure = IngredientMeasure.SACHET;
    private CoffeeBrand brand;

    CoffeeBeans(CoffeeBrand brand) {
        super(ingredientMeasure);
        this.brand = brand;
    }
}
