package ru.aasmc.tacocloud.data;

import org.springframework.data.repository.CrudRepository;
import ru.aasmc.tacocloud.Taco;

public interface TacoRepository extends CrudRepository<Taco, Long> {
}
