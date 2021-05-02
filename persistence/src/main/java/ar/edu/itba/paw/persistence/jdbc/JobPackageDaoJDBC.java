package ar.edu.itba.paw.persistence.jdbc;

import ar.edu.itba.paw.interfaces.HirenetUtils;
import ar.edu.itba.paw.interfaces.dao.JobPackageDao;
import ar.edu.itba.paw.models.JobPackage;
import ar.edu.itba.paw.models.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class JobPackageDaoJDBC implements JobPackageDao {

    private static Integer getLimit(int page){
        return page == HirenetUtils.ALL_PAGES ? null : HirenetUtils.PAGE_SIZE;
    }

    private final static RowMapper<JobPackage> JOB_PACKAGE_ROW_MAPPER = (resultSet, rowNum) -> new JobPackage(
            resultSet.getLong("package_id"),
            resultSet.getLong("post_id"),
            resultSet.getString("package_title"),
            resultSet.getString("package_description"),
            resultSet.getDouble("package_price"),
            JobPackage.RateType.values()[resultSet.getInt("package_rate_type")],
            resultSet.getBoolean("package_is_active"));

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public JobPackageDaoJDBC(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds).withTableName("job_package").usingGeneratedKeyColumns("package_id");
    }

    @Override
    public JobPackage create(long postId, String title, String description, Double price, JobPackage.RateType rateType) {
        Number key = jdbcInsert.executeAndReturnKey(new HashMap<String,Object>(){{
            put("post_id",postId);
            put("package_title",title);
            put("package_description",description);
            put("package_price",price);
            put("package_rate_type",rateType.ordinal());
            put("package_is_active",true);
        }});

        return new JobPackage(key.longValue(),postId,title,description,price,rateType,true);
    }

    @Override
    public Optional<JobPackage> findById(long id) {
        return jdbcTemplate.query("SELECT * FROM job_package WHERE package_id = ? AND package_is_active = TRUE",new Object[]{id},JOB_PACKAGE_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public List<JobPackage> findByPostId(long id,int page) {
        Integer limit =getLimit(page);
        int offset = page == HirenetUtils.ALL_PAGES ? 0 : HirenetUtils.PAGE_SIZE * page;
        return jdbcTemplate.query("SELECT * FROM job_package WHERE post_id = ? AND package_is_active = TRUE ORDER BY package_id",new Object[]{id},JOB_PACKAGE_ROW_MAPPER);
    }

    @Override
    public boolean deletePackage(long id){
        return jdbcTemplate.update("UPDATE job_package SET package_is_active = FALSE WHERE package_id=?",id) == 1;
    }

    @Override
    public boolean updatePackage(long id, String title, String description, Double price, JobPackage.RateType rateType){
        return jdbcTemplate.update("UPDATE job_package SET package_title = ?, package_description = ?, package_price = ?, package_rate_type = ? WHERE package_id = ?",
                title,description,price,rateType.ordinal(),id) == 1;
    }

}
