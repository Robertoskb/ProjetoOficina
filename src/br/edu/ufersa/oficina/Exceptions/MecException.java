package br.edu.ufersa.oficina.Exceptions;

public class MecException extends RuntimeException {
    public MecException(String message) {
        super("MecException: " + message);
    }
}
