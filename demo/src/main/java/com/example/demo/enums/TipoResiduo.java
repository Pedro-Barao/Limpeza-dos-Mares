package com.example.demo.enums;

public enum TipoResiduo {

    SOLIDO,
    LIQUIDO,
    PASTOSO;

    /** Converte texto (sem diferenciar maiúsculas/minúsculas) para o enum. */
    public static TipoResiduo deTexto(String valor)
    {

        try {

            return TipoResiduo.valueOf(valor.trim().toUpperCase());

        } catch (IllegalArgumentException e) {

            throw new IllegalArgumentException("Tipo de resíduo inválido: " + valor + ". Valores aceitos: SOLIDO, LIQUIDO, PASTOSO");

        }

    }

}
