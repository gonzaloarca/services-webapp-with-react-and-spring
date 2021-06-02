package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.interfaces.HirenetUtils;
import ar.edu.itba.paw.interfaces.dao.JobCardDao;
import ar.edu.itba.paw.models.JobCard;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.persistence.utils.PagingUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class JobCardDaoJpa implements JobCardDao {

    private static final String RELATED_CARDS_QUERY = new StringBuilder()
            .append("FROM job_cards NATURAL JOIN (")
            .append("    SELECT DISTINCT pro2_contracts.post_id, ")
            .append("COUNT(DISTINCT pro2_contracts.client_id) AS clients_in_common")
            .append("    FROM full_contract pro1_contracts")
            .append("             JOIN full_contract pro2_contracts ON pro1_contracts.client_id = pro2_contracts.client_id")
            .append("    WHERE :proId <> pro2_contracts.professional_id AND :proId = pro1_contracts.professional_id")
            .append("      AND pro2_contracts.contract_creation_date >= :dateLimit")
            .append("    GROUP BY pro2_contracts.post_id) AS recommended_posts ")
            .append("WHERE post_is_active = TRUE ")
            .append("  AND post_job_type IN (")
            .append("    SELECT DISTINCT post_job_type")
            .append("    FROM job_post")
            .append("    WHERE :proId = user_id) ")
            .toString();
    // Obtiene las cards de otros profesionales con el que comparte clientes(los cuales contrataron al otro
    // profesional en los ultimos 30 dias) y tipo de trabajo

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<JobCard> findAll(int page) {
        Query nativeQuery = em.createNativeQuery(
                "SELECT post_id FROM job_post WHERE post_is_active = TRUE"
        );
        return executePageQuery(page,nativeQuery);
    }

    @Override
    public List<JobCard> findByUserId(long id, int page) {
        Query nativeQuery = em.createNativeQuery(
                "SELECT post_id FROM job_post WHERE post_is_active = TRUE AND user_id = :id")
                .setParameter("id", id);
        return executePageQuery(page,nativeQuery);
    }

    @Override
    public List<JobCard> search(String query, JobPost.Zone zone, List<JobPost.JobType> similarTypes, int page) {
        return searchWithCategory(query, zone, null, similarTypes, page);
    }

    @Override
    public List<JobCard> searchWithCategory(String query, JobPost.Zone zone, JobPost.JobType jobType, List<JobPost.JobType> similarTypes, int page) {
        StringBuilder sqlQuery = new StringBuilder().append("SELECT post_id FROM job_cards");

        Query nativeQuery = buildSearchQuery(query, zone, jobType, similarTypes, sqlQuery);

        return executePageQuery(page,nativeQuery);
    }

    @Override
    public Optional<JobCard> findByPostId(long id) {
        return em.createQuery("FROM JobCard AS card WHERE card.jobPost.isActive = TRUE", JobCard.class)
                .getResultList().stream().findFirst();
    }

    @Override
    public Optional<JobCard> findByPostIdWithInactive(long id) {
        return Optional.ofNullable(em.find(JobCard.class, id));
    }

    @Override
    public List<JobCard> findRelatedJobCards(long professional_id, int page) {
        StringBuilder sqlQuery = new StringBuilder()
                .append("SELECT post_id ")
                .append(RELATED_CARDS_QUERY)
                .append("ORDER BY clients_in_common DESC, post_contract_count DESC ");
        // ordenados descendientemente por clientes en comun y luego por cantidad de contratos.

        Query nativeQuery = em.createNativeQuery(sqlQuery.toString())
                .setParameter("proId", professional_id)
                .setParameter("dateLimit", LocalDateTime.now().minusDays(30));

        return executePageQuery(page, nativeQuery);
    }

    @Override
    public int findAllMaxPage() {
        Long size = em.createQuery("SELECT COUNT(*) FROM JobPost jpost WHERE jpost.isActive = TRUE", Long.class)
                .getSingleResult();
        return (int) Math.ceil((double) size.intValue() / HirenetUtils.PAGE_SIZE);
    }

    @Override
    public int findMaxPageByUserId(long id) {
        Long size = em.createQuery("SELECT COUNT(*) FROM JobPost jpost WHERE jpost.user.id = :id AND jpost.isActive = TRUE", Long.class)
                .setParameter("id", id).getSingleResult();
        return (int) Math.ceil((double) size.intValue() / HirenetUtils.PAGE_SIZE);
    }

    @Override
    public int findMaxPageSearch(String query, JobPost.Zone zone, List<JobPost.JobType> similarTypes) {
        return findMaxPageSearchWithCategory(query, zone, null, similarTypes);
    }

    @Override
    public int findMaxPageSearchWithCategory(String query, JobPost.Zone zone, JobPost.JobType jobType, List<JobPost.JobType> similarTypes) {
        StringBuilder sqlQuery = new StringBuilder().append("SELECT count(*) FROM job_cards");

        Query nativeQuery = buildSearchQuery(query, zone, jobType, similarTypes, sqlQuery);

        @SuppressWarnings("unchecked")
        BigInteger result = (BigInteger) nativeQuery.getResultList().stream().findFirst().orElse(BigInteger.valueOf(0));
        return result.intValue();
    }

    @Override
    public int findMaxPageRelatedJobCards(long professional_id) {
        StringBuilder sqlQuery = new StringBuilder()
                .append("SELECT count(*) ")
                .append(RELATED_CARDS_QUERY);

        Query nativeQuery = em.createNativeQuery(sqlQuery.toString())
                .setParameter("proId", professional_id)
                .setParameter("dateLimit", LocalDateTime.now().minusDays(30));

        @SuppressWarnings("unchecked")
        BigInteger size = (BigInteger) nativeQuery.getResultList().stream().findFirst().orElse(BigInteger.valueOf(0));
        return (int) Math.ceil((double) size.intValue() / HirenetUtils.PAGE_SIZE);
    }

    private List<JobCard> executePageQuery(int page, Query nativeQuery) {
        List<Long> filteredIds = PagingUtil.getFilteredIds(page, nativeQuery);

        return em.createQuery("FROM JobCard AS card WHERE card.jobPost.id IN :filteredIds", JobCard.class)
                .setParameter("filteredIds", (filteredIds.isEmpty())? null : filteredIds)
                .getResultList().stream().sorted(
                        //Ordenamos los elementos segun el orden de filteredIds
                        Comparator.comparingInt(o -> filteredIds.indexOf(o.getJobPost().getId()))
                ).collect(Collectors.toList());
    }

    private Query buildSearchQuery(String query, JobPost.Zone zone, JobPost.JobType jobType, List<JobPost.JobType> similarTypes, StringBuilder sqlQuery) {
        sqlQuery.append(" WHERE (UPPER(post_title) LIKE UPPER('%'|| :query ||'%')");

        if (!similarTypes.isEmpty()) {
            String types = similarTypes.stream().map(type -> String.valueOf(type.ordinal())).collect(Collectors.joining(","));
            sqlQuery.append(" OR post_job_type in (")
                    .append(types)
                    .append(")");
        }

        sqlQuery.append(") AND :zone IN (SELECT zone_id FROM post_zone WHERE job_cards.post_id = post_zone.post_id) AND post_is_active = TRUE");

        if(jobType != null)
            sqlQuery.append(" AND post_job_type = :type");

        sqlQuery.append(" GROUP BY job_cards.rating,job_cards.post_id");
        sqlQuery.append(" ORDER BY rating DESC");

        Query nativeQuery = em.createNativeQuery(sqlQuery.toString())
                .setParameter("query", query)
                .setParameter("zone", zone.getValue());

        if(jobType != null)
            nativeQuery.setParameter("type", jobType.getValue());

        return nativeQuery;
    }
}
