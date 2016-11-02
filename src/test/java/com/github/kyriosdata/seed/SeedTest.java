package com.github.kyriosdata.seed;


import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

public class SeedTest {

    @Test
    public void tiposPrimitivos() {
        Seed s = new Seed();
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
        Seed s = new Seed();

        assertEquals("casa", s.unpackString(s.pack("casa"), 0));

        String msg = "um longa mensagem, quer dizer, nem tanto!";
        assertEquals(msg, s.unpackString(s.pack(msg), 0));
    }
}

