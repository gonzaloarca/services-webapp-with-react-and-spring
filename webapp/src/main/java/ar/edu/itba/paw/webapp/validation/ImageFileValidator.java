package ar.edu.itba.paw.webapp.validation;

import ar.edu.itba.paw.interfaces.services.ImageService;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImageFileValidator implements ConstraintValidator<ValidImage, FormDataBodyPart> {

    @Autowired
    private ImageService imageService;

    @Override
    public void initialize(ValidImage constraintAnnotation) {

    }

    @Override
    public boolean isValid(FormDataBodyPart multipartFile, ConstraintValidatorContext context) {
        if(multipartFile == null)
            return false;
        String contentType = multipartFile.getMediaType().getType() + "/" + multipartFile.getMediaType().getSubtype();
        if (multipartFile.getContentDisposition().getSize() != 0 && !imageService.isValidType(contentType)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Only JPG and PNG are supported")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

}
