package org.egov.digit.expense.calculator;

import org.egov.common.utils.MultiStateInstanceUtil;
import org.egov.tracer.config.TracerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@Import({ MultiStateInstanceUtil.class, TracerConfiguration.class })
@SpringBootApplication
public class ExpenseCalculatorMain {
	public static void main(String[] args) {
		SpringApplication.run(ExpenseCalculatorMain.class, args);
	}
}
