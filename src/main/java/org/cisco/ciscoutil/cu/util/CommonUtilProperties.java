package org.cisco.ciscoutil.cu.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.regions.Regions;

@Component
public class CommonUtilProperties {
	@Value("${java.melody.user.name}")
	private String javaMelodyUserName;
	
	@Value("${java.melody.user.password}")
	private String javaMelodyPassword;
	
	@Value("${rest.template.read.timeout:30}")
	private Integer readTimeout;
	
	@Value("${rest.template.connection.timeout:30}")
	private Integer connectionTimeout;
	
	@Value("${rest.template.connection.request.timeout:20}")
	private Integer connectionRequestTimeout;
	
	@Value("${base.package.name}")
	private String basePackageName;
	
	@Value("${common.filter.auth-token}")
	private String authToken;
	
	@Value("${common.filter.external.services.auth-token}")
	private String externalServicesAuthToken;
	
	@Value("${detailed.logging.enabled:false}")
	private Boolean detailedLoggingEnabled;
	
	@Value("${common.exception.handler.disabled:false}")
	private Boolean isCommonExceptionHandlerDisabled;
	
	@Value("${common.thread.pool.enabled:false}")
	private Boolean isCommonThreadPoolEnabled;
	
	@Value("${thread.pool.core.size:50}")
	private Integer threadPoolCoreSize;

	@Value("${thread.pool.max.size:50}")
	private Integer threadPoolMaxSize;
	
	@Value("${max.image.height:1080}")
	private Integer maxImageHeight;
	
	@Value("${max.image.width:1920}")
	private Integer maxImageWidth;
	
	@Value("${aws.file.upload.enabled:false}")
	private Boolean awsFileUploadEnabled;
	
	@Value("${aws.s3.host.url:}")
	private String awsS3HostUrl;
	
	@Value("${aws.s3.access.key:}")
	private String awsS3AccessKey;
	
	@Value("${aws.s3.secret.key:}")
	private String awsS3SecretKey;
	
	@Value("${aws.s3.region.name:}")
	private Regions awsS3RegionName;
	
	@Value("${is.error.msg.from.db.enabled:false}")
	private Boolean isErrorMsgFromDbEnabled;
	
	@Value("${error.code.prefix:}")
	private String errorCodePrefix;

	public String getJavaMelodyUserName() {
		return javaMelodyUserName;
	}

	public String getJavaMelodyPassword() {
		return javaMelodyPassword;
	}

	public Integer getReadTimeout() {
		return readTimeout;
	}

	public Integer getConnectionTimeout() {
		return connectionTimeout;
	}

	public Integer getConnectionRequestTimeout() {
		return connectionRequestTimeout;
	}

	public String getBasePackageName() {
		return basePackageName;
	}

	public String getAuthToken() {
		return authToken;
	}
	
	public String getExternalServicesAuthToken() {
		return externalServicesAuthToken;
	}

	public Boolean getDetailedLoggingEnabled() {
		return detailedLoggingEnabled;
	}

	public Boolean getIsCommonExceptionHandlerDisabled() {
		return isCommonExceptionHandlerDisabled;
	}
	
	public Boolean getIsCommonThreadPoolEnabled() {
		return isCommonThreadPoolEnabled;
	}
	
	public Integer getThreadPoolCoreSize() {
		return threadPoolCoreSize;
	}

	public Integer getThreadPoolMaxSize() {
		return threadPoolMaxSize;
	}

	public Integer getMaxImageHeight() {
		return maxImageHeight;
	}

	public Integer getMaxImageWidth() {
		return maxImageWidth;
	}
	
	public Boolean getAwsFileUploadEnabled() {
		return awsFileUploadEnabled;
	}

	public String getAwsS3HostUrl() {
		return awsS3HostUrl;
	}
	
	public String getAwsS3AccessKey() {
		return awsS3AccessKey;
	}

	public String getAwsS3SecretKey() {
		return awsS3SecretKey;
	}
	
	public Regions getAwsS3RegionName() {
		return awsS3RegionName;
	}
	
	public Boolean getIsErrorMsgFromDbEnabled() {
		return isErrorMsgFromDbEnabled;
	}

	public String getErrorCodePrefix() {
		return errorCodePrefix;
	}

}
