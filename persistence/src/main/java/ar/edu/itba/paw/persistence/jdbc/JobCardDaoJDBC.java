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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class JobCardDaoJDBC implements JobCardDao {

    private static List<JobPost.Zone> auxiGetZones(Object[] objs) {
        List<JobPost.Zone> zones = new ArrayList<>();
        Arrays.stream(objs)
                .forEach(obj -> zones.add(JobPost.Zone.values()[(int) obj]));
        return zones;
    }

    private static Integer getLimit(int page) {
        return page == HirenetUtils.ALL_PAGES ? null : HirenetUtils.PAGE_SIZE;
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
        Integer limit = getLimit(page);
        int offset = page == HirenetUtils.ALL_PAGES ? 0 : HirenetUtils.PAGE_SIZE * page;
        return jdbcTemplate.query(
                "SELECT * FROM job_cards WHERE post_is_active = TRUE LIMIT ? OFFSET ?", new Object[]{limit, offset},
                JOB_CARD_ROW_MAPPER);
    }

    @Override
    public List<JobCard> findByUserId(long id, int page) {
        Integer limit = getLimit(page);
        int offset = page == HirenetUtils.ALL_PAGES ? 0 : HirenetUtils.PAGE_SIZE * page;
        return jdbcTemplate.query(
                "SELECT * FROM job_cards WHERE user_id = ? AND post_is_active = TRUE LIMIT ? OFFSET ?",
                new Object[]{id, limit, offset}, JOB_CARD_ROW_MAPPER);
    }

    @Override
    public List<JobCard> search(String query, JobPost.Zone zone, int page) {
        Integer limit = getLimit(page);
        int offset = page == HirenetUtils.ALL_PAGES ? 0 : HirenetUtils.PAGE_SIZE * page;
        query = "%" + query + "%";
        return jdbcTemplate.query(
                "SELECT * FROM job_cards WHERE UPPER(post_title) LIKE UPPER(?) AND ? IN(SELECT UNNEST(zones) FROM job_cards jc WHERE jc.post_id = job_cards.post_id) AND post_is_active = TRUE LIMIT ? OFFSET ?",
                new Object[]{query, zone.ordinal(), limit, offset},
                JOB_CARD_ROW_MAPPER
        );
    }

    @Override
    public List<JobCard> searchWithCategory(String query, JobPost.Zone zone, JobPost.JobType jobType, int page) {
        query = "%" + query + "%";
        Integer limit = getLimit(page);
        int offset = page == HirenetUtils.ALL_PAGES ? 0 : HirenetUtils.PAGE_SIZE * page;
        return jdbcTemplate.query(
                "SELECT * FROM job_cards WHERE UPPER(post_title) LIKE UPPER(?) AND ? IN(SELECT UNNEST(zones) FROM job_cards jc WHERE jc.post_id = job_cards.post_id) AND post_job_type = ? AND post_is_active = TRUE LIMIT ? OFFSET ?",
                new Object[]{query, zone.ordinal(), jobType.ordinal(), limit, offset},
                JOB_CARD_ROW_MAPPER
        );
    }

    @Override
    public List<JobCard> findByUserIdWithReview(long id, int page) {
        Integer limit = getLimit(page);
        int offset = page == HirenetUtils.ALL_PAGES ? 0 : HirenetUtils.PAGE_SIZE * page;
        return jdbcTemplate.query(
                "SELECT * FROM job_cards WHERE user_id = ? AND post_is_active = TRUE LIMIT ? OFFSET ?",
                new Object[]{id, limit, offset}, JOB_CARD_ROW_MAPPER);
    }

    @Override
    public Optional<JobCard> findByPostId(long id) {
        return jdbcTemplate.query(
                "SELECT * FROM job_cards WHERE post_id = ? AND post_is_active = TRUE",
                new Object[]{id}, JOB_CARD_ROW_MAPPER).stream().findFirst();
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
        query = "%" + query + "%";
        Integer totalJobsCount = jdbcTemplate.queryForObject("SELECT COUNT(post_id) FROM job_cards " +
                        "WHERE UPPER(post_title) LIKE UPPER(?) AND ? IN(SELECT UNNEST(zones) FROM job_cards jc WHERE jc.post_id = job_cards.post_id) AND post_is_active = TRUE",
                new Object[]{query, zone.ordinal()}, Integer.class);
        return (int) Math.ceil((double) totalJobsCount / HirenetUtils.PAGE_SIZE);
    }

    @Override
    public int findMaxPageSearchWithCategory(String query, JobPost.Zone zone, JobPost.JobType jobType) {
        query = "%" + query + "%";
        Integer totalJobsCount = jdbcTemplate.queryForObject("SELECT COUNT(post_id) FROM job_cards " +
                        "WHERE UPPER(post_title) LIKE UPPER(?) AND ? IN(SELECT UNNEST(zones) FROM job_cards jc WHERE jc.post_id = job_cards.post_id) AND post_is_active = TRUE AND ? = post_job_type",
                new Object[]{query, zone.ordinal(), jobType.ordinal()}, Integer.class);
        return (int) Math.ceil((double) totalJobsCount / HirenetUtils.PAGE_SIZE);
    }
}
