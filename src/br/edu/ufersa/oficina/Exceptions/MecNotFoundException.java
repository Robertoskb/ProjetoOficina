package br.edu.ufersa.oficina.Exceptions;

public class MecNotFoundException extends RuntimeException {
    public MecNotFoundException(String message) {
        super("MecNotFoundException: " + message);
    }
}
