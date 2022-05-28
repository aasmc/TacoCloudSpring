package ru.aasmc.tacocloud.data;

import org.springframework.data.repository.CrudRepository;
import ru.aasmc.tacocloud.TacoOrder;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

}
