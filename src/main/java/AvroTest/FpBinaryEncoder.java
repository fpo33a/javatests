package AvroTest;

import org.apache.avro.io.DirectBinaryEncoder;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.util.Utf8;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class FpBinaryEncoder extends Encoder {

    static DirectBinaryEncoder directBinaryEncoder = null;

    FpBinaryEncoder (OutputStream out) {
        directBinaryEncoder = (DirectBinaryEncoder) EncoderFactory.get().directBinaryEncoder(out, directBinaryEncoder);
    }

    @Override
    public void writeNull() throws IOException {
        directBinaryEncoder.writeNull();
    }

    @Override
    public void writeBoolean(boolean b) throws IOException {
        directBinaryEncoder.writeBoolean(b);
    }

    @Override
    public void writeInt(int n) throws IOException {
        directBinaryEncoder.writeInt(n);
    }

    @Override
    public void writeLong(long n) throws IOException {
        directBinaryEncoder.writeLong(n);
    }

    @Override
    public void writeFloat(float f) throws IOException {
        directBinaryEncoder.writeFloat(f);
    }

    @Override
    public void writeDouble(double d) throws IOException {
        directBinaryEncoder.writeDouble(d);
    }

    @Override
    public void writeString(Utf8 utf8) throws IOException {
        //this.writeString(utf8);
        System.out.println("in writeString "+utf8);

        this.writeBytes(utf8.getBytes(), 0, utf8.getByteLength());
    }

    @Override
    public void writeBytes(ByteBuffer bytes) throws IOException {
        directBinaryEncoder.writeBytes(bytes);
    }

    @Override
    public void writeBytes(byte[] bytes, int start, int len) throws IOException {
        directBinaryEncoder.writeBytes(bytes,start,len);
    }

    @Override
    public void writeFixed(byte[] bytes, int start, int len) throws IOException {
        directBinaryEncoder.writeFixed(bytes,start,len);
    }

    @Override
    public void writeEnum(int e) throws IOException {
        directBinaryEncoder.writeEnum(e);
    }

    @Override
    public void writeArrayStart() throws IOException {
        directBinaryEncoder.writeArrayStart();
    }

    @Override
    public void setItemCount(long itemCount) throws IOException {
        directBinaryEncoder.setItemCount(itemCount);
    }

    @Override
    public void startItem() throws IOException {
        directBinaryEncoder.startItem();
    }

    @Override
    public void writeArrayEnd() throws IOException {
        directBinaryEncoder.writeArrayEnd();
    }

    @Override
    public void writeMapStart() throws IOException {
        directBinaryEncoder.writeMapStart();
    }

    @Override
    public void writeMapEnd() throws IOException {
        directBinaryEncoder.writeMapEnd();
    }

    @Override
    public void writeIndex(int unionIndex) throws IOException {
        directBinaryEncoder.writeIndex(unionIndex);
    }

    @Override
    public void flush() throws IOException {
        directBinaryEncoder.flush();
    }
}
