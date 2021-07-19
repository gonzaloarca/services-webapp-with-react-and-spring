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

    private static final String FULL_CONTRACT = "(SELECT * FROM contract NATURAL JOIN job_package NATURAL JOIN (SELECT user_id AS professional_id, post_id FROM job_post) AS post NATURAL JOIN (SELECT user_id AS client_id FROM users) AS client NATURAL JOIN (SELECT user_id AS professional_id FROM users) AS professional)";

    private static final String RELATED_CARDS_QUERY = new StringBuilder()
            .append("FROM job_cards NATURAL JOIN (")
            .append("    SELECT DISTINCT pro2_contracts.post_id, ")
            .append("COUNT(DISTINCT pro2_contracts.client_id) AS clients_in_common")
            .append("    FROM ")
            .append(FULL_CONTRACT).append(" pro1_contracts")
            .append("             JOIN ")
            .append(FULL_CONTRACT).append(" pro2_contracts")
            .append(" ON pro1_contracts.client_id = pro2_contracts.client_id")
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
        return executePageQuery(page, nativeQuery);
    }

    @Override
    public List<JobCard> findByUserId(long id, int page) {
        Query nativeQuery = em.createNativeQuery(
                "SELECT post_id FROM job_post WHERE post_is_active = TRUE AND user_id = :id")
                .setParameter("id", id);
        return executePageQuery(page, nativeQuery);
    }

    @Override
    public List<JobCard> search(String query, JobPost.Zone zone, List<JobPost.JobType> similarTypes, JobCard.OrderBy orderBy, boolean withZone, int page) {
        return searchWithCategory(query, zone, null, similarTypes, orderBy, withZone, page);
    }

    @Override
    public List<JobCard> searchWithCategory(String query, JobPost.Zone zone, JobPost.JobType jobType, List<JobPost.JobType> similarTypes, JobCard.OrderBy orderBy, boolean withZone, int page) {
        StringBuilder sqlQuery = new StringBuilder().append("SELECT post_id FROM job_cards");

        Query nativeQuery = buildSearchQuery(query, zone, jobType, similarTypes, sqlQuery, orderBy, withZone, false);

        return executePageQuery(page, nativeQuery);
    }

    @Override
    public Optional<JobCard> findByPostId(long id) {
        return em.createQuery("FROM JobCard AS card WHERE card.jobPost.id = :id", JobCard.class)
                .setParameter("id", id).getResultList().stream().findFirst();
    }

    @Override
    public Optional<JobCard> findByPackageIdWithPackageInfoWithInactive(long id) {
        return em.createQuery("SELECT new JobCard (jcard.jobPost, jpack.rateType, CAST(COALESCE(jpack.price,0) AS double),jcard.contractsCompleted, jcard.reviewsCount,jcard.rating, jcard.postImageId) FROM JobCard jcard JOIN JobPackage jpack ON jcard.jobPost.id = jpack.jobPost.id WHERE jpack.id = :id ", JobCard.class)
                .setParameter("id", id).getResultList().stream().findFirst();
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
    public int findByUserIdMaxPage(long id) {
        Long size = em.createQuery("SELECT COUNT(*) FROM JobPost jpost WHERE jpost.user.id = :id AND jpost.isActive = TRUE", Long.class)
                .setParameter("id", id).getSingleResult();
        return (int) Math.ceil((double) size.intValue() / HirenetUtils.PAGE_SIZE);
    }

    @Override
    public int searchMaxPage(String query, JobPost.Zone zone, List<JobPost.JobType> similarTypes, boolean withZone) {
        return searchWithCategoryMaxPage(query, zone, null, similarTypes, withZone);
    }

    @Override
    public int searchWithCategoryMaxPage(String query, JobPost.Zone zone, JobPost.JobType jobType, List<JobPost.JobType> similarTypes, boolean withZone) {
        StringBuilder sqlQuery = new StringBuilder().append("SELECT count(*) FROM job_cards");

        Query nativeQuery = buildSearchQuery(query, zone, jobType, similarTypes, sqlQuery, JobCard.OrderBy.BETTER_QUALIFIED, withZone, true); // no importa el orden

        @SuppressWarnings("unchecked")
        BigInteger result = (BigInteger) nativeQuery.getResultList().stream().findFirst().orElse(BigInteger.valueOf(0));
        return (int) Math.ceil((double) result.intValue() / HirenetUtils.PAGE_SIZE);
    }

    @Override
    public int findRelatedJobCardsMaxPage(long professional_id) {
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
                .setParameter("filteredIds", (filteredIds.isEmpty()) ? null : filteredIds)
                .getResultList().stream().sorted(
                        //Ordenamos los elementos segun el orden de filteredIds
                        Comparator.comparingInt(o -> filteredIds.indexOf(o.getJobPost().getId()))
                ).collect(Collectors.toList());
    }

    private Query buildSearchQuery(String query, JobPost.Zone zone, JobPost.JobType jobType, List<JobPost.JobType> similarTypes, StringBuilder sqlQuery, JobCard.OrderBy orderBy, boolean withZone, boolean maxPage) {
        sqlQuery.append(" WHERE ( UPPER(post_title) LIKE :query ");
        if (!similarTypes.isEmpty()) {
            String types = similarTypes.stream().map(type -> String.valueOf(type.ordinal()))
                    .collect(Collectors.joining(","));
            sqlQuery.append(" OR post_job_type in (")
                    .append(types)
                    .append(")");
        }

        sqlQuery.append(") AND post_is_active = TRUE ");

        if (withZone)
            sqlQuery.append(" AND :zone IN (SELECT zone_id FROM post_zone WHERE job_cards.post_id = post_zone.post_id) ");

        if (jobType != null)
            sqlQuery.append(" AND post_job_type = :type");

        if (!maxPage) {
            sqlQuery.append(" GROUP BY job_cards.rating, job_cards.post_id, job_cards.post_contract_count, job_cards.post_creation_date");

            switch (orderBy) {
                case BETTER_QUALIFIED:
                    sqlQuery.append(" ORDER BY job_cards.rating DESC");
                    break;
                case WORST_QUEALIFIED:
                    sqlQuery.append(" ORDER BY job_cards.rating ASC");
                    break;
                case MOST_HIRED:
                    sqlQuery.append(" ORDER BY job_cards.post_contract_count DESC");
                    break;
                case LEAST_HIRED:
                    sqlQuery.append(" ORDER BY job_cards.post_contract_count ASC");
                    break;
                case NEWEST:
                    sqlQuery.append(" ORDER BY job_cards.post_creation_date DESC");
                    break;
                case OLDEST:
                    sqlQuery.append(" ORDER BY job_cards.post_creation_date ASC");
                    break;
                default:
            }
        }

        Query nativeQuery = em.createNativeQuery(sqlQuery.toString()).setParameter("query",
                String.format("%%%s%%", query.replace("%", "\\%")
                        .replace("_", "\\_")).toUpperCase()
                );

        if (jobType != null)
            nativeQuery.setParameter("type", jobType.getValue());
        if (withZone)
            nativeQuery.setParameter("zone", zone.getValue());

        return nativeQuery;
    }
}
