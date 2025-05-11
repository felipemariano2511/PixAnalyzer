package br.com.unicuritiba.dictapi.infrastructure.configs;

import br.com.unicuritiba.dictapi.domain.repositories.PixKeyRepository;
import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class DictApiDataInitializer implements CommandLineRunner {

    @Autowired
    private PixKeyRepository repository;

    private final DataSource dataSource;

    public DictApiDataInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            var keyOptional = repository.findByKey("46bfe0e6-4d82-43a0-9292-1039de1ad12c");

            if(keyOptional.isEmpty()){
                ScriptUtils.executeSqlScript(connection, new ClassPathResource("sql/data.sql"));
                System.out.println("Arquivo data.sql executado com sucesso. Os dados foram inseridos no banco de dados.");
            }
        }
    }
}
