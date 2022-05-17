package io.trident.soda.storage.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Oss类型枚举
 */
@Getter
@AllArgsConstructor
public enum StorageStatusEnum {

	/**
	 * 关闭
	 */
	DISABLE(1),
	/**
	 * 启用
	 */
	ENABLE(2),
	;

	/**
	 * 类型编号
	 */
	final int num;

}
