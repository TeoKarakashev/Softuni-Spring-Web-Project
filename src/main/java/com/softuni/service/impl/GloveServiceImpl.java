package com.softuni.service.impl;

import com.softuni.error.BrandNotFoundException;
import com.softuni.error.GloveNotFoundException;
import com.softuni.error.UserNotFoundException;
import com.softuni.model.entity.BrandEntity;
import com.softuni.model.entity.GloveEntity;
import com.softuni.model.entity.UserEntity;
import com.softuni.model.service.GloveServiceModel;
import com.softuni.model.service.ImportGloveRootService;
import com.softuni.model.service.ImportGloveService;
import com.softuni.model.view.GloveViewModel;
import com.softuni.repository.BrandRepository;
import com.softuni.repository.GloveRepository;
import com.softuni.repository.UserRepository;
import com.softuni.service.BrandService;
import com.softuni.service.GloveService;
import com.softuni.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GloveServiceImpl implements GloveService {

    private static final String GLOVES_PATH = "src/main/resources/init/gloves.xml";
    private final GloveRepository gloveRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final BrandService brandService;
    private final BrandRepository brandRepository;
    private final UserRepository userRepository;

    @Autowired
    public GloveServiceImpl(GloveRepository gloveRepository, ModelMapper modelMapper, XmlParser xmlParser, BrandService brandService, BrandRepository brandRepository, UserRepository userRepository) {
        this.gloveRepository = gloveRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.brandService = brandService;
        this.brandRepository = brandRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<GloveViewModel> findAllGloves() {
        return this.gloveRepository.findAll().stream()
                .map(gloveEntity -> {
                    return this.modelMapper.map(gloveEntity, GloveViewModel.class);
                }).collect(Collectors.toList());
    }

    @Override
    public void initGloves() throws JAXBException {
        if(this.gloveRepository.count() == 0 ){
            ImportGloveRootService importGloveRootService = this.xmlParser.parseXml(ImportGloveRootService.class, GLOVES_PATH);

            for (ImportGloveService glove : importGloveRootService.getGloves()) {
                GloveEntity gloveEntity = this.modelMapper.map(glove, GloveEntity.class);
                gloveEntity.setBrand(this.modelMapper.map(this.brandService.findByName(glove.getBrand()), BrandEntity.class));
                this.gloveRepository.save(gloveEntity);
            }

        }
    }

    @Override
    public GloveViewModel findById(String id) {

        return this.modelMapper.map(this.gloveRepository.findById(id).orElseThrow(() -> new GloveNotFoundException("Glove not found!")), GloveViewModel.class);
    }

    @Override
    public GloveEntity getOne() {
        return this.gloveRepository.getALlGloves().get(0);
    }

    @Override
    public List<GloveViewModel> findByBrand(String brandName) {
        BrandEntity brand = this.brandRepository.findByName(brandName).orElseThrow(() -> new BrandNotFoundException("brand not found"));
        List<GloveViewModel> gloves = this.gloveRepository.findByBrand(brand).stream()
                .map(gloveEntity -> this.modelMapper.map(gloveEntity, GloveViewModel.class))
                .collect(Collectors.toList());
        return gloves;

    }

    @Override
    public void buy(String id, String username) {
        UserEntity user = this.userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("no user found"));
        GloveEntity glove = this.gloveRepository.findById(id).orElseThrow(() -> new GloveNotFoundException("no glove found"));
        user.setGlove(glove);
        this.userRepository.save(user);
        glove.setQuantity(glove.getQuantity() - 1);
            this.gloveRepository.save(glove);

    }

    @Override
    public boolean gloveExists(String name) {
        return this.gloveRepository.findByName(name).isPresent();
    }

    @Override
    public void save(GloveServiceModel gloveServiceModel) {
        GloveEntity gloveEntity = this.modelMapper.map(gloveServiceModel, GloveEntity.class);
        gloveEntity.setQuantity(10);
        gloveEntity.setBrand(this.brandRepository.findByName(gloveServiceModel.getBrand())
                .orElseThrow(() -> new BrandNotFoundException("Brand not found")));
        this.gloveRepository.save(gloveEntity);

    }
}
