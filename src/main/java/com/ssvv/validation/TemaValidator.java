package com.ssvv.validation;

import com.ssvv.domain.Tema;

import java.util.Objects;

public class TemaValidator implements Validator<Tema> {



    @Override
    public void validate(Tema entity) throws ValidationException {
        if(Objects.nonNull(entity.getID())) {
            if(entity.getID().equals("")) {
                throw new ValidationException("Numar tema invalid!");
            }
        } else {
            throw new ValidationException("Numar tema invalid!");
        }
        if(entity.getDescriere().equals("")){
            throw new ValidationException("Descriere invalida!");
        }
        if(entity.getDeadline() < 1 || entity.getDeadline() > 14) {
            throw new ValidationException("Deadlineul trebuie sa fie intre 1-14.");
        }
        if(entity.getPrimire() < 1 || entity.getPrimire() > 14) {
            throw new ValidationException("Saptamana primirii trebuie sa fie intre 1-14.");
        }
    }
}
