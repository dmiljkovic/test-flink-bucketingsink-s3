package com.acme.s3write;

import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.*;

//com.acme.s3write.Write2S3
public class Write2S3 {
    private static DataStream<String> ds1(StreamExecutionEnvironment env) {
        List<String> rec = new ArrayList<>();
        int numEvents = 100;
        for (int i = 0; i < numEvents; ++i) {
            String val = "s1-test-val-" + i;
            rec.add(val);
        }

        return env.fromCollection(rec);
    }

    public static void main(String[] args) throws Exception {
        S3SinkConfig s3conf = new S3SinkConfig();
        s3conf.setHadoopconf("resurces");
        s3conf.setS3Uri("s3://xxxxx");
        s3conf.setSplitDateFormat("yyyy-MM-dd");
        s3conf.setFilePrefix("file-");
        s3conf.setFileSufix(".json");

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //https://ci.apache.org/projects/flink/flink-docs-release-1.3/ops/state_backends.html#available-state-backends
        //env.setStateBackend(new MemoryStateBackend(1073741824, true));
        //needed for S3 write
        env.setStateBackend(new FsStateBackend("file:///tmp", true));


        Map par = new HashMap();
        par.put("fs.hdfs.hadoopconf", s3conf.getHadoopconf());
        ParameterTool pt = ParameterTool.fromMap(par);
        env.getConfig().setGlobalJobParameters(pt);
        DataStream<String> ds1 = ds1(env);

        S3BucketingSink bs1 = new S3BucketingSink(s3conf, "bbb");
        bs1.write(ds1);

        env.execute();
    }
}