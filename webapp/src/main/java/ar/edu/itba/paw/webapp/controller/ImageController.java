package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.ByteImage;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/image")
public class ImageController {

    private final Logger profileControllerLogger = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    UserService userService;

    @RequestMapping("/user/{id}")
    public void getProfileImage(@PathVariable("id") final long id, HttpServletRequest request, HttpServletResponse response){
        ByteImage profilePic;

        try {
            profilePic = userService.findById(id).getByteImage();
        } catch (NoSuchElementException e) {
            profilePic = null;
        }

        try {
            if(profilePic == null || profilePic.getData() == null) {
                //TODO: capaz cambiar redirect por una copia de esta imagen?
                response.sendRedirect(request.getContextPath() + "/resources/images/defaultavatar.svg");
                return;
            }

            OutputStream out = response.getOutputStream();

            response.setHeader("Content-Disposition", "inline;filename=\"profile\"");
            response.setContentType(profilePic.getType());
            response.setContentLength(profilePic.getData().length);

            IOUtils.copy(new ByteArrayInputStream(profilePic.getData()), out);

            out.flush();
            out.close();

        } catch (IOException e) {
            //TODO: qué hago si hay excepción?
        }
    }
}
