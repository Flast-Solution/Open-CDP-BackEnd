package vn.flast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "vn.flast")
@PropertySource(value = {"file:conf/application.properties","file:conf/custom.properties"})
@EnableAutoConfiguration(exclude = { HibernateJpaAutoConfiguration.class })
@EnableJpaRepositories("vn.flast.repositories")
public class CdpStart {
	public static void main(String[] args) {
		SpringApplication.run(CdpStart.class, args);
	}
}
