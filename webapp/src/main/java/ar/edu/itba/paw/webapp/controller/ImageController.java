package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.JobContractService;
import ar.edu.itba.paw.interfaces.services.JobPostImageService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.ByteImage;
import ar.edu.itba.paw.models.JobPostImage;
import ar.edu.itba.paw.models.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.models.exceptions.JobPostImageNotFoundException;
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

@Controller
@RequestMapping("/image")
public class ImageController {

    //TODO: logging en este controller?
    private final Logger profileControllerLogger = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    UserService userService;

    @Autowired
    JobPostImageService jobPostImageService;

    @Autowired
    JobContractService jobContractService;

    @RequestMapping("/user/{id}")
    public void getProfileImage(@PathVariable("id") final long id, HttpServletRequest request, HttpServletResponse response) {
        ByteImage profilePic;

        try {
            profilePic = userService.findImageByUserId(id);
        } catch (ImageNotFoundException e) {
            profilePic = null;
        }

        try {
            if(profilePic == null || profilePic.getData() == null) {
                response.sendRedirect(request.getContextPath() + "/resources/images/defaultavatar.svg");
                return;
            }

            buildResponse(profilePic, response);

        } catch (IOException e) {
            //TODO: qué hago si hay excepción?
        }
    }

    @RequestMapping("/post/{id}")
    public void getPostImage(@PathVariable("id") final long imageId, HttpServletRequest request, HttpServletResponse response) {
        JobPostImage jobPostImage;

        try {
            jobPostImage = jobPostImageService.findById(imageId);
        } catch (JobPostImageNotFoundException e) {
            jobPostImage = null;
        }

        try {
            if(jobPostImage == null || jobPostImage.getByteImage() == null || jobPostImage.getByteImage().getData() == null) {
                //Aunque devolvemos esta imagen default, los jsp se encargan de usar las imagenes predeterminadas de cada tipo de servicio
                response.sendRedirect(request.getContextPath() + "/resources/images/service-default.svg");
                return;
            }

            buildResponse(jobPostImage.getByteImage(), response);

        } catch (IOException e) {
            //TODO: qué hago si hay excepción?
        }
    }

    @RequestMapping("/contract/{id}")
    public void getContractImage(@PathVariable("id") final long id, HttpServletRequest request, HttpServletResponse response) {
        ByteImage contractImage;

        try {
            contractImage = jobContractService.findImageByContractId(id);
        } catch (ImageNotFoundException e) {
            contractImage = null;
        }

        try {
            if (contractImage == null || contractImage.getData() == null) {
                response.sendRedirect(request.getContextPath() + "/resources/images/service-default.svg");
                return;
            }

            buildResponse(contractImage, response);
        } catch (IOException e) {
            //TODO: qué hago si hay excepción?
        }
    }

    private void buildResponse(ByteImage byteImage, HttpServletResponse response) throws IOException {
        OutputStream out = response.getOutputStream();

        response.setHeader("Content-Disposition", "inline;filename=\"image\"");
        response.setContentType(byteImage.getType());
        response.setContentLength(byteImage.getData().length);

        IOUtils.copy(new ByteArrayInputStream(byteImage.getData()), out);

        out.flush();
        out.close();
    }
}
