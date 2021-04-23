package ar.edu.itba.paw.webapp.validation;

import ar.edu.itba.paw.interfaces.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImageFileValidator implements ConstraintValidator<ValidImage, MultipartFile> {

    @Autowired
    private ImageService imageService;

    @Override
    public void initialize(ValidImage constraintAnnotation) {

    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
        if(multipartFile == null)
            return false;
        String contentType = multipartFile.getContentType();
        if (multipartFile.getSize() != 0 && !imageService.isValidType(contentType)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Only JPG and PNG are supported")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

}
