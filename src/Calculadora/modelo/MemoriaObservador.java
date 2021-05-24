package Calculadora.modelo;

@FunctionalInterface
public interface MemoriaObservador {

    void valorAlterado(String novoValor);
}
