package ca.ulaval.glo4002.stocks;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@Import({SpringDataRestConfiguration.class})
@EntityScan(
        basePackageClasses = {StocksSpringApplication.class, Jsr310JpaConverters.class}
)
@EnableSwagger2
public class StocksSpringApplication {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ca.ulaval.glo4002.stocks.controllers"))
                .paths(documentedPaths())
                .build()
                .apiInfo(getApiInfo());
    }

    private Predicate<String> documentedPaths() {
        return Predicates.not(PathSelectors.regex("/(error|profile).*"));
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .contact(new Contact("The GLO-4002 team", "http://projet2018.qualitelogicielle.ca",
                        "aide@qualitelogicielle.ca"))
                .title("Stocks API")
                .description("Stocks API")
                .version("1.0")
                .build();
    }
}
