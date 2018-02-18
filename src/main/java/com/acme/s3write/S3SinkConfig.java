package com.acme.s3write;

import com.fasterxml.jackson.annotation.JsonProperty;

public class S3SinkConfig {
    @JsonProperty("batch-size")
    private Integer batchSize;

    @JsonProperty("hadoopconf")
    private String hadoopconf;

    @JsonProperty("s3-uri")
    private String s3Uri;

    @JsonProperty("file-prefix")
    private String filePrefix;

    @JsonProperty("file-sufix")
    private String fileSufix;

    @JsonProperty("split-date-format")
    private String splitDateFormat;

    @JsonProperty("enable-s3-write")
    private boolean enableS3Write;

    public Integer getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(Integer batchSize) {
        this.batchSize = batchSize;
    }

    public String getHadoopconf() {
        return hadoopconf;
    }

    public void setHadoopconf(String hadoopconf) {
        this.hadoopconf = hadoopconf;
    }

    public String getS3Uri() {
        return s3Uri;
    }

    public void setS3Uri(String s3Uri) {
        this.s3Uri = s3Uri;
    }

    public String getFilePrefix() {
        return filePrefix;
    }

    public void setFilePrefix(String filePrefix) {
        this.filePrefix = filePrefix;
    }

    public String getFileSufix() {
        return fileSufix;
    }

    public void setFileSufix(String fileSufix) {
        this.fileSufix = fileSufix;
    }

    public String getSplitDateFormat() {
        return splitDateFormat;
    }

    public void setSplitDateFormat(String splitDateFormat) {
        this.splitDateFormat = splitDateFormat;
    }

    public boolean getEnableS3Write() {
        return enableS3Write;
    }

    public void setEnableS3Write(boolean enableS3Write) {
        this.enableS3Write = enableS3Write;
    }

    @Override
    public String toString() {
        return "S3SinkConfig{" +
                "batchSize=" + batchSize +
                ", hadoopconf='" + hadoopconf + '\'' +
                ", s3Uri='" + s3Uri + '\'' +
                ", filePrefix='" + filePrefix + '\'' +
                ", fileSufix='" + fileSufix + '\'' +
                ", splitDateFormat='" + splitDateFormat + '\'' +
                ", enableS3Write=" + enableS3Write +
                '}';
    }
}
