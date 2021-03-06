/*
 * Copyright (c) 2016 Fábio Nogueira de Lucena
 *
 * Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.seed;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Serializa um registro em um vetor de bytes e, no sentido inverso,
 * permite recuperar os valores correspondentes a partir do vetor
 * gerado anteriormente.
 *
 * <p>Cada registro é formado por uma sequência de campos, ordenados
 * de 0 até n - 1, onde n é o total de campos. O tipo de um campo pode
 * ser um tipo primitivo, uma sequência de caracteres (String)
 * ou vetor de bytes. Os tipos possíveis são: {@link #BYTE},
 * {@link #BOOLEAN}, {@link #CHAR}, {@link #STRING}, {@link #VETOR},
 * {@link #SHORT}, {@link #INT}, {@link #LONG}, {@link #FLOAT} e
 * {@link #DOUBLE}.
 *
 * <p>A serialização do registro inclui, nos bytes iniciais, a
 * metainformação correspondente. A metainformação é empregada
 * para assegurar que as informações originalmente fornecidas possam
 * ser recuperadas.
 *
 * <p>A metainformação é definida por uma sequência de bytes.
 * O primeiro byte não é empregado. O segundo indica a quantidade
 * de campos do registro (ou seja, limitado a 127 campos) e, na
 * sequência, para cada um dos campos, o tipo correspondente.
 * A definição de cada tipo faz uso de um byte (veja constantes
 * citadas acima).
 *
 * <p>A construção de um registro após definida a metainformação
 * correspondente deve ser feita estritamente na ordem em que os
 * campos foram definidos na metainformação. Ou seja, o primeiro
 * valor a ser depositado no registro deve ser aquele do primeiro
 * campo, o segundo deve ser o segundo da metainformação e assim
 * sucessivamente. Essa exigência decorre, em parte, da existência
 * de tipos de tamanho variável como sequências de caracteres e
 * vetores de bytes. Nesses casos, o tamanho de cada um deles só
 * pode ser definido quando o valor correspondente é estabelecido.
 *
 * <p>O uso da classe para a serialização envolve a obtenção de
 * uma instância para tal por meio do método {@link #serializa(byte[])}.
 * A operação inversa exige o uso do método {@link #desserializa(byte[])}.
 * No primeiro caso a metainformação é fornecida (vetor de bytes), no
 * segundo, o vetor de bytes gerado pelo método {@link #array()} deve
 * ser utilizado.
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
     * Esse tipo é empregado com propósito geral, por exemplo,
     * manter uma imagem ou mesmo a serialização de outro
     * objeto.
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
    private static final int MAX_BUFFER_SIZE = 128;

    /**
     * Tamanhos empregados para armazenar cada um dos
     * tipos primitivos. Observe que o valor do tipo
     * é o índice no vetor do tamanho correspondente.
     */
    private final int[] tamanho = new int[]{1, 2, 4, 8, 4, 8, 1, 2, 0, 0};

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

    /**
     * Evita criação desenecessária de instância.
     * Consulte {@link #serializa(byte[])} ou
     * {@link #desserializa(byte[])}.
     */
    private Seed() {
    }

    /**
     * Cria uma instância com a metainformação indicada
     * a ser utilizada para serialização.
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
        s.setOffsetInicio(0);

        return s;
    }

    /**
     * Cria uma instância a ser utilizada para recuperar valores
     * do vetor de bytes previamente serializado.
     *
     * @param dados Vetor de bytes previamente serializado conforme
     *              a descrição dessa classe.
     * @return Instância que recupera valores do vetor de bytes.
     *
     * @see #serializa(byte[])
     */
    public static Seed desserializa(byte[] dados) {
        return desserializa(dados, 0);
    }

    /**
     * Cria uma instância a ser utilizada para recuperar valores
     * do vetor de bytes previamente serializado.
     *
     * @param dados Vetor de bytes previamente serializado conforme
     *              a descrição dessa classe.
     * @param inicio Posição inicial do registro no vetor.
     * @return Instância que recupera valores do vetor de bytes.
     *
     * @see #serializa(byte[])
     */
    public static Seed desserializa(byte[] dados, int inicio) {
        Seed s = new Seed();

        // Buffer do qual dados serão recuperados
        s.buffer = ByteBuffer.wrap(dados);

        s.setOffsetInicio(inicio);

        return s;
    }

    /**
     * Define posição inicial do registro corrente.
     *
     * @param inicio Posição inicial na qual se inicia
     *               registro no vetor de bytes.
     *
     * @see #getOffsetInicio()
     */
    public void setOffsetInicio(int inicio) {
        offsetInicio = inicio;
    }

    /**
     * Recupera posição inicial do registro corrente.
     * @return A posição do byte inicial do registro corrente no vetor.
     *
     * @see #setOffsetInicio(int)
     */
    public int getOffsetInicio() {
        return offsetInicio;
    }

    /**
     * Recupera o vetor de bytes empregado no processo de
     * serialização/desserialização.
     *
     * @return Vetor de bytes exatamente como fornecido na desserialização
     * ou o vetor de bytes (todos eles utilizados) no processo de
     * serialização.
     */
    public byte[] array() {
        byte[] bytesUsados = new byte[tamanhoRegistro()];

        buffer.position(0);
        buffer.get(bytesUsados);

        return bytesUsados;
    }

    /**
     * Recupera o valor correspondente ao tipo da metainformação.
     *
     * @param posicao Posição inicial da metainformação.
     *
     * @return Valor do tipo da metainformação.
     *
     * @see #getTamanho(int)
     * @see #getAtributos(int)
     */
    public byte getTipo(int posicao) {
        return buffer.get(posicao);
    }

    /**
     * Recupera a quantidade de atributos da metainformação que
     * se inicia na posição.
     *
     * @param posicao A posição de início da metainformação.
     *
     * @return Quantidade de atributos da metainformação.
     *
     * @see #getTipo(int)
     * @see #getAtributos(int)
     */
    public byte getTamanho(int posicao) {
        return buffer.get(posicao + 1);
    }

    /**
     * Recupera vetor contendo os atributos da metainformação.
     *
     * @param posicao Posição inicial da metainformação.
     *
     * @return Vetor contendo um byte para cada atributo da
     * metainformação, na ordem empregada para armazená-los.
     *
     * @see #getTipo(int)
     * @see #getTamanho(int)
     */
    public byte[] getAtributos(int posicao) {
        byte qtdAtributos = getTamanho(posicao);
        byte[] atributos = new byte[qtdAtributos];

        buffer.position(posicao + 2);
        buffer.get(atributos, 0, qtdAtributos);

        return atributos;
    }

    /**
     * Define o valor lógico para o campo indicado.
     * @param ordem A ordem do campo que recebe o valor.
     * @param valor O valor definido para o campo.
     */
    public void defineBoolean(int ordem, boolean valor) {
        ByteBuffer bf = ByteBuffer.allocate(1);
        bf.put((byte) (valor ? 1 : 0));
        byte[] bytesValor = bf.array();
        buffer.position(offset(ordem));
        buffer.put(bytesValor);
    }

    /**
     * Recupera o membro {@code boolean}.
     *
     * @param ordem A ordem do membro a ser recuperado.
     * @return Valor lógico armazenado no membro.
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
        ByteBuffer buffer1 = ByteBuffer.allocate(2);
        buffer1.putChar(valor);
        byte[] bytesValor = buffer1.array();
        buffer.position(offset(ordem));
        buffer.put(bytesValor);
    }

    /**
     * Recupera o caractere do registro.
     *
     * @param ordem A ordem do caractere no registro.
     * @return Valor do caractere armazenado no registro.
     */
    public char obtemChar(int ordem) {
        return buffer.getChar(offset(ordem));
    }

    /**
     * Define o byte para a ordem indicada.
     *
     * @param ordem A ordem do campo no registro.
     * @param valor O valor a ser definido para o campo.
     */
    public void defineByte(int ordem, byte valor) {
        buffer.position(offset(ordem));
        buffer.put(valor);
    }

    /**
     * Recupera o byte do registro.
     *
     * @param ordem A ordem do caractere no registro.
     * @return Valor do caractere armazenado no registro.
     */
    public byte obtemByte(int ordem) {
        return buffer.get(offset(ordem));
    }

    /**
     * Define o valor {@code short} para a ordem indicada.
     *
     * @param ordem A ordem do campo no registro.
     * @param valor O valor a ser definido para o campo.
     */
    public void defineShort(int ordem, short valor) {
        buffer.position(offset(ordem));
        buffer.putShort(valor);
    }

    /**
     * Recupera o valor {@code short} da ordem indicada do registro.
     *
     * @param ordem A ordem do campo no registro.
     * @return Valor do caractere armazenado no registro.
     */
    public short obtemShort(int ordem) {
        return buffer.getShort(offset(ordem));
    }

    /**
     * Define o valor {@code int} para a ordem indicada
     * no registro.
     *
     * @param ordem A ordem do campo no registro.
     * @param valor O valor a ser definido para o campo.
     */
    public void defineInt(int ordem, int valor) {
        buffer.position(offset(ordem));
        buffer.putInt(valor);
    }

    /**
     * Recupera o valor {@code int} da ordem indicada do registro.
     *
     * @param ordem A ordem do campo no registro.
     * @return Valor do caractere armazenado no registro.
     */
    public int obtemInt(int ordem) {
        return buffer.getInt(offset(ordem));
    }

    /**
     * Define o valor {@code long} para a ordem indicada
     * no registro.
     *
     * @param ordem A ordem do campo no registro.
     * @param valor O valor a ser definido para o campo.
     */
    public void defineLong(int ordem, long valor) {
        buffer.position(offset(ordem));
        buffer.putLong(valor);
    }

    /**
     * Recupera o valor {@code long} da ordem indicada do registro.
     *
     * @param ordem A ordem do campo no registro.
     * @return Valor do caractere armazenado no registro.
     */
    public long obtemLong(int ordem) {
        return buffer.getLong(offset(ordem));
    }

    /**
     * Define o valor {@code float} para a ordem indicada
     * no registro.
     *
     * @param ordem A ordem do campo no registro.
     * @param valor O valor a ser definido para o campo.
     */
    public void defineFloat(int ordem, float valor) {
        buffer.position(offset(ordem));
        buffer.putFloat(valor);
    }

    /**
     * Recupera o valor {@code float} da ordem indicada do registro.
     *
     * @param ordem A ordem do campo no registro.
     * @return Valor do caractere armazenado no registro.
     */
    public float obtemFloat(int ordem) {
        return buffer.getFloat(offset(ordem));
    }

    /**
     * Define o valor {@code double} para a ordem indicada
     * no registro.
     *
     * @param ordem A ordem do campo no registro.
     * @param valor O valor a ser definido para o campo.
     */
    public void defineDouble(int ordem, double valor) {
        buffer.position(offset(ordem));
        buffer.putDouble(valor);
    }

    /**
     * Recupera o valor {@code double} da ordem indicada do registro.
     *
     * @param ordem A ordem do campo no registro.
     * @return Valor do caractere armazenado no registro.
     */
    public double obtemDouble(int ordem) {
        return buffer.getDouble(offset(ordem));
    }

    /**
     * Sequências de caracteres e vetores são armazenados como
     * sequências de bytes. A sequência é precidida da quantidade
     * de bytes utilizadas. O presente método permite recuperar,
     * para um tipo {@link #STRING} ou {@link #VETOR}, a quantidade
     * de bytes correspondente.
     *
     * @param ordem Ordem do campo do tipo {@link #STRING} ou
     *              {@link #VETOR}.
     *
     * @return Tamanho em bytes do vetor de bytes empregado para
     * armazenar a sequência de caracteres ou o vetor de bytes.
     * Não inclui o armazenamento do próprio tamanho.
     */
    public int obtemTamanho(int ordem) {
        int delta = offset(ordem);
        return buffer.getInt(delta);
    }

    /**
     * Define o valor {@code String} para a ordem indicada
     * no registro.
     *
     * @param ordem A ordem do campo no registro.
     * @param valor O valor a ser definido para o campo.
     */
    public void defineString(int ordem, String valor) {
        buffer.position(offset(ordem));
        buffer.put(pack(valor));
    }

    /**
     * Recupera o valor {@code double} da ordem indicada do registro.
     *
     * @param ordem A ordem do campo no registro.
     * @return Valor do caractere armazenado no registro.
     */
    public String obtemString(int ordem) {
        int delta = offset(ordem);
        buffer.position(delta);
        return unpackString(buffer.array(), delta);
    }

    /**
     * Define o vetor de bytes para a ordem indicada
     * no registro.
     *
     * @param ordem A ordem do campo no registro.
     * @param valor O valor a ser definido para o campo.
     */
    public void defineByteArray(int ordem, byte[] valor) {
        buffer.position(offset(ordem));
        buffer.put(pack(valor));
    }

    /**
     * Recupera o vetor de bytes da ordem indicada do registro.
     *
     * @param ordem A ordem do campo no registro.
     * @return Valor do caractere armazenado no registro.
     */
    public byte[] obtemByteArray(int ordem) {
        int delta = offset(ordem);
        buffer.position(delta);
        return unpackByteArray(buffer.array(), delta);
    }

    /**
     * Produz o deslocamento em bytes a partir
     * do início do registro, no qual inicia-se o
     * campo de ordem indicada.
     * <p>
     * <p><b>Importante:</b> esse método só está
     * definido para registros cujos campos já
     * foram definidos os respectivos valores.
     * Ou seja, apenas as metainformações não são
     * suficientes, mas os valores devem ser
     * estabelecidos. Convém recordar que sequências
     * de caracteres e vetores de bytes possuem
     * tamanho variado.
     *
     * @param ordem Ordem do campo do registro.
     * @return Quantidade de bytes, a partir da qual se
     * inicia o membro de ordem indicada.
     */
    public int offset(int ordem) {
        int delta = posicaoInicialDados();
        for (int i = 0; i < ordem; i++) {
            byte tipo = buffer.get(offsetInicio + i + 2);
            if (tipo == STRING || tipo == VETOR) {
                buffer.position(offset(i));

                // Inclui o inteiro que guarda o tamanho
                // mais a quantidade de bytes por ele indicada
                delta = delta + 4 + buffer.getInt();
            } else {
                delta = delta + tamanho[tipo];
            }
        }

        return delta;
    }

    /**
     * Identifica o tamanho em bytes do registro após
     * valores dos campos estarem definidos.
     * <p>
     * <p>Observe que antes da definição dos valores dos
     * campos não é possível identificar o tamanho do registro,
     * que pode conter campos de tamanho variável como
     * sequências de caracteres e vetores de byte.
     *
     * @return Quantidade de bytes ocupada pela serialização
     * do registro.
     */
    private int tamanhoRegistro() {
        int membros = buffer.get(offsetInicio + 1);

        return offset(membros);
    }

    /**
     * Posição inicial dos dados do registro, ou seja,
     * posição do primeiro byte após metainformações.
     * <p>
     * <p>O primeiro byte não é utilizado, o segundo
     * indica a quantidade de campos do registro, ou seja,
     * a posição inicial é dada pela quantidade de campos
     * do registro mais 2.
     *
     * @return A posição do primeiro byte de dados do registro.
     */
    private int posicaoInicialDados() {
        return buffer.get(offsetInicio + POS_QTDE) + 2 + offsetInicio;
    }

    /**
     * Empacota um {@code String} em vetor de bytes.
     *
     * @param valor Valor a ser empacotado.
     * @return Vetor de bytes contendo o valor empacotado.
     */
    private byte[] pack(String valor) {
        return pack(valor.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Empacota o vertor de bytes em outro vetor de bytes.
     *
     * @param bytes Vetor de bytes a ser empacotado.
     * @return Vetor de bytes que empacota um vetor de bytes.
     * @see #unpackByteArray(byte[], int)
     */
    private byte[] pack(byte[] bytes) {
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
    private String unpackString(byte[] buffer, int offset) {
        byte[] strBytes = unpackByteArray(buffer, offset);

        return new String(strBytes, StandardCharsets.UTF_8);
    }

    /**
     * Recupera o vetor de bytes disponível no buffer a partir
     * da posição indicada.
     *
     * @param buffer Buffer que contém o vetor de bytes.
     * @param offset Posição inicial do vetor de bytes no buffer.
     * @return Vetor de bytes.
     * @see #pack(byte[])
     */
    private byte[] unpackByteArray(byte[] buffer, int offset) {
        ByteBuffer wrapped = ByteBuffer.wrap(buffer);

        int tamanho = wrapped.getInt(offset);
        byte[] strBytes = new byte[tamanho];

        wrapped.position(offset + 4);
        wrapped.get(strBytes, 0, tamanho);
        return strBytes;
    }
}
