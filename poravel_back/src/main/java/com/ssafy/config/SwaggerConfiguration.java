package com.ssafy.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {

//	Swagger-UI 3.x 확인
//	{root}/swagger-ui/index.html
	
	private final ServerProperties serverProperties;

    public SwaggerConfiguration(ServerProperties serverProperties) {
		super();
		this.serverProperties = serverProperties;
	}
	
    @Bean
    public Docket allApi() {
        return new Docket(DocumentationType.OAS_30)
        	.consumes(getConsumeContentTypes())
        	.produces(getProduceContentTypes())
            .groupName("0.전체API")
            .apiInfo(allApiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.ssafy"))
            .paths(PathSelectors.ant("/**"))
            .build()
            .useDefaultResponseMessages(false);
    }
    
    @Bean
    public Docket userApi() {
        return new Docket(DocumentationType.OAS_30)
        	.consumes(getConsumeContentTypes())
            .produces(getProduceContentTypes())
            .groupName("회원API")		// 1. 그룹이름
            .apiInfo(userApiInfo())		// 2.
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.ssafy.user.controller"))	// 3.
//            .paths(PathSelectors.ant("/user/**"))
            .build()
            .useDefaultResponseMessages(false);
    }
    
    @Bean
    public Docket boardApi() {
        return new Docket(DocumentationType.OAS_30)
        	.consumes(getConsumeContentTypes())
            .produces(getProduceContentTypes())
            .groupName("게시판API")
            .apiInfo(boardApiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.ssafy.board.controller"))
//            .paths(PathSelectors.ant("/board/**"))
            .build()
            .useDefaultResponseMessages(false);
    }
    
    
    private ApiInfo allApiInfo() {
        Integer port = serverProperties.getPort();
        return new ApiInfoBuilder()
            .title("SSAFY Swagger Open API Docs")
            .description(
                "<h3>SSAFY API Reference for Developers</h3>Vuejs를 위한 API<br>")
            .version("1.0")
            .license("SSAFY License")
            .licenseUrl("https://www.ssafy.com/ksp/jsp/swp/etc/swpPrivacy.jsp")
            .termsOfServiceUrl("http://edu.ssafy.com")
            .contact(new Contact("SSAFY", "https://www.ssafy.com", "ssafy@ssafy.com"))
            .build();
    }

    private ApiInfo userApiInfo() {
        return new ApiInfoBuilder()
            .title("회원관리")
            .description("<h2>회원관리 API</h2>")
            .version("1.0")
            .license("SSAFY License")
            .licenseUrl("https://www.ssafy.com/ksp/jsp/swp/etc/swpPrivacy.jsp")
            .termsOfServiceUrl("http://edu.ssafy.com")
            .contact(new Contact("SSAFY", "https://www.ssafy.com", "ssafy@ssafy.com"))
            .build();
    }
    
    private ApiInfo boardApiInfo() {
        return new ApiInfoBuilder()
            .title("게시판")
            .description("<h2>게시판 API</h2>")
            .version("1.0")
            .license("SSAFY License")
            .licenseUrl("https://www.ssafy.com/ksp/jsp/swp/etc/swpPrivacy.jsp")
            .termsOfServiceUrl("http://edu.ssafy.com")
            .contact(new Contact("SSAFY", "https://www.ssafy.com", "ssafy@ssafy.com"))
            .build();
    }
    

	private Set<String> getConsumeContentTypes() {
        Set<String> consumes = new HashSet<>();
        consumes.add("application/json;charset=UTF-8");
//      consumes.add("application/xml;charset=UTF-8");
        consumes.add("application/x-www-form-urlencoded");
        return consumes;
    }

    private Set<String> getProduceContentTypes() {
        Set<String> produces = new HashSet<>();
        produces.add("application/json;charset=UTF-8");
        return produces;
    }

}
