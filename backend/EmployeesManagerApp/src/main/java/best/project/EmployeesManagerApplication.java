package best.project;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import best.project.helpers.TablesPopulator;
import best.project.repositories.DepartmentsRepository;
import best.project.repositories.EmployeesRepository;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class EmployeesManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeesManagerApplication.class, args);
	}

	@Bean
	public CommandLineRunner populateTables(DepartmentsRepository departmentsRepository,
			EmployeesRepository employeesRepository) {
		return args -> {

			TablesPopulator.loadInitialDataToDB(departmentsRepository, employeesRepository);

		};
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.ant("/api/**")).build().apiInfo(metaData());
	}

	private ApiInfo metaData() {
		return new ApiInfoBuilder().title("Spring Boot REST API")
				.description("\"Spring Boot REST API for Employees Manager\"").version("1.0.0")
				.license("Apache License Version 2.0").licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
				.build();
	}
}
