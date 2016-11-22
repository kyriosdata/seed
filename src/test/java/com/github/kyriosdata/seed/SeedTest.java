package com.github.kyriosdata.seed;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SeedTest {

    @Test
    public void metaInformacao() {
        // Primeira metainformação (posição 0)
        // (0) tipo: 2
        // (0) size: 3
        // (0) atrs: 4, 5, 6

        // Segunda metainformação (posição 5)
        // (5) tipo: 2
        // (5) size: 3
        // (5) atrs: 4, 5, 6
        byte[] meta = new byte[] {
                2, 3, 4, 5, 6,
                1, 2, 3, 4
            };

        byte[] bytes = Seed.serializa(meta).array();

        Seed r = Seed.desserializa(bytes);

        // Primeira metainformação (posição 0)
        assertEquals(2, r.getTipo(0));
        assertEquals(3, r.getTamanho(0));
        assertArrayEquals(new byte[] { 4, 5, 6}, r.getAtributos(0));

        // Segunda metainformação (posição 5)
        assertEquals(1, r.getTipo(5));
        assertEquals(2, r.getTamanho(5));
        assertArrayEquals(new byte[] { 3, 4 }, r.getAtributos(5));

    }

    @Test
    public void registroComUmBoolean() {
        // Primeiro byte não usado
        // Segundo byte indica quantidade de campos do registro
        // Terceiro byte indica tipo armazenado
        byte[] meta = new byte[] { 0, 1, Seed.BOOLEAN };

        Seed s = Seed.serializa(meta);
        s.defineBoolean(0, true);

        // Serializa
        byte[] dados = s.array();

        // Reconstrução
        Seed r = Seed.desserializa(dados);

        assertTrue(r.obtemBoolean(0));
    }

    @Test
    public void registroComDoisMembrosPrimitivos() {
        // Primeiro byte não usado
        // Segundo byte a quantidade de campos
        // Demais bytes, um para cada campo, o tipo correspondente.
        byte[] meta = new byte[] { 0, 2, Seed.BOOLEAN, Seed.BOOLEAN };

        Seed s = Seed.serializa(meta);

        s.defineBoolean(0, true);
        s.defineBoolean(1, false);

        // Serialização produzida
        byte[] vetor = s.array();

        Seed r = Seed.desserializa(vetor);

        assertTrue(r.obtemBoolean(0));
        assertFalse(r.obtemBoolean(1));
    }

    @Test
    public void registroComUmChar() {
        // Primeiro byte não usado
        // Segundo byte a quantidade de campos
        // Demais bytes, um para cada campo, o tipo correspondente.
        byte[] meta = new byte[] { 0, 1, Seed.CHAR };

        Seed s = Seed.serializa(meta);

        s.defineChar(0, 'x');

        // Serialização produzida
        byte[] vetor = s.array();

        Seed r = Seed.desserializa(vetor);

        assertEquals('x', r.obtemChar(0));
    }

    @Test
    public void registroComUmByte() {
        // Primeiro byte não usado
        // Segundo byte a quantidade de campos
        // Demais bytes, um para cada campo, o tipo correspondente.
        byte[] meta = new byte[] { 0, 1, Seed.BYTE };

        Seed s = Seed.serializa(meta);

        s.defineByte(0, (byte)11);

        // Serialização produzida
        byte[] vetor = s.array();

        Seed r = Seed.desserializa(vetor);

        assertEquals(11, r.obtemByte(0));
    }

    @Test
    public void registroComUmShort() {
        byte[] meta = new byte[] { 0, 1, Seed.SHORT };

        Seed s = Seed.serializa(meta);

        s.defineShort(0, (short)11);

        // Serialização produzida
        byte[] vetor = s.array();

        Seed r = Seed.desserializa(vetor);

        assertEquals(11, r.obtemShort(0));
    }

    @Test
    public void registroComUmInt() {
        byte[] meta = new byte[] { 0, 1, Seed.INT };

        Seed s = Seed.serializa(meta);

        s.defineInt(0, 11);

        // Serialização produzida
        byte[] vetor = s.array();

        Seed r = Seed.desserializa(vetor);

        assertEquals(11, r.obtemInt(0));
    }

    @Test
    public void registroComUmLong() {
        byte[] meta = new byte[] { 0, 1, Seed.LONG };

        Seed s = Seed.serializa(meta);

        s.defineLong(0, 11L);

        // Serialização produzida
        byte[] vetor = s.array();

        Seed r = Seed.desserializa(vetor);

        assertEquals(11, r.obtemLong(0));
    }

    @Test
    public void registroComUmFloat() {
        byte[] meta = new byte[] { 0, 1, Seed.FLOAT };

        Seed s = Seed.serializa(meta);

        s.defineFloat(0, 3.1415f);

        // Serialização produzida
        byte[] vetor = s.array();

        Seed r = Seed.desserializa(vetor);

        assertEquals(3.1415, r.obtemFloat(0), 0.0001d);
    }

    @Test
    public void registroComUmDouble() {
        byte[] meta = new byte[] { 0, 1, Seed.DOUBLE };

        Seed s = Seed.serializa(meta);

        s.defineDouble(0, 11.4d);

        // Serialização produzida
        byte[] vetor = s.array();

        Seed r = Seed.desserializa(vetor);

        assertEquals(11.4d, r.obtemDouble(0), 0.0001d);
    }

    @Test
    public void registroComUmaString() throws UnsupportedEncodingException {
        byte[] meta = new byte[] { 0, 1, Seed.STRING };

        Seed s = Seed.serializa(meta);

        String msg = "A vida é bela!";

        s.defineString(0, msg);

        // Serialização produzida
        byte[] vetor = s.array();

        Seed r = Seed.desserializa(vetor);

        assertEquals(msg.getBytes(StandardCharsets.UTF_8).length, r.obtemTamanho(0));
        assertEquals(msg, r.obtemString(0));
    }

    @Test
    public void registroComUmVetorDeBytes() {
        byte[] meta = new byte[] { 0, 2, Seed.BYTE, Seed.VETOR };

        Seed s = Seed.serializa(meta);

        String msg = "A vida é bela!";
        byte[] dados = msg.getBytes(StandardCharsets.UTF_8);

        s.defineByteArray(1, dados);

        // Serialização produzida
        byte[] vetor = s.array();

        Seed r = Seed.desserializa(vetor);

        assertEquals(msg, new String(r.obtemByteArray(1), StandardCharsets.UTF_8));
    }

    @Test
    public void registroComVariosCampos() {
        byte[] meta = new byte[] { 0, 10,
                Seed.VETOR,
                Seed.BOOLEAN,
                Seed.STRING,
                Seed.SHORT,
                Seed.BYTE,
                Seed.CHAR,
                Seed.DOUBLE,
                Seed.FLOAT,
                Seed.INT,
                Seed.LONG
        };

        Seed s = Seed.serializa(meta);

        byte[] d0 = new byte[] { 1, 2 };
        boolean d1 = true;
        String d2 = "açaí";
        short d3 = 3;
        byte d4 = 4;
        char d5 = '@';
        double d6 = 5.6;
        float d7 = 7.8f;
        int d8 = 9;
        long d9 = 10L;

        s.defineByteArray(0, d0);
        s.defineBoolean(1, d1);
        s.defineString(2, d2);
        s.defineShort(3, d3);
        s.defineByte(4, d4);
        s.defineChar(5, d5);
        s.defineDouble(6, d6);
        s.defineFloat(7, d7);
        s.defineInt(8, d8);
        s.defineLong(9, d9);

        // Registro serializado
        byte[] vetor = s.array();

        // Coloca registro serializado como campo em outro
        byte[] metaOuter = new byte[] { 0, 1, Seed.VETOR};
        Seed outer = Seed.serializa(metaOuter);
        outer.defineByteArray(0, vetor);
        byte[] outerBytes = outer.array();

        Seed r = Seed.desserializa(outerBytes);

        // Inicio do registro é após meta e tamanho em bytes.
        r.setOffsetInicio(3 + 4);

        assertArrayEquals(d0, r.obtemByteArray(0));
        assertEquals(d1, r.obtemBoolean(1));
        assertEquals(d2, r.obtemString(2));
        assertEquals(d3, r.obtemShort(3));
        assertEquals(d4, r.obtemByte(4));
        assertEquals(d5, r.obtemChar(5));
        assertEquals(d6, r.obtemDouble(6), 0.0001d);
        assertEquals(d7, r.obtemFloat(7), 0.0001d);
        assertEquals(d8, r.obtemInt(8));
        assertEquals(d9, r.obtemLong(9));
    }
}

