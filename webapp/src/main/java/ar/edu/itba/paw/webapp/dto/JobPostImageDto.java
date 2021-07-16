package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.JobPostImage;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class JobPostImageDto {
    private Long id;
    private URI uri;

    public static JobPostImageDto fromJobPostImage(long postId,Long imageId, UriInfo uriInfo){
        JobPostImageDto jobPostImageDto = new JobPostImageDto();
        jobPostImageDto.id = imageId;
        jobPostImageDto.uri = uriInfo.getBaseUriBuilder().path("/job-posts").path(String.valueOf(postId)).path("/images").path(String.valueOf(jobPostImageDto.id)).build();
        return jobPostImageDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }
}
