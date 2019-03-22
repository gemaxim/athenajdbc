package de.angelasensio.athenajdbc;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.tomcat.jdbc.pool.PoolConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class Application implements CommandLineRunner {

	private static final Logger LOG = LoggerFactory.getLogger(Application.class);

	public static void main(String args[]) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		PoolConfiguration configuration = ((org.apache.tomcat.jdbc.pool.DataSource)dataSource).getPoolProperties();
		Properties properties = configuration.getDbProperties();
		properties.put("s3_staging_dir", "S3 BUCKET WHERE THE RESULTS WILL BE SAVED");
		properties.put("aws_credentials_provider_class","com.amazonaws.auth.profile.ProfileCredentialsProvider");
		properties.put("aws_credentials_provider_arguments","default");

		configuration.setDbProperties(properties);

		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void run(String... strings) {
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList("SELECT * FROM \"inventory-analytics-db\".\"onlineshops\" limit 10");
		list.forEach(x -> LOG.info(x.toString()));
		System.exit(0);
	}
}