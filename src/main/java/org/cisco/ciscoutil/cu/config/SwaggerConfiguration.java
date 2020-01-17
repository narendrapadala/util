package org.cisco.ciscoutil.cu.config;

import org.cisco.ciscoutil.cu.util.CommonUtilProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile(value= {"local", "dev", "staging"})
@Import(CommonUtilProperties.class)
public class SwaggerConfiguration {
	
	@Autowired
	private CommonUtilProperties properties;
	
	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("Module").apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage(properties.getBasePackageName())).paths(PathSelectors.any()).build();

	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Module").description("Module API reference for developers").build();
	}


}
