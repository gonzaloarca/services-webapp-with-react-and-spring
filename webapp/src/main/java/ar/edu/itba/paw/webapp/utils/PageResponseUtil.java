package ar.edu.itba.paw.webapp.utils;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public class PageResponseUtil {

    public static Response getGenericListResponse(@DefaultValue("1") @QueryParam("page") int page, int maxPage,
                                                  UriInfo uriInfo, Response.ResponseBuilder builder) {
        return builder
                .link(uriInfo.getAbsolutePathBuilder().queryParam("page", 1).build(), "first")
                .link(uriInfo.getAbsolutePathBuilder().queryParam("page", maxPage).build(), "last")
                .link(uriInfo.getAbsolutePathBuilder().queryParam("page", (page == 1) ? page : page - 1).build(), "prev")
                .link(uriInfo.getAbsolutePathBuilder().queryParam("page", (page == maxPage) ? page : page + 1).build(), "next")
                .build();
    }

    private PageResponseUtil() {
        throw new UnsupportedOperationException();
    }
}
