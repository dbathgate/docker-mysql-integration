import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.cj.jdbc.MysqlDataSource;

public class MySqlDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(MySqlDao.class);
    private static final String USER = "docker_it";
    private static final String PASSWORD = "123456";
    private static final String DB = "docker_it";

    private static final String GET_VALUE_SQL = "SELECT value FROM table1 WHERE id = ?";
    private static final String SET_VALUE_SQL = "INSERT into table1 (id, value) VALUES (?, ?)";
    private static final String PING_SQL = "SELECT now() FROM dual";

    private final String host;
    private final Integer port;

    public MySqlDao(String host, Integer port) {
        this.host = host;
        this.port = port;

        init();
    }

    public Optional<String> getValue(String key) {

        try (Connection connection = createConnection();
             PreparedStatement statement = connection.prepareStatement(GET_VALUE_SQL)){

            statement.setString(1, key);
            
            ResultSet result = statement.executeQuery();

            if(result.first()) {
                return Optional.ofNullable(result.getString("value"));
            }

        } catch (SQLException e) {
            LOGGER.error("Failed to get value", e);
        }

        return Optional.empty();
    }

    public int setValue(String key, String value) {

        try (Connection connection = createConnection();
             PreparedStatement statement = connection.prepareStatement(SET_VALUE_SQL)){

            statement.setString(1, key);
            statement.setString(2, value);
            
            return statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("Failed to get value", e);
        }

        return 0;
    }
    public Boolean ping() {
        try (Connection connection = createConnection();
                PreparedStatement statement = connection.prepareStatement(PING_SQL)){
            
            ResultSet result = statement.executeQuery();

            if(result.first()) {
                return true;
            }

        } catch (SQLException e) {
            LOGGER.error("Failed to ping database", e);
        }
        return false;
    }

    private void init() {
        Flyway flyway = new Flyway();
        flyway.setDataSource(getDataSource());
        flyway.setLocations("classpath:/sql");

        flyway.migrate();
    }

    private Connection createConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    private DataSource getDataSource() {
        String jdbcUrl = "jdbc:mysql://" + host + ":" + port + "/" + DB;
        LOGGER.info("JDBC URL: " + jdbcUrl);

        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl(jdbcUrl);
        dataSource.setUser(USER);
        dataSource.setPassword(PASSWORD);

        return dataSource;
    }
}
