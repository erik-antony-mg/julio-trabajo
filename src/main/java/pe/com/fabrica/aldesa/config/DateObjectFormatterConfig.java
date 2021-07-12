package pe.com.fabrica.aldesa.config;

import java.util.Calendar;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Muestra los valores de Fecha (fecha/hora) y de objetos de la clase {@link Calendar} serializados como marcas de tiempo numéricas.
 *
 * @author Anthony Lopez
 *
 */
@Configuration
public class DateObjectFormatterConfig {

	@Bean
	@Primary
	public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
		ObjectMapper objectMapper = builder.build();
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
		return objectMapper;
	}

}
