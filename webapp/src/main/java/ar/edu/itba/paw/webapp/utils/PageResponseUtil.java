package ar.edu.itba.paw.webapp.utils;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public class PageResponseUtil {

    public static Response getGenericListResponse(int page, int maxPage,
                                                  UriInfo uriInfo, Response.ResponseBuilder builder) {
        if(maxPage > 0) {
            if (page > 1) {
                builder.link(uriInfo.getAbsolutePathBuilder().queryParam("page", 1).build(), "first")
                        .link(uriInfo.getAbsolutePathBuilder().queryParam("page", page - 1).build(), "prev");
            }
            if (page < maxPage) {
                builder.link(uriInfo.getAbsolutePathBuilder().queryParam("page", maxPage).build(), "last")
                        .link(uriInfo.getAbsolutePathBuilder().queryParam("page", page + 1).build(), "next");
            }
            if (page > maxPage) {
                builder.link(uriInfo.getAbsolutePathBuilder().queryParam("page", maxPage).build(), "last");
            }
        }
        return builder.build();
    }

    private PageResponseUtil() {
        throw new UnsupportedOperationException();
    }
}
