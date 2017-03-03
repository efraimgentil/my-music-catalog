package me.efraimgentil.mymusic;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;

@SpringBootApplication
@ComponentScan
public class CatalogApplication {

	@Bean
	public Jackson2ObjectMapperBuilder jacksonBuilder() {
		return  new Jackson2ObjectMapperBuilder()
				.failOnUnknownProperties(false)
				.serializationInclusion(JsonInclude.Include.NON_EMPTY)
				.serializerByType(Page.class, new JsonPageSerializer());

	}

	public class JsonPageSerializer extends JsonSerializer<Page> {

		@Override
		public void serialize(Page page, JsonGenerator jsonGen, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {

			ObjectMapper om=new ObjectMapper()
					.disable(MapperFeature.DEFAULT_VIEW_INCLUSION)
					.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
			jsonGen.writeStartObject();
			jsonGen.writeFieldName("size");
			jsonGen.writeNumber(page.getSize());
			jsonGen.writeFieldName("number");
			jsonGen.writeNumber(page.getNumber());
			jsonGen.writeFieldName("totalElements");
			jsonGen.writeNumber(page.getTotalElements());
			jsonGen.writeFieldName("last");
			jsonGen.writeBoolean(page.isLast());
			jsonGen.writeFieldName("totalPages");
			jsonGen.writeNumber(page.getTotalPages());
			jsonGen.writeObjectField("sort", page.getSort());
			jsonGen.writeFieldName("first");
			jsonGen.writeBoolean(page.isFirst());
			jsonGen.writeFieldName("numberOfElements");
			jsonGen.writeNumber(page.getNumberOfElements());
			jsonGen.writeFieldName("content");
			jsonGen.writeRawValue(om.writerWithView(serializerProvider.getActiveView())
					.writeValueAsString(page.getContent()));
			jsonGen.writeEndObject();
		}

	}

	public static void main(String[] args) {
		SpringApplication.run(CatalogApplication.class, args);
	}
}
