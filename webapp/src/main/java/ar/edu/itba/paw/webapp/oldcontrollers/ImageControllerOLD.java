package ar.edu.itba.paw.webapp.oldcontrollers;

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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Component
//@Path("/")
public class ImageControllerOLD {

    @Autowired
    UserService userService;

    @Autowired
    JobPostImageService jobPostImageService;

    @Autowired
    JobContractService jobContractService;

//    @RequestMapping("/user/{id}")
//    public void getProfileImage(@PathVariable("id") final long id, HttpServletRequest request, HttpServletResponse response) {
//        ByteImage profilePic;
//
//        try {
//            profilePic = userService.findImageByUserId(id);
//        } catch (ImageNotFoundException e) {
//            profilePic = null;
//        }
//
//        try {
//            if(profilePic == null || profilePic.getData() == null) {
//                response.sendRedirect(request.getContextPath() + "/resources/images/defaultavatar.svg");
//                return;
//            }
//
//            buildResponse(profilePic, response);
//
//        } catch (IOException e) {
//            //No hago nada
//        }
//    }



//    @RequestMapping("/contract/{id}")
//    public void getContractImage(@PathVariable("id") final long id, HttpServletRequest request, HttpServletResponse response) {
//        ByteImage contractImage;
//
//        try {
//            contractImage = jobContractService.findImageByContractId(id);
//        } catch (ImageNotFoundException e) {
//            contractImage = null;
//        }
//
//        try {
//            if (contractImage == null || contractImage.getData() == null) {
//                response.sendRedirect(request.getContextPath() + "/resources/images/service-default.svg");
//                return;
//            }
//
//            buildResponse(contractImage, response);
//        } catch (IOException e) {
//            //No hago nada
//        }
//    }


}
