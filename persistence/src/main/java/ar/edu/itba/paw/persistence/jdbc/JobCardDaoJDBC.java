package ar.edu.itba.paw.persistence.jdbc;

import ar.edu.itba.paw.interfaces.HirenetUtils;
import ar.edu.itba.paw.interfaces.dao.JobCardDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.persistence.utils.ImageDataConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class JobCardDaoJDBC implements JobCardDao {

    private static List<JobPost.Zone> auxiGetZones(Object[] objs) {
        List<JobPost.Zone> zones = new ArrayList<>();
        Arrays.stream(objs)
                .forEach(obj -> zones.add(JobPost.Zone.values()[(int) obj]));
        return zones;
    }

    private final static RowMapper<JobCard> JOB_CARD_ROW_MAPPER = (resultSet, i) -> new JobCard(
            new JobPost(
                    resultSet.getLong("post_id"),
                    new User(
                            resultSet.getLong("user_id"),
                            resultSet.getString("user_email"),
                            resultSet.getString("user_name"),
                            resultSet.getString("user_phone"),
                            resultSet.getBoolean("user_is_active"),
                            resultSet.getBoolean("user_is_verified"),
                            new EncodedImage(ImageDataConverter.getEncodedString(resultSet.getBytes("user_image")),
                                    resultSet.getString("user_image_type")),
                            resultSet.getTimestamp("user_creation_date").toLocalDateTime()),
                    resultSet.getString("post_title"),
                    resultSet.getString("post_available_hours"),
                    JobPost.JobType.values()[resultSet.getInt("post_job_type")],
                    auxiGetZones((Object[]) resultSet.getArray("zones").getArray()),
                    resultSet.getDouble("rating"),
                    resultSet.getBoolean("post_is_active"),
                    resultSet.getTimestamp("post_creation_date").toLocalDateTime()
            ),
            JobPackage.RateType.values()[resultSet.getInt("min_rate_type")],
            resultSet.getDouble("min_pack_price"),
            new JobPostImage(
                    resultSet.getLong("card_image_id"),
                    resultSet.getLong("post_id"),
                    new EncodedImage(ImageDataConverter.getEncodedString(resultSet.getBytes("card_image_data")),
                            resultSet.getString("card_image_type"))
            ),
            resultSet.getInt("post_contract_count"),
            resultSet.getInt("post_reviews_size")
    );

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JobCardDaoJDBC(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public List<JobCard> findAll(int page) {
        List<Object> parameters = new ArrayList<>();
        if (page != HirenetUtils.ALL_PAGES) {
            parameters.add(HirenetUtils.PAGE_SIZE);
            parameters.add(HirenetUtils.PAGE_SIZE * page);
        }
        return jdbcTemplate.query(
                "SELECT * FROM job_cards WHERE post_is_active = TRUE " +
                        (page == HirenetUtils.ALL_PAGES ? "" : "LIMIT ? OFFSET ?"),
                parameters.toArray(), JOB_CARD_ROW_MAPPER);
    }

    @Override
    public List<JobCard> findByUserId(long id, int page) {
        List<Object> parameters = new ArrayList<>(Collections.singletonList(id));
        if (page != HirenetUtils.ALL_PAGES) {
            parameters.add(HirenetUtils.PAGE_SIZE);
            parameters.add(HirenetUtils.PAGE_SIZE * page);
        }
        return jdbcTemplate.query(
                "SELECT * FROM job_cards WHERE user_id = ? AND post_is_active = TRUE " +
                        (page == HirenetUtils.ALL_PAGES ? "" : "LIMIT ? OFFSET ?"),
                parameters.toArray(), JOB_CARD_ROW_MAPPER);
    }

    @Override
    public List<JobCard> search(String query, JobPost.Zone zone, List<JobPost.JobType> similarTypes, int page) {
        List<Object> parameters = new ArrayList<>(Arrays.asList("%" + query + "%", zone.ordinal()));

        StringBuilder sqlQuery = new StringBuilder().append("SELECT * FROM job_cards WHERE (UPPER(post_title) LIKE UPPER(?)");

        if (!similarTypes.isEmpty()) {
            String types = similarTypes.stream().map(type -> String.valueOf(type.ordinal())).collect(Collectors.joining(","));
            sqlQuery.append(" OR post_job_type in (")
                    .append(types)
                    .append(")");
        }

        sqlQuery.append(") AND ? IN(SELECT UNNEST(zones) FROM job_cards jc WHERE jc.post_id = job_cards.post_id) AND post_is_active = TRUE");

        addPaging(sqlQuery, page);

        return jdbcTemplate.query(sqlQuery.toString(), parameters.toArray(), JOB_CARD_ROW_MAPPER);
    }

    @Override
    public List<JobCard> searchWithCategory(String query, JobPost.Zone zone, JobPost.JobType jobType, List<JobPost.JobType> similarTypes, int page) {
        List<Object> parameters = new ArrayList<>(Arrays.asList("%" + query + "%", zone.ordinal(), jobType.ordinal()));

        StringBuilder sqlQuery = new StringBuilder().append("SELECT * FROM job_cards WHERE (UPPER(post_title) LIKE UPPER(?)");

        if (!similarTypes.isEmpty()) {
            String types = similarTypes.stream().map(type -> String.valueOf(type.ordinal())).collect(Collectors.joining(","));
            sqlQuery.append(" OR post_job_type in (")
                    .append(types)
                    .append(")");
        }

        sqlQuery.append(") AND ? IN(SELECT UNNEST(zones) FROM job_cards jc WHERE jc.post_id = job_cards.post_id) AND post_job_type = ? AND post_is_active = TRUE");

        addPaging(sqlQuery, page);

        return jdbcTemplate.query(sqlQuery.toString(), parameters.toArray(), JOB_CARD_ROW_MAPPER);
    }

    @Override
    public Optional<JobCard> findByPostId(long id) {
        return jdbcTemplate.query(
                "SELECT * FROM job_cards WHERE post_id = ? AND post_is_active = TRUE",
                new Object[]{id}, JOB_CARD_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public List<JobCard> findRelatedJobCards(long professional_id, int page) {
        List<Object> parameters = new ArrayList<>(Arrays.asList(professional_id,
                LocalDateTime.now().minusDays(30), professional_id));
        if (page != HirenetUtils.ALL_PAGES) {
            parameters.add(HirenetUtils.PAGE_SIZE);
            parameters.add(HirenetUtils.PAGE_SIZE * page);
        }
        return jdbcTemplate.query(
                "SELECT * " +
                        "FROM job_cards" +
                        "         NATURAL JOIN (" +
                        "    SELECT DISTINCT pro2_contracts.post_id, " +
                        "COUNT(DISTINCT pro2_contracts.client_id) AS clients_in_common" +
                        "    FROM full_contract pro1_contracts" +
                        "             JOIN full_contract pro2_contracts ON pro1_contracts.client_id = pro2_contracts.client_id" +
                        "    WHERE ? <> pro2_contracts.professional_id" +
                        "      AND pro2_contracts.contract_creation_date >= ?" +
                        "    GROUP BY pro2_contracts.post_id) AS recommended_posts " +
                        "WHERE post_is_active = TRUE" +
                        "  AND post_job_type IN (" +
                        "    SELECT DISTINCT post_job_type" +
                        "    FROM job_post" +
                        "    WHERE ? = user_id) " +
                        "ORDER BY clients_in_common DESC, contracts DESC " +
                        (page == HirenetUtils.ALL_PAGES ? "" : "LIMIT ? OFFSET ?"),
                // Obtiene las cards de otros profesionales con el que comparte clientes(los cuales contrataron al otro
                // profesional en los ultimos 30 dias) y tipo de trabajo,
                // ordenados descendientemente por clientes en comun y luego por cantidad de contratos.
                parameters.toArray(), JOB_CARD_ROW_MAPPER);
    }

    @Override
    public int findAllMaxPage() {
        Integer totalJobsCount = jdbcTemplate.queryForObject("SELECT COUNT(post_id) FROM job_cards WHERE post_is_active = TRUE", Integer.class);
        return (int) Math.ceil((double) totalJobsCount / HirenetUtils.PAGE_SIZE);
    }

    @Override
    public int findMaxPageByUserId(long id) {
        Integer totalJobsCount = jdbcTemplate.queryForObject("SELECT COUNT(post_id) FROM job_cards WHERE user_id = ? AND post_is_active = TRUE",
                new Object[]{id}, Integer.class);
        return (int) Math.ceil((double) totalJobsCount / HirenetUtils.PAGE_SIZE);
    }

    @Override
    public int findMaxPageSearch(String query, JobPost.Zone zone) {
        Integer totalJobsCount = jdbcTemplate.queryForObject("SELECT COUNT(post_id) FROM job_cards " +
                        "WHERE UPPER(post_title) LIKE UPPER(?) AND ? IN(SELECT UNNEST(zones) FROM job_cards jc WHERE jc.post_id = job_cards.post_id) AND post_is_active = TRUE",
                new Object[]{"%" + query + "%", zone.ordinal()}, Integer.class);
        return (int) Math.ceil((double) totalJobsCount / HirenetUtils.PAGE_SIZE);
    }

    @Override
    public int findMaxPageSearchWithCategory(String query, JobPost.Zone zone, JobPost.JobType jobType) {
        Integer totalJobsCount = jdbcTemplate.queryForObject("SELECT COUNT(post_id) FROM job_cards " +
                        "WHERE UPPER(post_title) LIKE UPPER(?) AND ? IN(SELECT UNNEST(zones) FROM job_cards jc WHERE jc.post_id = job_cards.post_id) AND post_is_active = TRUE AND ? = post_job_type",
                new Object[]{"%" + query + "%", zone.ordinal(), jobType.ordinal()}, Integer.class);
        return (int) Math.ceil((double) totalJobsCount / HirenetUtils.PAGE_SIZE);
    }


    @Override
    public int findMaxPageRelatedJobCards(long professional_id) {
        Integer totalJobsCount = jdbcTemplate.queryForObject("SELECT COUNT(post_id) " +
                        "FROM job_cards" +
                        "         NATURAL JOIN (" +
                        "    SELECT DISTINCT pro2_contracts.post_id, " +
                        "COUNT(DISTINCT pro2_contracts.client_id) AS clients_in_common" +
                        "    FROM full_contract pro1_contracts" +
                        "             JOIN full_contract pro2_contracts ON pro1_contracts.client_id = pro2_contracts.client_id" +
                        "    WHERE ? <> pro2_contracts.professional_id" +
                        "      AND pro2_contracts.contract_creation_date >= now()::date-30" +
                        "    GROUP BY pro2_contracts.post_id) AS recommended_posts " +
                        "WHERE post_is_active = TRUE" +
                        "  AND post_job_type IN (" +
                        "    SELECT DISTINCT post_job_type" +
                        "    FROM job_post" +
                        "    WHERE ? = user_id) "
                , new Object[]{professional_id, professional_id}, Integer.class);
        return (int) Math.ceil((double) totalJobsCount / HirenetUtils.PAGE_SIZE);
    }

    private void addPaging(StringBuilder sqlQuery, int page) {
        if (page != HirenetUtils.ALL_PAGES) {
            sqlQuery.append(" LIMIT ")
                    .append(HirenetUtils.PAGE_SIZE)
                    .append(" OFFSET ")
                    .append(HirenetUtils.PAGE_SIZE * page);
        }
    }
}
