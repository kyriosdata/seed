/*
 * Copyright (c) 2016 Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.seed;

import java.io.UnsupportedEncodingException;
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
     * Recupera {@code long} depositado no buffer na posição indicada.
     *
     * @param buffer Vetor do qual o {@code long} será recuperado.
     * @param offset Posição inicial do valor a ser recuperado.
     * @return Valor recuperado do buffer na posição indicada.
     */
    public long unpackLong(byte[] buffer, int offset) {
        ByteBuffer wrapped = ByteBuffer.wrap(buffer);
        return wrapped.getLong(offset);
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
     * Recupera {@code float} depositado no buffer na posição indicada.
     *
     * @param buffer Vetor do qual o {@code float} será recuperado.
     * @param offset Posição inicial do valor a ser recuperado.
     * @return Valor recuperado do buffer na posição indicada.
     */
    public float unpackFloat(byte[] buffer, int offset) {
        ByteBuffer wrapped = ByteBuffer.wrap(buffer);
        return wrapped.getFloat(offset);
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
     * Recupera {@code double} depositado no buffer na posição indicada.
     *
     * @param buffer Vetor do qual o {@code double} será recuperado.
     * @param offset Posição inicial do valor a ser recuperado.
     * @return Valor recuperado do buffer na posição indicada.
     */
    public double unpackDouble(byte[] buffer, int offset) {
        ByteBuffer wrapped = ByteBuffer.wrap(buffer);
        return wrapped.getDouble(offset);
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
     * Recupera {@code boolean} depositado no buffer na posição indicada.
     *
     * @param buffer Vetor do qual o {@code boolean} será recuperado.
     * @param offset Posição inicial do valor a ser recuperado.
     * @return Valor recuperado do buffer na posição indicada.
     */
    public boolean unpackBoolean(byte[] buffer, int offset) {
        ByteBuffer wrapped = ByteBuffer.wrap(buffer);
        return wrapped.get(offset) == 1;
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

    /**
     * Recupera {@code char} depositado no buffer na posição indicada.
     *
     * @param buffer Vetor do qual o {@code char} será recuperado.
     * @param offset Posição inicial do valor a ser recuperado.
     * @return Valor recuperado do buffer na posição indicada.
     */
    public char unpackChar(byte[] buffer, int offset) {
        ByteBuffer wrapped = ByteBuffer.wrap(buffer);
        return wrapped.getChar(offset);
    }

    /**
     * Empacota um {@code String} em vetor de bytes.
     *
     * @param valor Valor a ser empacotado.
     *
     * @return Vetor de bytes contendo o valor empacotado.
     */
    public byte[] pack(String valor) throws UnsupportedEncodingException {
        byte[] bytes = valor.getBytes("UTF-8");
        int tamanho = bytes.length;

        // Guarda tamanho (int) + os bytes da String propriamente dita
        ByteBuffer buffer = ByteBuffer.allocate(4 + tamanho);
        buffer.putInt(tamanho);
        buffer.put(bytes);
        return buffer.array();
    }

    /**
     * Recupera {@code char} depositado no buffer na posição indicada.
     *
     * @param buffer Vetor do qual o {@code char} será recuperado.
     * @param offset Posição inicial do valor a ser recuperado.
     * @return Valor recuperado do buffer na posição indicada.
     */
    public String unpackString(byte[] buffer, int offset) throws UnsupportedEncodingException {
        ByteBuffer wrapped = ByteBuffer.wrap(buffer);

        int tamanho = wrapped.getInt(offset);
        byte[] strBytes = new byte[tamanho];

        wrapped.position(4);
        wrapped.get(strBytes, 0, tamanho);

        return new String(strBytes, "UTF-8");
    }
}
