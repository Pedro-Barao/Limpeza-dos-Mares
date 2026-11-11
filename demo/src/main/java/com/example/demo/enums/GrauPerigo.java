package com.example.demo.enums;

public enum GrauPerigo {

    BAIXO,
    MEDIO,
    ALTO;

    /** Converte texto (sem diferenciar maiúsculas/minúsculas) para o enum. */
    public static GrauPerigo deTexto(String valor)
    {

        try {

            return GrauPerigo.valueOf(valor.trim().toUpperCase());

        } catch (IllegalArgumentException e) {

            throw new IllegalArgumentException("Grau de perigo inválido: " + valor + ". Valores aceitos: BAIXO, MEDIO, ALTO");

        }

    }

}
