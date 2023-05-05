package org.egov.digit.expense;

import org.egov.tracer.config.TracerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@Import({ TracerConfiguration.class })
@SpringBootApplication
@ComponentScan(basePackages = { "org.egov.digit.expense", "org.egov.digit.expense.web.controllers",
		"org.egov.digit.expense.config" })
public class ExpenseApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ExpenseApplication.class, args);
	}

}
