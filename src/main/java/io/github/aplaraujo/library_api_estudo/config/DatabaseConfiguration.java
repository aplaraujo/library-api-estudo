package io.github.aplaraujo.library_api_estudo.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

    // Injeção das propriedades
    @Value("${spring.datasource.url}")
    String url;

    @Value("${spring.datasource.username}")
    String username;

    @Value("${spring.datasource.password}")
    String password;

    @Value("${spring.datasource.driver-class-name}")
    String driver;

    // Implementação básica de uma base de dados fornecida pelo Spring JPA
    // NÃO deve ser usada em produção!!!!
//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource ds = new DriverManagerDataSource();
//        ds.setUrl(url);
//        ds.setUsername(username);
//        ds.setPassword(password);
//        ds.setDriverClassName(driver);
//        return ds;
//    }

    // Implementação padrão de uma base de dados (para criar um "pool" de conexões)
    // Recomendada pela equipe do Spring Boot
    @Bean
    public DataSource hikariDataSource() {
        HikariConfig config = new HikariConfig();
        // Propriedades do banco
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);

        // Configurações do "pool" de conexões
        config.setMaximumPoolSize(10); // Quantidade máxima de conexões liberadas
        config.setMinimumIdle(1); // Quantidade mínima de conexões liberadas
        config.setPoolName("library-db-pool"); // Nome do "pool" de conexões
        config.setMaxLifetime(600000); // Tempo máximo de uma conexão em milissegundos (mínimo de 30 segundos [30000 ms] e máximo de 30 minutos [1800000 ms])
        config.setConnectionTimeout(500000); // Tempo máximo para conseguir uma conexão
        config.setConnectionTestQuery("select 1"); // Consulta de teste para verificar a conexão com o banco de dados

        return new HikariDataSource(config);
    }
}
