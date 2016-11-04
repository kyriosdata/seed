/*
 * Copyright (c) 2016 Fábio Nogueira de Lucena
 *
 * Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.seed;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * Serializa um registro, formado por uma combinação de valores
 * de tipo primitivo, uma sequência de caracteres ou um vetor
 * de bytes, em uma sequência de bytes e, no sentido inverso,
 * recupera os campos do registro a partir do vetor de bytes gerado.
 *
 * <p>Cada registro é precedido pela metainformação correspondente.
 * Essa metainformação é definida por uma sequência de bytes.
 * O primeiro deles não é empregado. O segundo indica a quantidade
 * de campos (ou seja, limitada a 128) e, na sequência, para cada
 * um dos campos (quantidade indicada no segundo byte), o tipo
 * correspondente.
 */
public class Seed {

    /**
     * Tipo do valor armazenado é um {@code byte}.
     */
    public final static byte BYTE = 0;

    /**
     * Tipo do valor armazenado é um {@code short}.
     */
    public final static byte SHORT = 1;

    /**
     * Tipo do valor armazenado é um {@code int}.
     */
    public final static byte INT = 2;

    /**
     * Tipo do valor armazenado é um {@code long}.
     */
    public final static byte LONG = 3;

    /**
     * Tipo do valor armazenado é um {@code float}.
     */
    public final static byte FLOAT = 4;

    /**
     * Tipo do valor armazenado é um {@code double}.
     */
    public final static byte DOUBLE = 5;

    /**
     * Tipo do valor armazenado é um {@code boolean}.
     */
    public final static byte BOOLEAN = 6;

    /**
     * Tipo do valor armazenado é um {@code char}.
     */
    public final static byte CHAR = 7;

    /**
     * Tipo do valor armazenado é uma {@code String}.
     */
    public final static byte STRING = 8;

    /**
     * Tipo do valor armazenado é um vetor de {@code byte}.
     */
    public final static byte VETOR = 9;

    /**
     * Posição no vetor de metainformações que contém a
     * quantidade de campos do registro.
     */
    private final static byte POS_QTDE = 1;

    /**
     * Tamanho máximo do buffer empregado para montar o registro.
     */
    public static final int MAX_BUFFER_SIZE = 128;

    /**
     * Tamanhos empregados para armazenar cada um dos
     * tipos primitivos. Observe que o valor do tipo
     * é o índice no vetor para o tamanho correspondente.
     */
    private int[] tamanho = new int[] { 1, 2, 4, 8, 4, 8, 1, 2, 0, 0 };

    /**
     * Marca início dos dados propriamente ditos, primeiro
     * byte após metainformações.
     */
    private int offsetInicio;

    /**
     * Buffer no qual a serialização de um objeto
     * será depositada.
     */
    private ByteBuffer buffer;

    private Seed() {
    }

    /**
     * Cria uma instância com a metainformação indicada.
     *
     * @param meta Metainformação associada ao objeto
     *             a ser serializado. Primeiro byte indica
     *             a quantidade membros e os seguintes,
     *             os tipos de cada um deles, na ordem
     *             em que devem ser armazenados.
     * @return Objeto apto a realizar operações de serialização.
     */
    public static Seed serializa(byte[] meta) {
        Seed s = new Seed();

        // Buffer temporário no qual o registro
        // será construído. 
        s.buffer = ByteBuffer.allocate(MAX_BUFFER_SIZE);

        // Primeiro conteúdo do registro são as
        // metainformações correspondentes.
        s.buffer.put(meta);

        // A posição inicial
        s.offsetInicio = s.posicaoInicialDados();

        return s;
    }

    public static Seed desserializa(byte[] dados) {
        Seed s = new Seed();

        // Buffer do qual dados serão recuperados
        s.buffer = ByteBuffer.wrap(dados);

        // Primeiro byte indica apenas quantidade de membros
        // que segue essa quantidade, armazenada em um byte.
        s.offsetInicio = s.posicaoInicialDados();

        return s;
    }

    /**
     * Posição inicial dos dados do registro, ou seja,
     * posição do primeiro byte após metainformações.
     *
     * @return A posição do primeiro byte de dados do registro.
     */
    public int posicaoInicialDados() {
        return buffer.get(POS_QTDE) + 2;
    }

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
     *
     * @throws UnsupportedEncodingException Caso o suporte para UTF-8
     * não esteja disponível.
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
     *
     * @throws UnsupportedEncodingException Caso o suporte para UTF-8
     * não esteja disponível.
     */
    public String unpackString(byte[] buffer, int offset) throws UnsupportedEncodingException {
        ByteBuffer wrapped = ByteBuffer.wrap(buffer);

        int tamanho = wrapped.getInt(offset);
        byte[] strBytes = new byte[tamanho];

        wrapped.position(4);
        wrapped.get(strBytes, 0, tamanho);

        return new String(strBytes, "UTF-8");
    }

    public byte[] array() {
        byte[] bytesUsados = new byte[tamanhoRegistro()];

        buffer.position(0);
        buffer.get(bytesUsados);

        return bytesUsados;
    }

    /**
     * Identifica o tamanho em bytes do registro.
     *
     * @return Quantidade de bytes ocupada pela serialização
     * do registro.
     */
    public int tamanhoRegistro() {
        int membros = buffer.get(1);

        // Deslocamento de todos os campos.
        return offset(membros);
    }

    public void defineBoolean(int ordem, boolean valor) {
        byte[] bytesValor = pack(valor);
        buffer.position(offset(ordem));
        buffer.put(bytesValor);
    }

    /**
     * Recupera o membro {@code boolean}.
     *
     * @param ordem A ordem do membro a ser recuperado.
     * @return Valor lógico armazenado no membro.
     *
     */
    public boolean obtemBoolean(int ordem) {
        return buffer.get(offset(ordem)) == 1;
    }

    /**
     * Define o caractere para a ordem indicada.
     *
     * @param ordem A ordem do campo no registro.
     * @param valor O valor a ser definido para o campo.
     */
    public void defineChar(int ordem, char valor) {
        byte[] bytesValor = pack(valor);
        buffer.position(offset(ordem));
        buffer.put(bytesValor);
    }

    /**
     * Recupera o caractere do registro.
     *
     * @param ordem A ordem do caractere no registro.
     * @return Valor do caractere armazenado no registro.
     *
     */
    public char obtemChar(int ordem) {
        return buffer.getChar(offset(ordem));
    }

    /**
     * Produz o deslocamento em bytes a partir
     * do início do registro, no qual inicia-se o
     * campo de ordem indicada.
     *
     * @param ordem Ordem do campo do registro.
     *
     * @return Quantidade de bytes, a partir da qual se
     * inicia o membro de ordem indicada.
     */
    public int offset(int ordem) {
        int delta = offsetInicio;
        for (int i = 0; i < ordem; i++) {
            int tipo = buffer.get(i + 2);
            delta = delta + tamanho[tipo];
        }

        return delta;
    }
}
