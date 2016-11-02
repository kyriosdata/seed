/*
 * Copyright (c) 2016 Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.seed;

import java.nio.ByteBuffer;

/**
 * Encapsula um valor, que pode ser uma sequência
 */
public class Seed {

    /**
     * Empacota um byte.
     *
     * @param valor Byte a ser empacotado.
     *
     * @return Vetor de bytes contendo o byte.
     */
    public byte[] pack(byte valor) {
        return new byte[] { valor };
    }

    /**
     * Recupera {@code byte} depositado no buffer na posição indicada.
     *
     * @param buffer Vetor do qual o byte será recuperado.
     * @param offset Posição inicial do valor a ser recuperado.
     * @return Valor recuperado do buffer na posição indicada.
     */
    public byte unpackByte(byte[] buffer, int offset) {
        return buffer[offset];
    }

    /**
     * Empacota um {@code short} em vetor de bytes.
     *
     * @param valor Valor a ser empacotado.
     *
     * @return Vetor de bytes contendo o valor empacotado.
     */
    public byte[] pack(short valor) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.putShort(valor);
        return buffer.array();
    }

    /**
     * Recupera {@code short} depositado no buffer na posição indicada.
     *
     * @param buffer Vetor do qual o {@code short} será recuperado.
     * @param offset Posição inicial do valor a ser recuperado.
     * @return Valor recuperado do buffer na posição indicada.
     */
    public short unpackShort(byte[] buffer, int offset) {
        ByteBuffer wrapped = ByteBuffer.wrap(buffer);
        return wrapped.getShort(offset);
    }

    /**
     * Empacota um {@code int} em vetor de bytes.
     *
     * @param valor Valor a ser empacotado.
     *
     * @return Vetor de bytes contendo o valor empacotado.
     */
    public byte[] pack(int valor) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(valor);
        return buffer.array();
    }

    /**
     * Recupera {@code int} depositado no buffer na posição indicada.
     *
     * @param buffer Vetor do qual o {@code int} será recuperado.
     * @param offset Posição inicial do valor a ser recuperado.
     * @return Valor recuperado do buffer na posição indicada.
     */
    public int unpackInt(byte[] buffer, int offset) {
        ByteBuffer wrapped = ByteBuffer.wrap(buffer);
        return wrapped.getInt(offset);
    }

    /**
     * Empacota um {@code long} em vetor de bytes.
     *
     * @param valor Valor a ser empacotado.
     *
     * @return Vetor de bytes contendo o valor empacotado.
     */
    public byte[] pack(long valor) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(valor);
        return buffer.array();
    }

    /**
     * Empacota um {@code float} em vetor de bytes.
     *
     * @param valor Valor a ser empacotado.
     *
     * @return Vetor de bytes contendo o valor empacotado.
     */
    public byte[] pack(float valor) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putFloat(valor);
        return buffer.array();
    }

    /**
     * Empacota um {@code double} em vetor de bytes.
     *
     * @param valor Valor a ser empacotado.
     *
     * @return Vetor de bytes contendo o valor empacotado.
     */
    public byte[] pack(double valor) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putDouble(valor);
        return buffer.array();
    }

    /**
     * Empacota um {@code boolean} em vetor de bytes.
     *
     * @param valor Valor a ser empacotado.
     *
     * @return Vetor de bytes contendo o valor empacotado.
     */
    public byte[] pack(boolean valor) {
        ByteBuffer buffer = ByteBuffer.allocate(1);
        buffer.put((byte)(valor ? 1 : 0));
        return buffer.array();
    }

    /**
     * Empacota um {@code char} em vetor de bytes.
     *
     * @param valor Valor a ser empacotado.
     *
     * @return Vetor de bytes contendo o valor empacotado.
     */
    public byte[] pack(char valor) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.putChar(valor);
        return buffer.array();
    }
}
