package br.com.unicuritiba.keysfrontapi.infrastructure.configs;

import br.com.unicuritiba.keysfrontapi.repositories.KeysFrontRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.UUID;

@Component
public class KeysFrontDataInitializer implements CommandLineRunner{

    @Autowired
    private KeysFrontRepository repository;

    private final DataSource dataSource;

    public KeysFrontDataInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            var keyOptional = repository.findById(UUID.fromString("0ed4dcc4-3085-4242-a144-1cb4dc168951"));

            if(keyOptional.isEmpty()){
                ScriptUtils.executeSqlScript(connection, new ClassPathResource("sql/data.sql"));
                System.out.println("Arquivo data.sql executado com sucesso. Os dados foram inseridos no banco de dados.");
            }
        }
    }
}
