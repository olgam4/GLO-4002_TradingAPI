package ca.ulaval.glo4002.trading.application;

public class UnableResolveServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UnableResolveServiceException(Class<?> contract) {
        super("Unable to resolve a service for '" + contract.getCanonicalName() + "'.");
    }

}