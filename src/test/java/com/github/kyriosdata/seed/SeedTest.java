package com.github.kyriosdata.seed;


import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SeedTest {

    @Test
    public void tiposPrimitivos() {
        byte[] meta = new byte[0];
        Seed s = Seed.serializa(meta);
        assertEquals(121, s.unpackByte(s.pack((byte)121), 0));
        assertEquals(500, s.unpackShort(s.pack((short)500), 0));
        assertEquals(500, s.unpackInt(s.pack(500), 0));
        assertEquals(500, s.unpackLong(s.pack((long)500), 0));
        assertEquals(5.1, s.unpackFloat(s.pack((float)5.1), 0), 0.0001d);
        assertEquals(5.1, s.unpackDouble(s.pack((double)5.1), 0), 0.0001d);
        assertEquals('a', s.unpackChar(s.pack('a'), 0));
        assertEquals(false, s.unpackBoolean(s.pack(false), 0));
    }

    @Test
    public void tipoString() throws UnsupportedEncodingException {
        byte[] m = new byte[0];
        Seed s = Seed.serializa(m);

        assertEquals("casa", s.unpackString(s.pack("casa"), 0));

        String msg = "um longa mensagem, quer dizer, nem tanto!";
        assertEquals(msg, s.unpackString(s.pack(msg), 0));
    }

    @Test
    public void objetoComUmMembroPrimitivo() {
        byte[] meta = new byte[] { 1, Seed.BOOLEAN };
        Seed s = Seed.serializa(meta);
        s.defineBoolean(0, true);

        // Serializa
        byte[] dados = s.array();

        // Reconstrução
        Seed r = Seed.desserializa(dados);
        assertTrue(r.obtemBoolean(0));
    }

    @Test
    public void objetoComDoisMembrosPrimitivos() {
        byte[] meta = new byte[] { 2, Seed.BOOLEAN, Seed.BOOLEAN };
        Seed s = Seed.serializa(meta);

        s.defineBoolean(0, true);
        s.defineBoolean(1, false);

        // Serialização produzida
        byte[] vetor = s.array();

        Seed r = Seed.desserializa(vetor);

        assertTrue(s.obtemBoolean(0));
        assertFalse(s.obtemBoolean(1));
    }
}

