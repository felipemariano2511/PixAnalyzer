package br.com.unicuritiba.dadosgov.infrastructure.configs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class DadosGovDataInitializer implements CommandLineRunner {

    private final DataSource dataSource;

    public DadosGovDataInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("sql/data.sql"));
            System.out.println("Arquivo data.sql executado com sucesso.");
        }
    }
}
