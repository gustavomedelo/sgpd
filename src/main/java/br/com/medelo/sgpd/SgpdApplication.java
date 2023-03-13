package br.com.medelo.sgpd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SgpdApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SgpdApplication.class, args);
	}
}
