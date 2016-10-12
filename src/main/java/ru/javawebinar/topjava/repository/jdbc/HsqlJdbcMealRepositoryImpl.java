package ru.javawebinar.topjava.repository.jdbc;


import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.Profiles;

import javax.sql.DataSource;

@Profile(Profiles.TEST_DB)
@Repository
public class HsqlJdbcMealRepositoryImpl extends JdbcMealRepositoryImpl {

    public HsqlJdbcMealRepositoryImpl(DataSource dataSource) {
        super(dataSource);
    }
}
