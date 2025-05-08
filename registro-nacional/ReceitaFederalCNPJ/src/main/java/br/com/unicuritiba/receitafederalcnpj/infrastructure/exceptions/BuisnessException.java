package br.com.unicuritiba.receitafederalcnpj.infrastructure.exceptions;

public class BuisnessException  extends RuntimeException{

    public BuisnessException(String message) {
        super(message);
    }

    public BuisnessException(String message, Throwable cause) {

        super(message, cause);
    }
}
