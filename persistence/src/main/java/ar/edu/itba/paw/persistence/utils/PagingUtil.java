package ar.edu.itba.paw.persistence.utils;

import ar.edu.itba.paw.interfaces.HirenetUtils;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PagingUtil {
    public static List<Long> getFilteredIds(int page, Query nativeQuery) {
        if (page >= 0) {
            nativeQuery.setFirstResult((page) * HirenetUtils.PAGE_SIZE);
            nativeQuery.setMaxResults(HirenetUtils.PAGE_SIZE);
        }

        @SuppressWarnings("unchecked")
        List<Long> filteredIds = (List<Long>) nativeQuery.getResultList().stream()
                .map(e -> Long.valueOf(e.toString())).collect(Collectors.toList());

        if (filteredIds.isEmpty())
            return new ArrayList<>();
        else return filteredIds;
    }

    private PagingUtil() {
        throw new UnsupportedOperationException();
    }
}
