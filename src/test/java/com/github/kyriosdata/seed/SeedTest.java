package com.github.kyriosdata.seed;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SeedTest {

    @Test
    public void tipoString() throws UnsupportedEncodingException {
        byte[] m = new byte[0];
        Seed s = Seed.serializa(m);

        assertEquals("casa", s.unpackString(s.pack("casa"), 0));

        String msg = "um longa mensagem, quer dizer, nem tanto!";
        assertEquals(msg, s.unpackString(s.pack(msg), 0));
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

        s.defineLong(0, 11l);

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

        assertEquals(msg, r.obtemString(0));
    }
}

