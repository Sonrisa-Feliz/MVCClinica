package com.ClinicaOdontologica.UP;

import com.ClinicaOdontologica.UP.dao.BD;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UpApplication {

	public static void main(String[] args) {
        BD.crearTablas();
		SpringApplication.run(UpApplication.class, args);
	}

}
