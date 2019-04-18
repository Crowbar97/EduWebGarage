package edu.web.garage.controllers;

import edu.web.garage.dao.AvailModelsRepo;
import edu.web.garage.dao.OwnModelsRepo;
import edu.web.garage.dto.ResponseDto;
import edu.web.garage.entities.Model;
import edu.web.garage.entities.OwnModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("v1/cars")
public class GarageController {
    @Autowired
    private AvailModelsRepo availModelsRepo;
    @Autowired
    private OwnModelsRepo ownModelsRepo;

    // help
    @GetMapping(value = "help")
    public Map<String, String> getHelp() {
        Map<String, String> help = new HashMap<>();
        help.put("help", "views this help");
        help.put("show", "shows own cars");
        help.put("add", "adds new car");
        help.put("remove", "removes specified car");
        help.put("exit", "closes your garage");
        return help;
    }
    @GetMapping(value = "show_avail")
    public Iterable<Model> getAvailModels() {
        return availModelsRepo.findAll();
    }
    @GetMapping(value = "show_own")
    public Iterable<OwnModel> getOwnModels() {
        return ownModelsRepo.findAll();
    }

    // actions
    @PutMapping(value = "add")
    public ResponseDto addModel(@RequestParam(name = "mark") String markName, @RequestParam(name = "model") String modelName) {
        if (!availModelsRepo.existsByMark_NameAndName(markName, modelName))
            return new ResponseDto("error", "Car with mark '" + markName
                                                            + "' and model '" + modelName
                                                            + "' does not available!");

        if (ownModelsRepo.existsByModel_Mark_NameAndModel_Name(markName, modelName)) {
            OwnModel ownModel = ownModelsRepo.findByModel_Mark_NameAndModel_Name(markName, modelName).get(0);
            ownModel.incCount();
            ownModelsRepo.save(ownModel);
            return new ResponseDto("notice", "Car with mark '" + markName
                                                            + "' and model '" + modelName
                                                            + "' count was updated up to " + ownModel.getCount()
                                                            + "!");
        }

        Model model = availModelsRepo.findByMark_NameAndName(markName, modelName).get(0);
        OwnModel ownModel = new OwnModel(model, 1);
        ownModelsRepo.save(ownModel);
        return new ResponseDto("notice", "Car with mark '" + markName
                                                            + "' and model '" + modelName
                                                            + "' was added to your garage!");
    }
    @DeleteMapping(value = "remove")
    public ResponseDto removeModel(@RequestParam(name = "mark") String markName, @RequestParam(name = "model") String modelName) {
        if (ownModelsRepo.existsByModel_Mark_NameAndModel_Name(markName, modelName)) {
            OwnModel ownModel = ownModelsRepo.findByModel_Mark_NameAndModel_Name(markName, modelName).get(0);
            if (ownModel.getCount() > 1) {
                ownModel.decCount();
                ownModelsRepo.save(ownModel);
                return new ResponseDto("notice", "Car with mark '" + markName
                                                                + "' and model '" + modelName
                                                                + "' count was updated down to " + ownModel.getCount()
                                                                + "!");
            }
            ownModelsRepo.delete(ownModel);
            return new ResponseDto("notice", "Car with mark '" + markName
                                                            + "' and model '" + modelName
                                                            + "' was removed from your garage!");
        }

        return new ResponseDto("error", "Car with mark '" + markName
                                                        + "' and model '" + modelName
                                                        + "' does not exist in your garage!");
    }
}
