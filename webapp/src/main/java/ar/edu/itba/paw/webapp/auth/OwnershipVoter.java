package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.services.JobContractService;
import ar.edu.itba.paw.interfaces.services.JobPostService;
import ar.edu.itba.paw.interfaces.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.NoSuchElementException;

@Component
public class OwnershipVoter implements AccessDecisionVoter {

    @Autowired
    private JobContractService jobContractService;

    @Autowired
    private JobPostService jobPostService;

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }


    @Override
    public int vote(Authentication authentication, Object o, Collection collection) {
        System.out.println(o);
        if(o instanceof FilterInvocation){
            FilterInvocation filterInvocation = (FilterInvocation) o;
            String url = filterInvocation.getRequestUrl();

            if(url.equals("/"))
                return ACCESS_ABSTAIN;

            String[] paths = url.substring(1).split("/");
            int id;
            switch (paths[0]){
                case "qualify-contract":
                    if(paths.length == 1)
                        return ACCESS_ABSTAIN;
                    try {
                        id = Integer.parseInt(paths[1]);
                    }catch (NumberFormatException e){
                        return ACCESS_ABSTAIN;
                    }
                    if(jobContractService.findById(id).getClient().getEmail().equals(authentication.getName()))
                        return ACCESS_GRANTED;
                    break;
                case "job":
                    if(paths.length == 1)
                        return ACCESS_ABSTAIN;
                    try {
                        id = Integer.parseInt(paths[1]);
                    }catch (NumberFormatException e){
                        return ACCESS_ABSTAIN;
                    }
                    boolean isOwner;
                    try{
                        isOwner = jobPostService.findById(id).getUser().getEmail().equals(authentication.getName());
                    }catch (NoSuchElementException e){
                        isOwner= false;
                    }
                    if(paths.length > 2 && paths[2].equals("edit")){
                        if(isOwner)
                            return ACCESS_GRANTED;
                        else
                            return ACCESS_DENIED;
                    }
            }
//
        }
        return ACCESS_ABSTAIN;
    }

    @Override
    public boolean supports(Class aClass) {
        return true;
    }
}
