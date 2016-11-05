/*
 * Copyright (c) 2016 Fábio Nogueira de Lucena
 *
 * Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.seed;

/**
 * Representa situação excepcional gerada pelas atividades
 * de serialização/desserialização.
 */
public class SeedException extends RuntimeException {

    /**
     * Cria exceção com mensagem associada.
     * @param msg
     */
    public SeedException(String msg) {
        super(msg);
    }
}