package org.egov.digit.expense.calculator;

import org.egov.tracer.config.TracerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@Import({ TracerConfiguration.class })
@SpringBootApplication
@ComponentScan(basePackages = { "org.egov.digit.expense.calculator",
		"org.egov.digit.expense.calculator.web.controllers", "org.egov.digit.expense.calculator.config" })
public class ExpenseCalculatorMain {
	public static void main(String[] args) {
		SpringApplication.run(ExpenseCalculatorMain.class, args);
	}
}
