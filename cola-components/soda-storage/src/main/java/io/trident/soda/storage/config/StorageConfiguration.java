package io.trident.soda.storage.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 存储配置类
 */
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
@EnableConfigurationProperties(StorageProperties.class)
public class StorageConfiguration {

	private final StorageProperties storageProperties;

	@Bean
	@ConditionalOnMissingBean(StorageRule.class)
	public StorageRule ossRule() {
		return new SodaOssRule(storageProperties.getTenantMode());
	}

}
