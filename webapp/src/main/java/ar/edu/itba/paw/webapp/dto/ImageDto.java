package ar.edu.itba.paw.webapp.dto;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class ImageDto {
    private URI uri;
    private Long id;

    public static ImageDto fromImageId(long id,long postId, UriInfo uriInfo){
        ImageDto imageDto = new ImageDto();
        imageDto.uri = uriInfo.getBaseUriBuilder().path("/job-posts").path(String.valueOf(postId)).path("/images").path(String.valueOf(id)).build();
        imageDto.id = id;
        return imageDto;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
