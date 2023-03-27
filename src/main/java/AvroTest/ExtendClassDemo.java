package AvroTest;

import org.apache.avro.Schema;
import org.apache.avro.generic.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ExtendClassDemo extends AbstractClassDemo {

    private String testString () {
        return "ExtendClassDemo";
    }

    public static void main (String args[]) {
        ExtendClassDemo extendClassDemo = new ExtendClassDemo();

        System.out.println(extendClassDemo.testString());
        System.out.println(extendClassDemo.testInt());

        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse("{\n" +
                "     \"type\": \"record\",\n" +
                "     \"namespace\": \"com.example\",\n" +
                "     \"name\": \"Customer\",\n" +
                "     \"fields\": [\n" +
                "       { \"name\": \"first_name\", \"type\": \"string\", \"doc\": \"First Name of Customer\" },\n" +
                "       { \"name\": \"last_name\", \"type\": \"string\", \"doc\": \"Last Name of Customer\" },\n" +
                "       { \"name\": \"age\", \"type\": \"int\", \"doc\": \"Age at the time of registration\" },\n" +
                "       { \"name\": \"height\", \"type\": \"float\", \"doc\": \"Height at the time of registration in cm\" },\n" +
                "       { \"name\": \"weight\", \"type\": \"float\", \"doc\": \"Weight at the time of registration in kg\" },\n" +
                "       { \"name\": \"automated_email\", \"type\": \"boolean\", \"default\": true, \"doc\": \"Field indicating if the user is enrolled in marketing emails\" }\n" +
                "     ]\n" +
                "}");


        // we build our first customer
        GenericRecordBuilder customerBuilder = new GenericRecordBuilder(schema);
        customerBuilder.set("first_name", "Frank");
        customerBuilder.set("last_name", "Polet");
        customerBuilder.set("age", 52);
        customerBuilder.set("height", 185f);
        customerBuilder.set("weight", 80.5f);
        customerBuilder.set("automated_email", false);
        GenericData.Record myCustomer = customerBuilder.build();
        System.out.println(myCustomer);


        // test serialization
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        FpBinaryEncoder fpBinaryEncoder = new FpBinaryEncoder(bos) ;
        GenericDatumWriter<GenericRecord> writer = new GenericDatumWriter<GenericRecord>(schema);
        try {
            writer.write(myCustomer, fpBinaryEncoder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("bos = "+bos);


        // test deserialization

        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        FpBinaryDecoder fpBinaryDecoder = new FpBinaryDecoder(bis);
        GenericDatumReader<GenericRecord> reader = new GenericDatumReader<GenericRecord>(schema);
        GenericRecord record = new GenericData.Record(schema);
        try {
            reader.read(record, fpBinaryDecoder);
            System.out.println(record);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
