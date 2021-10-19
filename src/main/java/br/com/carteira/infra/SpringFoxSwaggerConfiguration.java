package br.com.carteira.infra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxSwaggerConfiguration {

	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any())                          
          .build()
          .apiInfo(apiInfo());                                           
    }

	@SuppressWarnings("deprecation")
	private ApiInfo apiInfo() {
		return new ApiInfo("API Carteira de Investimentos", 
				"REST API criada durante a imersão Alura",
				"1.0",
				null,"Luiz Costa", 
				null, null);
	}
	
}
