package com.usa.palcosapp.service;

import com.usa.palcosapp.model.Box;
import com.usa.palcosapp.repository.BoxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoxService {

    @Autowired
    BoxRepository boxRepository;

    public List<Box> getAll() {
        return boxRepository.getAll();
    }

    public Optional<Box> getById(Integer id) {
        return boxRepository.getById(id);
    }

    public Box save(Box box) {
        Optional<Box> optional;
        if (box.getId() == null) {
            return boxRepository.save(box);
        } else {
            optional = boxRepository.getById(box.getId());
            if (optional.isEmpty()) {
                return boxRepository.save(box);
            } else {
                return box;
            }
        }
    }

    public Box update(Box box) {
        if (box.getId() != null) {
            Optional<Box> optional = boxRepository.getById(box.getId());
            if (!optional.isEmpty()) {
                if (box.getName() != null) {
                    optional.get().setName(box.getName());
                }
                if (box.getCapacity() != null) {
                    optional.get().setCapacity(box.getCapacity());
                }
                if (box.getLocation() != null) {
                    optional.get().setLocation(box.getLocation());
                }
                if (box.getDescription() != null) {
                    optional.get().setDescription(box.getDescription());
                }
                if (box.getCategory() != null){
                    optional.get().setCategory(box.getCategory());
                }
                boxRepository.save(optional.get());
                return optional.get();
            } else {
                return box;
            }
        } else {
            return box;
        }
    }

    public boolean delete(Integer id) {
        Boolean aBoolean = getById(id).map(box ->{
            boxRepository.delete(box);
            return true;
        }).orElse(false);
        return aBoolean;
    }
}

