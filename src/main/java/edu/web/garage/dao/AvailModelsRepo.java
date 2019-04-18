package edu.web.garage.dao;

import edu.web.garage.entities.Model;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AvailModelsRepo extends CrudRepository<Model, Long> {
    boolean existsByMark_NameAndName(String markName, String name);
    List<Model> findByMark_NameAndName(String markName, String name);
}
