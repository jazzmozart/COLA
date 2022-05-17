package io.trident.soda.storage.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Oss枚举类
 */
@Getter
@AllArgsConstructor
public enum StorageEnum {

	/**
	 * minio
	 */
	MINIO("minio", 1),

	/**
	 * qiniu
	 */
	QINIU("qiniu", 2),

	/**
	 * ali
	 */
	ALI("ali", 3),

	/**
	 * tencent
	 */
	TENCENT("tencent", 4),

	/**
	 * huawei
	 */
	HUAWEI("huawei", 5);

	/**
	 * 名称
	 */
	final String name;
	/**
	 * 类型
	 */
	final int category;

	/**
	 * 匹配枚举值
	 *
	 * @param name 名称
	 * @return StorageEnum
	 */
	public static StorageEnum of(String name) {
		if (name == null) {
			return null;
		}
		StorageEnum[] values = StorageEnum.values();
		for (StorageEnum storageEnum : values) {
			if (storageEnum.name.equals(name)) {
				return storageEnum;
			}
		}
		return null;
	}

}
