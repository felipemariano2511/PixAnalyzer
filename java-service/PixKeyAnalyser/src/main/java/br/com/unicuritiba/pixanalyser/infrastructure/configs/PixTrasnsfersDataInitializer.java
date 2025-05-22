package br.com.unicuritiba.pixanalyser.infrastructure.configs;

import br.com.unicuritiba.pixanalyser.domain.repositories.PixTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.UUID;

@Component
public class PixTrasnsfersDataInitializer implements CommandLineRunner {

    @Autowired
    private PixTransactionRepository repository;

    private final DataSource dataSource;

    public PixTrasnsfersDataInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            var keyOptional = repository.findById(UUID.fromString("cc34f1e4-4c45-4fea-b91f-6ca2b26ee9f6"));

            if(keyOptional.isEmpty()){
                ScriptUtils.executeSqlScript(connection, new ClassPathResource("sql/data.sql"));
                System.out.println("Arquivo data.sql executado com sucesso. Os dados foram inseridos no banco de dados.");
            }
        }
    }
}
