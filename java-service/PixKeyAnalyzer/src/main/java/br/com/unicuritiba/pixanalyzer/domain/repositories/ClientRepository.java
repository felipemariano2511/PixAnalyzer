package br.com.unicuritiba.pixanalyzer.domain.repositories;

import java.math.BigDecimal;

public interface ClientRepository {
    String getNameById(Long id);
    BigDecimal getBalanceById(Long id);

    String getOriginBankById(Long id);
}
