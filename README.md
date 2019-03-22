# athenajdbc
Spring Boot 2 Project with Athena JDBC access using the JDBC Template. It uses the latest Athena JDBC Driver (for Java 8): AthenaJDBC42. 

## Configuration

- Edit application.yml and set the AWS region

spring:
    datasource:       
        url: jdbc:awsathena://AwsRegion=[REGION] 

- Edit Application.java and set the S3 Bucket where your query results should be saved

properties.put("s3_staging_dir", "S3 BUCKET WHERE THE RESULTS WILL BE SAVED"); // Line 33

and change the SQL statement:
this.jdbcTemplate.queryForList("SELECT STATEMENT"); // Line 44

The AWS credentials are supposed to be configured on your system.
