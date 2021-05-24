package Calculadora.modelo;

import java.util.ArrayList;
import java.util.List;

public class Memoria {

    private static final Memoria instancia = new Memoria();

    private String textoAtual = "";

    private enum TipoComando {
        ZERAR,NUMERO,DIV , MULT, SUB,SOMA,IGUA,VIRGULA};

    private final List<MemoriaObservador> observadores = new ArrayList<>();

    private Memoria() {
    }

    public String getTextoAtual() {
        return textoAtual.isEmpty() ? "0" : textoAtual;
    }

    public static Memoria getInstancia() {
        return instancia;
    }
    public void adicionarObservador(MemoriaObservador o){
        observadores.add(o);
    }

    public void processarComando(String texto){

        TipoComando tipoCOmando = detectarTipoComando(texto);

        if("AC".equalsIgnoreCase(texto)){
            textoAtual ="";
        } else {

            textoAtual += texto;
        }
        observadores.forEach(o -> o.valorAlterado(getTextoAtual()));
    }

    private TipoComando detectarTipoComando(String texto) {

        if(textoAtual.isEmpty() && texto == "0"){
            return null;
        }
        try{
            Integer.parseInt(texto);
            return TipoComando.NUMERO;
        }catch (Exception e){
            if("AC".equalsIgnoreCase(texto)){
                return TipoComando.ZERAR;
            } else if("/".equalsIgnoreCase(texto)){
                return TipoComando.DIV;
            } else if("*".equalsIgnoreCase(texto)){
                return TipoComando.MULT;
            } else if("+".equalsIgnoreCase(texto)){
                return TipoComando.SOMA;
            } else if("-".equalsIgnoreCase(texto)){
                return TipoComando.SUB;
            } else if("=".equalsIgnoreCase(texto)){
                return TipoComando.IGUA;
            } else if(",".equalsIgnoreCase(texto)){
                return TipoComando.VIRGULA;
            }
        }
        return null;
    }


}
