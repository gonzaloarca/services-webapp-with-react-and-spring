package ar.edu.itba.paw.webapp.validation;

import ar.edu.itba.paw.interfaces.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ImageListValidator implements ConstraintValidator<ValidImageList, List<MultipartFile>> {

	@Autowired
	private ImageService imageService;

	@Override
	public void initialize(ValidImageList validImage) {

	}

	@Override
	public boolean isValid(List<MultipartFile> multipartFiles, ConstraintValidatorContext context) {
		if(multipartFiles == null)
			return false;

		if(multipartFiles.size() > 5) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(
					"Only up to 5 images is allowed")
					.addConstraintViolation();
			return false;
		}

		for(MultipartFile file : multipartFiles) {
			String contentType = file.getContentType();
			if (file.getSize() != 0 && !imageService.isValidType(contentType)) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate(
						"Only JPG and PNG are supported")
						.addConstraintViolation();
				return false;
			}
		}

		return true;
	}
}
