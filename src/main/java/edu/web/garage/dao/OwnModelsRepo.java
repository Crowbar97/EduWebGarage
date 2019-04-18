package edu.web.garage.dao;

import edu.web.garage.entities.OwnModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OwnModelsRepo extends CrudRepository<OwnModel, Long> {
    boolean existsByModel_Mark_NameAndModel_Name(String markName, String name);
    List<OwnModel> findByModel_Mark_NameAndModel_Name(String markName, String name);
}
