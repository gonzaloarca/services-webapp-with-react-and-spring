package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.services.JobContractService;
import ar.edu.itba.paw.interfaces.services.JobPackageService;
import ar.edu.itba.paw.interfaces.services.JobPostService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.JobContract;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;

@Component
public class OwnershipVoter implements AccessDecisionVoter<FilterInvocation> {

    @Autowired
    private JobContractService jobContractService;

    @Autowired
    private JobPostService jobPostService;

    @Autowired
    private JobPackageService jobPackageService;

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }


    @Override
    public int vote(Authentication authentication, FilterInvocation filterInvocation, Collection collection) {
        if (filterInvocation != null) {
            HttpServletRequest request = filterInvocation.getHttpRequest();
            String[] query = {};
            if (request.getQueryString() != null)
                query = request.getQueryString().split("&");
            if(request.getPathInfo() == null || request.getPathInfo().equals("/"))
                return ACCESS_ABSTAIN;
            String[] paths = request.getPathInfo().substring(1).split("/");
            Map<String, String> queryParams = new HashMap<>();
            for (String q :
                    query) {
                String[] splitted = q.split("=");
                if(splitted.length < 2)
                    queryParams.put(splitted[0], null);
                else
                        queryParams.put(splitted[0], splitted[1]);
            }
            User user;
            JobContract contract;
            JobPost jobPost;
            if (paths.length == 0)
                return ACCESS_ABSTAIN;
            try {
                switch (paths[0]) {
                    case "reviews":
                        if(queryParams.get("postId") != null)
                            return ACCESS_ABSTAIN;
                    case "contracts":
                        long contractId;
                        if (queryParams.get("userId") != null && queryParams.get("role") != null && queryParams.get("state") != null) {
                            user = userService.findById(Integer.parseInt(queryParams.get("userId")));
                            if (authentication == null)
                                return ACCESS_ABSTAIN;
                            if (user.getEmail().equals(authentication.getName())) return ACCESS_GRANTED;
                            else return ACCESS_DENIED;
                        }
                        if (paths.length == 2) {
                            contractId = Integer.parseInt(paths[1]);
                            contract = jobContractService.findByIdWithUser(contractId);
                            if (authentication == null)
                                return ACCESS_ABSTAIN;
                            if (contract.getClient().getEmail().equals(authentication.getName())) {
                                return ACCESS_GRANTED;
                            } else if (contract.getProfessional().getEmail().equals(authentication.getName()))
                                return ACCESS_GRANTED;
                            else
                                return ACCESS_DENIED;
                        } else if(paths.length > 2) {
                            if(paths[paths.length-1].equals("image"))
                                return ACCESS_ABSTAIN;
                            contractId = Integer.parseInt(paths[1]);
                            contract = jobContractService.findByIdWithUser(contractId);
                            if (authentication == null)
                                return ACCESS_ABSTAIN;
                            if (contract.getClient().getEmail().equals(authentication.getName()))
                                return ACCESS_GRANTED;
                            else if (contract.getProfessional().getEmail().equals(authentication.getName()) && request.getMethod().equalsIgnoreCase("GET"))
                                return ACCESS_GRANTED;
                            else return ACCESS_DENIED;
                        }
                    case "job-posts":
                        if (paths.length > 1) {
                            user = jobPostService.findUserByPostId(Integer.parseInt(paths[1]));

                            if (authentication != null && user.getEmail().equals(authentication.getName()) && (request.getMethod().equalsIgnoreCase("POST") || request.getMethod().equalsIgnoreCase("PUT")))
                                return ACCESS_GRANTED;
                            else if (authentication != null && !request.getMethod().equalsIgnoreCase("GET"))
                                return ACCESS_DENIED;
                            else {
                                if (paths.length > 2 && paths[2].equalsIgnoreCase("packages"))
                                    if (queryParams.get("role") != null && queryParams.get("role").equalsIgnoreCase("PROFESSIONAL")) {
                                        if (authentication == null)
                                            return ACCESS_DENIED;
                                        if (jobPostService.findUserByPostId(Integer.parseInt(paths[1])).getEmail().equals(authentication.getName()))
                                            return ACCESS_GRANTED;
                                        else return ACCESS_DENIED;
                                    }
                            }
                        } else return ACCESS_ABSTAIN;

                    case "users":
                        if (request.getMethod().equalsIgnoreCase("PUT") && paths.length > 1) {
                            user = userService.findById(Integer.parseInt(paths[1]));
                            if (authentication == null)
                                return ACCESS_ABSTAIN;
                            if (user.getEmail().equals(authentication.getName()))
                                return ACCESS_GRANTED;
                            else return ACCESS_DENIED;
                        }
                }

            } catch (NumberFormatException e) {
                return ACCESS_ABSTAIN;
            } catch (NoSuchElementException e) {
                return ACCESS_DENIED;
            }
        }
        return ACCESS_ABSTAIN;
    }

    @Override
    public boolean supports(Class aClass) {
        return true;
    }
}
