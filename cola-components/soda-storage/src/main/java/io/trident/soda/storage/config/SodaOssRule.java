package io.trident.soda.storage.config;

import lombok.AllArgsConstructor;

/**
 * 默认存储桶生成规则
 */
@AllArgsConstructor
public class SodaOssRule implements StorageRule {

	/**
	 * 租户模式
	 */
	private final Boolean tenantMode;

	@Override
	public String bucketName(String bucketName) {
        //TODO 后续支持多租户
		String prefix = "";
		return prefix + bucketName;
	}

	@Override
	public String fileName(String originalFilename) {
        //TODO 补充文件名称规则
		return "upload/" + originalFilename;
	}

}
