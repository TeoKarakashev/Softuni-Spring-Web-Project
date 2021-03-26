package com.softuni.service;

import com.softuni.model.service.BatServiceModel;
import com.softuni.model.view.BatViewModel;

import java.util.List;

public interface BatService {
    
    List<BatViewModel> findAllBats();

    void initBats();

    BatViewModel findById(String id);

    List<BatViewModel> findByBrand(String brandName);


    void buy(String id, String name);

    boolean batExists(String name);

    void save(BatServiceModel map);
}
