package com.vipdsilva.app.ws.service.impl;

import java.util.Optional;

import com.vipdsilva.app.ws.entities.Colors;
import com.vipdsilva.app.ws.exceptions.AlreadyExistsColorException;
import com.vipdsilva.app.ws.exceptions.NotFoundColorException;
import com.vipdsilva.app.ws.model.request.ColorRequestModel;
import com.vipdsilva.app.ws.model.request.UpdateColorRequestModel;
import com.vipdsilva.app.ws.model.response.ColorsDtoResponseModel;
import com.vipdsilva.app.ws.repository.ColorsRepository;
import com.vipdsilva.app.ws.service.ColorService;

import org.springframework.stereotype.Service;

@Service
public class ColorServiceImpl implements ColorService{

    @Override
    public ColorsDtoResponseModel createColor(ColorRequestModel colorInfo,
     ColorsRepository colorsRepository) {

        String colorName = colorInfo.getName();

        if(!hasColor(colorName, colorsRepository)){
            Colors color = new Colors(colorName);
            colorsRepository.save(color);
            ColorsDtoResponseModel response = color.toResponseDto();
            return response;
        };
    
        throw new AlreadyExistsColorException("nome");
    }

    @Override
    public ColorsDtoResponseModel updateColor(Integer colorId, UpdateColorRequestModel userDetails,
            ColorsRepository colorsRepository) {
        
        Optional<Colors> colorToUpdate = colorsRepository.findById(colorId);

        if(colorToUpdate.isPresent()){

            if(hasColor(userDetails.getName(), colorsRepository)){
                throw new AlreadyExistsColorException("nome");
            }

            colorToUpdate.get().setName(userDetails.getName().trim());
            return colorsRepository.findById(colorId).get().toResponseDto();

        } else {
            throw new NotFoundColorException(colorId);
        }

    }

    @Override
    public void deleteColor(Integer colorId, ColorsRepository colorsRepository) {
        Optional<Colors> existColor = colorsRepository.findById(colorId);

        if(existColor.isPresent()){
            colorsRepository.deleteById(colorId);
        } else {
            throw new NotFoundColorException(colorId);
        }
        
    }

    public boolean hasColor (String colorName, ColorsRepository colorsRepository) {
        return colorsRepository.findByName(colorName) != null;
    }
    
}
