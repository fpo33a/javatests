package AvroTest;

import org.apache.avro.AvroRuntimeException;
import org.apache.avro.io.*;
import org.apache.avro.util.Utf8;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class FpBinaryDecoder extends Decoder {

    static int MAX_ARRAY_SIZE = 4096;           // !!! arbitrary set
    static BinaryDecoder  decoder = null;
    InputStream in = null;

    FpBinaryDecoder (InputStream bis) {
        this.in = bis;
        decoder = DecoderFactory.get().directBinaryDecoder(this.in, decoder);
    }
    @Override
    public void readNull() throws IOException {
        decoder.readNull();
    }

    @Override
    public boolean readBoolean() throws IOException {
        return decoder.readBoolean();
    }

    @Override
    public int readInt() throws IOException {
        return decoder.readInt();
    }

    @Override
    public long readLong() throws IOException {
        return decoder.readLong();
    }

    @Override
    public float readFloat() throws IOException {
        return decoder.readFloat();
    }

    @Override
    public double readDouble() throws IOException {
        return decoder.readDouble();
    }

    protected void doReadBytes(byte[] bytes, int start, int length) throws IOException {
        for (;;) {
            int n = in.read(bytes, start, length);
            if (n == length || length == 0) {
                return;
            } else if (n < 0) {
                throw new EOFException();
            }
            start += n;
            length -= n;
        }
    }

    @Override
    public Utf8 readString(Utf8 old) throws IOException {
        long length = readLong();
        if (length > MAX_ARRAY_SIZE) {
            throw new UnsupportedOperationException("Cannot read strings longer than " + MAX_ARRAY_SIZE + " bytes");
        }
        if (length < 0L) {
            throw new AvroRuntimeException("Malformed data. Length is negative: " + length);
        }
        Utf8 result = (old != null ? old : new Utf8());
        result.setByteLength((int) length);
        if (0L != length) {
            doReadBytes(result.getBytes(), 0, (int) length);
        }
        return result;
    }


    @Override
    public String readString() throws IOException {

        Utf8 utf8 = decoder.readString(null);
        return utf8.toString();
    }

    @Override
    public void skipString() throws IOException {

    }

    @Override
    public ByteBuffer readBytes(ByteBuffer old) throws IOException {
        return decoder.readBytes(old);
    }

    @Override
    public void skipBytes() throws IOException {

    }

    @Override
    public void readFixed(byte[] bytes, int start, int length) throws IOException {
        doReadBytes(bytes, start, length);
    }

    @Override
    public void skipFixed(int length) throws IOException {

    }

    @Override
    public int readEnum() throws IOException {
        return decoder.readEnum();
    }

    @Override
    public long readArrayStart() throws IOException {
        return decoder.readArrayStart();
    }

    @Override
    public long arrayNext() throws IOException {
        return decoder.arrayNext();
    }

    @Override
    public long skipArray() throws IOException {
        return decoder.skipArray();
    }

    @Override
    public long readMapStart() throws IOException {
        return decoder.readMapStart();
    }

    @Override
    public long mapNext() throws IOException {
        return decoder.mapNext();
    }

    @Override
    public long skipMap() throws IOException {
        return decoder.skipMap();
    }

    @Override
    public int readIndex() throws IOException {
        return decoder.readIndex();
    }
}
