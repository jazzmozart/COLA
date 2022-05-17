package io.trident.soda.storage.template;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyun.oss.model.PutObjectResult;
import io.trident.soda.storage.SodaFile;
import io.trident.soda.storage.StorageFile;
import io.trident.soda.storage.config.StorageRule;
import io.trident.soda.storage.config.StorageProperties;
import io.trident.soda.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * AliossTemplate
 */
@AllArgsConstructor
public class AliossTemplate implements StorageTemplate {
	private final OSSClient ossClient;
	private final StorageProperties ossProperties;
	private final StorageRule storageRule;

	@Override
	@SneakyThrows
	public void makeBucket(String bucketName) {
		if (!bucketExists(bucketName)) {
			ossClient.createBucket(getBucketName(bucketName));
		}
	}

	@Override
	@SneakyThrows
	public void removeBucket(String bucketName) {
		ossClient.deleteBucket(getBucketName(bucketName));
	}

	@Override
	@SneakyThrows
	public boolean bucketExists(String bucketName) {
		return ossClient.doesBucketExist(getBucketName(bucketName));
	}

	@Override
	@SneakyThrows
	public void copy(String bucketName, String fileName, String destBucketName) {
		ossClient.copyObject(getBucketName(bucketName), fileName, getBucketName(destBucketName), fileName);
	}

	@Override
	@SneakyThrows
	public void copy(String bucketName, String fileName, String destBucketName, String destFileName) {
		ossClient.copyObject(getBucketName(bucketName), fileName, getBucketName(destBucketName), destFileName);
	}

	@Override
	@SneakyThrows
	public StorageFile getInfo(String fileName) {
		return getInfo(ossProperties.getBucketName(), fileName);
	}

	@Override
	@SneakyThrows
	public StorageFile getInfo(String bucketName, String fileName) {
		ObjectMetadata stat = ossClient.getObjectMetadata(getBucketName(bucketName), fileName);
        StorageFile ossFile = new StorageFile();
		ossFile.setName(fileName);
		ossFile.setUrl(getURL(ossFile.getName()));
		ossFile.setHash(stat.getContentMD5());
		ossFile.setLength(stat.getContentLength());
		ossFile.setLastModified(LocalDateTimeUtil.of(stat.getLastModified()));
		ossFile.setContentType(stat.getContentType());
		return ossFile;
	}

	@Override
	@SneakyThrows
	public String filePath(String fileName) {
		return getOssHost().concat("/").concat(fileName);
	}

	@Override
	@SneakyThrows
	public String filePath(String bucketName, String fileName) {
		return getOssHost(bucketName).concat("/").concat(fileName);
	}

	@Override
	@SneakyThrows
	public String getURL(String fileName) {
		return getOssHost().concat("/").concat(fileName);
	}

	@Override
	@SneakyThrows
	public String getURL(String bucketName, String fileName) {
		return getOssHost(bucketName).concat("/").concat(fileName);
	}

	/**
	 * 文件对象
	 *
	 * @param file 上传文件类
	 * @return 文件对象
	 */
	@Override
	@SneakyThrows
	public SodaFile upload(MultipartFile file) {
		return upload(ossProperties.getBucketName(), file.getOriginalFilename(), file);
	}

	/**
	 * @param fileName 上传文件名
	 * @param file     上传文件类
	 * @return  文件对象
	 */
	@Override
	@SneakyThrows
	public SodaFile upload(String fileName, MultipartFile file) {
		return upload(ossProperties.getBucketName(), fileName, file);
	}

	@Override
	@SneakyThrows
	public SodaFile upload(String bucketName, String fileName, MultipartFile file) {
		return upload(bucketName, fileName, file.getInputStream());
	}

	@Override
	@SneakyThrows
	public SodaFile upload(String fileName, InputStream stream) {
		return upload(ossProperties.getBucketName(), fileName, stream);
	}

	@Override
	@SneakyThrows
	public SodaFile upload(String bucketName, String fileName, InputStream stream) {
		return put(bucketName, stream, fileName, false);
	}

	@SneakyThrows
	public SodaFile put(String bucketName, InputStream stream, String key, boolean cover) {
		makeBucket(bucketName);
		String originalName = key;
		key = getFileName(key);
		// 覆盖上传
		if (cover) {
			ossClient.putObject(getBucketName(bucketName), key, stream);
		} else {
			PutObjectResult response = ossClient.putObject(getBucketName(bucketName), key, stream);
			int retry = 0;
			int retryCount = 5;
			while (ObjectUtil.isEmpty(response.getETag()) && retry < retryCount) {
				response = ossClient.putObject(getBucketName(bucketName), key, stream);
				retry++;
			}
		}
		SodaFile file = new SodaFile();
		file.setOriginalName(originalName);
		file.setName(key);
		file.setDomain(getOssHost(bucketName));
		file.setLink(getURL(bucketName, key));
		return file;
	}

	@Override
	@SneakyThrows
	public void delete(String fileName) {
		ossClient.deleteObject(getBucketName(), fileName);
	}

	@Override
	@SneakyThrows
	public void delete(String bucketName, String fileName) {
		ossClient.deleteObject(getBucketName(bucketName), fileName);
	}

	@Override
	@SneakyThrows
	public void delete(List<String> fileNames) {
		fileNames.forEach(this::delete);
	}

	@Override
	@SneakyThrows
	public void delete(String bucketName, List<String> fileNames) {
		fileNames.forEach(fileName -> delete(getBucketName(bucketName), fileName));
	}

	/**
	 * 根据规则生成存储桶名称规则
	 *
	 * @return String
	 */
	private String getBucketName() {
		return getBucketName(ossProperties.getBucketName());
	}

	/**
	 * 根据规则生成存储桶名称规则
	 *
	 * @param bucketName 存储桶名称
	 * @return String
	 */
	private String getBucketName(String bucketName) {
		return storageRule.bucketName(bucketName);
	}

	/**
	 * 根据规则生成文件名称规则
	 *
	 * @param originalFilename 原始文件名
	 * @return string
	 */
	private String getFileName(String originalFilename) {
		return storageRule.fileName(originalFilename);
	}

	public String getUploadToken() {
		return getUploadToken(ossProperties.getBucketName());
	}

	/**
	 * TODO 过期时间
	 * <p>
	 * 获取上传凭证，普通上传
	 */
	public String getUploadToken(String bucketName) {
		// 默认过期时间2小时
		return getUploadToken(bucketName, (long)ossProperties.getArgs().getOrDefault("expireTime", 3600L));
	}

	/**
	 * TODO 上传大小限制、基础路径
	 * <p>
	 * 获取上传凭证，普通上传
	 */
	public String getUploadToken(String bucketName, long expireTime) {
		String baseDir = "upload";

		long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
		Date expiration = new Date(expireEndTime);

		PolicyConditions policyConds = new PolicyConditions();
		// 默认大小限制10M
		policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, (long)ossProperties.getArgs().getOrDefault("contentLengthRange", 10485760));
		policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, baseDir);

		String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
		byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
		String encodedPolicy = BinaryUtil.toBase64String(binaryData);
		String postSignature = ossClient.calculatePostSignature(postPolicy);

		Map<String, String> respMap = new LinkedHashMap<>(16);
		respMap.put("accessid", ossProperties.getAccessKey());
		respMap.put("policy", encodedPolicy);
		respMap.put("signature", postSignature);
		respMap.put("dir", baseDir);
		respMap.put("host", getOssHost(bucketName));
		respMap.put("expire", String.valueOf(expireEndTime / 1000));
		return JsonUtil.toJsonString(respMap);
	}

	/**
	 * 获取域名
	 *
	 * @param bucketName 存储桶名称
	 * @return String
	 */
	public String getOssHost(String bucketName) {
		String prefix = ossProperties.getEndpoint().contains("https://") ? "https://" : "http://";
		return prefix + getBucketName(bucketName) + "." + ossProperties.getEndpoint().replaceFirst(prefix, "");
	}

	/**
	 * 获取域名
	 *
	 * @return String
	 */
	public String getOssHost() {
		return getOssHost(ossProperties.getBucketName());
	}

}
