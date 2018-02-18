package com.acme.s3write;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.connectors.fs.StringWriter;
import org.apache.flink.streaming.connectors.fs.bucketing.BucketingSink;
import org.apache.flink.streaming.connectors.fs.bucketing.DateTimeBucketer;

public class S3BucketingSink<T> {
    private final BucketingSink<String> sink;
    private final String uidPrefix;

    public S3BucketingSink(S3SinkConfig s3Conf) {
        this(s3Conf, "");
    }

    public S3BucketingSink(S3SinkConfig s3Conf, String subDir) {
        String s3Uri = s3Conf.getS3Uri();
        if (!subDir.equals("")) {
            s3Uri += "/" + subDir;
        }
        sink = new BucketingSink<>(s3Uri);
        sink.setBucketer(new DateTimeBucketer(s3Conf.getSplitDateFormat()));
        sink.setWriter(new StringWriter<>());
        if (s3Conf.getBatchSize() != null) {
            sink.setBatchSize(s3Conf.getBatchSize());
        }
        sink.setPendingPrefix(s3Conf.getFilePrefix());
        sink.setPendingSuffix(s3Conf.getFileSufix());

        String sufix;
        if (subDir.equals("")) {
            sufix = "";
        }
        else {
            sufix = "_" + subDir;
        }
        uidPrefix = "S3BucketingSink_write_" + sufix;
    }

    public void write(DataStream<T> events) {
        SingleOutputStreamOperator<String> ss =
                events.map(new MapFunction<T, String>(){
                    @Override
                    public String map(T val) {
                        return val.toString();
                    }}).uid(uidPrefix + "_UID_1").name(uidPrefix + "_UID_1");

        ss.print().name(uidPrefix + "_UID_2");

        ss.addSink(sink)
                .uid(uidPrefix + "_UID_3")
                .name(uidPrefix+ "_UID_3");
    }
}
