package br.com.unicuritiba.receitafederalcnpj.infrastructure.configs;

import br.com.unicuritiba.receitafederalcnpj.domain.repositories.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class ReceitaFederalCnpjDataInitializer implements CommandLineRunner {

    @Autowired
    private DataRepository repository;

    private final DataSource dataSource;

    public ReceitaFederalCnpjDataInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            var keyOptional = repository.findByCnpj("32548723317829");

            if(keyOptional.isEmpty()){
                ScriptUtils.executeSqlScript(connection, new ClassPathResource("sql/data.sql"));
                System.out.println("Arquivo data.sql executado com sucesso. Os dados foram inseridos no banco de dados.");
            }
        }
    }
}
