package ar.edu.itba.paw.webapp.utils;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public class PageResponseUtil {

    public static Response getGenericListResponse(int page, int maxPage,
                                                  UriInfo uriInfo, Response.ResponseBuilder builder) {
        if (maxPage > 1) {
            if (page != 1) {
                builder.link(uriInfo.getAbsolutePathBuilder().queryParam("page", 1).build(), "first");
                if (page > 1 && page <= maxPage)
                    builder.link(uriInfo.getAbsolutePathBuilder().queryParam("page", page - 1).build(), "prev");
            }

            if (page != maxPage) {
                builder.link(uriInfo.getAbsolutePathBuilder().queryParam("page", maxPage).build(), "last");
                if (page >= 1 && page < maxPage)
                    builder.link(uriInfo.getAbsolutePathBuilder().queryParam("page", page + 1).build(), "next");
            }
        } else if (maxPage > 0) {
            builder.link(uriInfo.getAbsolutePathBuilder().queryParam("page", maxPage).build(), "last");
            builder.link(uriInfo.getAbsolutePathBuilder().queryParam("page", 1).build(), "first");
        }

        return builder.build();
    }

    private PageResponseUtil() {
        throw new UnsupportedOperationException();
    }
}
