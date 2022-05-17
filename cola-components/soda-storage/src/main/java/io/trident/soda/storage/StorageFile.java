package io.trident.soda.storage;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class StorageFile {
    /**
     * 文件地址
     */
    private String url;
    /**
     * 文件名
     */
    private String name;
    /**
     * 文件hash值
     */
    public String hash;
    /**
     * 文件大小
     */
    private long length;

    /**
     * 文件contentType
     */
    private String contentType;

    /**
     * 最后修改时间
     */
    private LocalDateTime lastModified;

}
