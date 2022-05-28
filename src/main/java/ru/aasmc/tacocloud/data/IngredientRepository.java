package ru.aasmc.tacocloud.data;

import org.springframework.data.repository.CrudRepository;
import ru.aasmc.tacocloud.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {

}
