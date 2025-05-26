package br.com.unicuritiba.pixanalyzer.infrastructure.repositories.inmemory;

import br.com.unicuritiba.pixanalyzer.domain.repositories.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.Map;

@Repository
public class InMemoryClientRepository implements ClientRepository {

    private final Map<Long, ClientInfo> clients = Map.ofEntries(
            Map.entry(1L, new ClientInfo("Felipe Mariano", new BigDecimal("120000.00"), "BRADESCO S.A.")),
            Map.entry(2L, new ClientInfo("Ramon Tardetti", new BigDecimal("25056.51"), "BRADESCO S.A.")),
            Map.entry(3L, new ClientInfo("Matheus Henrique", new BigDecimal("11745.20"), "BRADESCO S.A.")),
            Map.entry(4L, new ClientInfo("Vinícius Stencel", new BigDecimal("7455.27"), "BRADESCO S.A.")),
            Map.entry(5L, new ClientInfo("Diego Palma Navarro", new BigDecimal("18200.00"), "BRADESCO S.A.")),
            Map.entry(6L, new ClientInfo("Lucas Andrade", new BigDecimal("9330.60"), "BRADESCO S.A.")),
            Map.entry(7L, new ClientInfo("Ana Beatriz Costa", new BigDecimal("18500.00"), "BRADESCO S.A.")),
            Map.entry(8L, new ClientInfo("João Pedro Lima", new BigDecimal("24200.75"), "BRADESCO S.A.")),
            Map.entry(9L, new ClientInfo("Bruna Martins", new BigDecimal("6550.00"), "BRADESCO S.A.")),
            Map.entry(10L, new ClientInfo("Carlos Eduardo", new BigDecimal("9100.00"), "BRADESCO S.A.")),
            Map.entry(11L, new ClientInfo("Fernanda Rocha", new BigDecimal("12300.20"), "BRADESCO S.A.")),
            Map.entry(12L, new ClientInfo("Gustavo Rezende", new BigDecimal("13150.80"), "BRADESCO S.A.")),
            Map.entry(13L, new ClientInfo("Isabela Torres", new BigDecimal("14670.45"), "BRADESCO S.A.")),
            Map.entry(14L, new ClientInfo("Thiago Pires", new BigDecimal("16890.00"), "BRADESCO S.A.")),
            Map.entry(15L, new ClientInfo("Juliana Almeida", new BigDecimal("11890.30"), "BRADESCO S.A.")),
            Map.entry(16L, new ClientInfo("Roberto Silva", new BigDecimal("9500.00"), "BRADESCO S.A.")),
            Map.entry(17L, new ClientInfo("Camila Santos", new BigDecimal("10400.70"), "BRADESCO S.A.")),
            Map.entry(18L, new ClientInfo("Pedro Henrique", new BigDecimal("20450.00"), "BRADESCO S.A.")),
            Map.entry(19L, new ClientInfo("Larissa Cunha", new BigDecimal("8790.00"), "BRADESCO S.A.")),
            Map.entry(20L, new ClientInfo("Mariana Lopes", new BigDecimal("13300.00"), "BRADESCO S.A."))
    );

    @Override
    public String getNameById(Long id) {
        return clients.get(id).name;
    }

    @Override
    public BigDecimal getBalanceById(Long id) {
        return clients.get(id).balance;
    }

    @Override
    public String getOriginBankById(Long id) { return clients.get(id).originBank; }

    @AllArgsConstructor
    private static class ClientInfo {
        private final String name;
        private final BigDecimal balance;
        private final String originBank;
    }
}
