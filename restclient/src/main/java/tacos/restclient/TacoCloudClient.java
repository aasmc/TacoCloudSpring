package tacos.restclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tacos.Ingredient;
import tacos.Taco;

import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TacoCloudClient {
    private RestTemplate rest;
    private Traverson traverson;

    public TacoCloudClient(RestTemplate rest, Traverson traverson) {
        this.rest = rest;
        this.traverson = traverson;
    }

    public Ingredient getIngredientById(String ingredientId) {
        return rest.getForObject("http://localhost:8080/ingredients/{id}",
                Ingredient.class, ingredientId);
    }

    public Ingredient getIngredientByIdUsingMap(String ingredientId) {
        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("id", ingredientId);
        return rest.getForObject("http://localhost:8080/ingredients/{id}",
                Ingredient.class, urlVariables);
    }

    public Ingredient getIngredientByIdUsingURI(String ingredientId) {
        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("id", ingredientId);
        URI uri = UriComponentsBuilder
                .fromHttpUrl("http://localhost:8080/ingredients/{id}")
                .build(urlVariables);
        return rest.getForObject(uri, Ingredient.class);
    }

    public Ingredient getIngredientByIdForEntity(String ingredientId) {
        ResponseEntity<Ingredient> entity = rest.getForEntity("http://localhost:8080/ingredients/{id}",
                Ingredient.class, ingredientId);
        log.info("Fetched time: {}", entity.getHeaders().getDate());
        return entity.getBody();
    }

    public List<Ingredient> getAllIngredients() {
        return rest.exchange("http://localhost:8080/ingredients/",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Ingredient>>() {
                        })
                .getBody();
    }

    public void updateIngredient(Ingredient ingredient) {
        rest.put("http://localhost:8080/ingredients/{id}",
                ingredient, ingredient.getId());
    }

    public Ingredient createIngredient(Ingredient ingredient) {
        return rest.postForObject("http://localhost:8080/ingredients",
                ingredient, Ingredient.class);
    }

    public URI createIngredientForLocation(Ingredient ingredient) {
        return rest.postForLocation("http://localhost:8080/ingredients",
                ingredient);
    }

    public Ingredient createIngredientResponseEntity(Ingredient ingredient) {
        ResponseEntity<Ingredient> entity = rest.postForEntity("http://localhost:8080/ingredients",
                ingredient, Ingredient.class);
        log.info("new resource created at {}",
                entity.getHeaders().getLocation());
        return entity.getBody();
    }

    public void deleteIngredient(Ingredient ingredient) {
        rest.delete("http://localhost:8080/ingredients/{id}",
                ingredient.getId());
    }

    public Iterable<Ingredient> getAllIngredientsWithTraverson() {
        ParameterizedTypeReference<CollectionModel<Ingredient>> ingredientType =
                new ParameterizedTypeReference<CollectionModel<Ingredient>>() {
                };

        CollectionModel<Ingredient> ingredientsRes = traverson
                .follow("ingredients")
                .toObject(ingredientType);

        Collection<Ingredient> content = ingredientsRes.getContent();
        return content;
    }

    public Ingredient addIngredient(Ingredient ingredient) {
        String ingredientsUrl = traverson
                .follow("ingredients")
                .asLink()
                .getHref();

        return rest.postForObject(ingredientsUrl, ingredient, Ingredient.class);
    }

    public Iterable<Taco> getRecentTacosWithTraverson() {
        ParameterizedTypeReference<CollectionModel<Taco>> tacoType =
                new ParameterizedTypeReference<CollectionModel<Taco>>() {
                };
        CollectionModel<Taco> tacosRes = traverson
                .follow("tacos")
                .follow("recents")
                .toObject(tacoType);

        Collection<Taco> content = tacosRes.getContent();
        // Alternatively, list the two paths in the same call to follow()
            /*
            CollectionModel<Taco> tacoRes =
               traverson
                 .follow("tacos", "recents")
                 .toObject(tacoType);
            */
        return content;
    }
}
