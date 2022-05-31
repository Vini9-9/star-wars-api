package com.vipdsilva.app.ws.service;

import com.vipdsilva.app.ws.model.request.ColorRequestModel;
import com.vipdsilva.app.ws.model.request.UpdateColorRequestModel;
import com.vipdsilva.app.ws.model.response.ColorsDtoResponseModel;
import com.vipdsilva.app.ws.repository.ColorsRepository;

public interface ColorService {

	ColorsDtoResponseModel createColor(ColorRequestModel colorInfo,
	 ColorsRepository colorsRepository);

	ColorsDtoResponseModel updateColor(Integer colorId, UpdateColorRequestModel userDetails,
	ColorsRepository colorsRepository);
	
	void deleteColor(Integer colorId, ColorsRepository colorsRepository);
}
