package ar.edu.itba.paw.webapp.dto;

import java.net.URI;

public class LinkDto {
    private URI uri;

    public LinkDto(){}

    public LinkDto(URI uri){
        this.uri = uri;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }
}
