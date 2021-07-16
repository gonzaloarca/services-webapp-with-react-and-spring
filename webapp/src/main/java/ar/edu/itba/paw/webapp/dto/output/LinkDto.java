package ar.edu.itba.paw.webapp.dto.output;

import java.net.URI;

public class LinkDto {
    private URI uri;
    private Long id;

    public static LinkDto fromUriAndId(URI uri, long id) {
        LinkDto dto = new LinkDto();
        dto.uri = uri;
        dto.id = id;
        return dto;
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
