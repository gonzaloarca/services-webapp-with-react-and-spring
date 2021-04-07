package ar.edu.itba.paw.webapp.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

//TODO: cambiar esto por un mejor metodo
@Component
public class ImageValidator implements Validator {

    // TODO: definir los tipos de imagen aceptados:
    // http://www.java2s.com/Code/Java/Network-Protocol/MapfileextensionstoMIMEtypesBasedontheApachemimetypesfile.htm
    private static final List<String> contentTypes = Arrays.asList("image/png", "image/jpeg");

    @Override
    public boolean supports(Class<?> aClass) {
        return MultipartFile.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        MultipartFile file = (MultipartFile) o;

        if (file.getSize() == 0) {
            //errors.rejectValue("image", "image.upload.empty");
            return;
        }

        if (!contentTypes.contains(file.getContentType())) {
            errors.rejectValue("image", "image.incorrect.format");
        }
    }
}
