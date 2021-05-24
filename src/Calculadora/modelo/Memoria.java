package Calculadora.modelo;

import java.util.ArrayList;
import java.util.List;

public class Memoria {

    private static final Memoria instancia = new Memoria();

    private TipoComando ultimaOperacao = null;
    private boolean substituir = false;
    private String textoAtual = "";
    private String textoBuffer = "";


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

        if(tipoCOmando == null){
            return;
        } else if(tipoCOmando == TipoComando.ZERAR) {
            textoAtual = "";
            textoBuffer = "";
            substituir = false;
            ultimaOperacao = null;
        } else if(tipoCOmando == TipoComando.NUMERO || tipoCOmando == TipoComando.VIRGULA) {
            textoAtual = substituir ? texto : textoAtual + texto;
            substituir = false;
        } else {
            substituir= true;
            textoAtual = obterResultadoOperacao();
            textoBuffer = textoAtual;
            ultimaOperacao = tipoCOmando;
        }


        observadores.forEach(o -> o.valorAlterado(getTextoAtual()));

    }

    private String obterResultadoOperacao() {
        if(ultimaOperacao == null || ultimaOperacao == TipoComando.IGUA){
            return textoAtual;
        }

        double numeroBuffer = Double.parseDouble(textoBuffer.replace(",","."));
        double numeroAtual = Double.parseDouble(textoAtual.replace(",","."));

        double resultado = 0;
        if(ultimaOperacao == TipoComando.SOMA){
            resultado = numeroBuffer + numeroAtual;
        } else if(ultimaOperacao == TipoComando.SUB){
            resultado = numeroBuffer - numeroAtual;
        } else if(ultimaOperacao == TipoComando.MULT){
            resultado = numeroBuffer * numeroAtual;
        } else if(ultimaOperacao == TipoComando.DIV){
            resultado = numeroBuffer / numeroAtual;
        }

        String resultadoString = Double.toString(resultado).replace(".",",");
        boolean inteiro = resultadoString.endsWith(",0");

        return inteiro ? resultadoString.replace(",0","") : resultadoString;
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
            } else if(",".equalsIgnoreCase(texto) && textoAtual.contains(",")){
                return TipoComando.VIRGULA;
            }
        }
        return null;
    }


}
