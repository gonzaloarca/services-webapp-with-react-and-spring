package ar.edu.itba.paw.persistence.utils;

import ar.edu.itba.paw.interfaces.HirenetUtils;

public class PagingUtil {
    public static void addPaging(StringBuilder sqlQuery, int page) {
        if (page != HirenetUtils.ALL_PAGES) {
            sqlQuery.append(" LIMIT ")
                    .append(HirenetUtils.PAGE_SIZE)
                    .append(" OFFSET ")
                    .append(HirenetUtils.PAGE_SIZE * page);
        }
    }
}
