package vn.flast.utils;

import java.util.*;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Slf4j
public class JsonUtils {

	public static <T> T Json2Object(String json, Class<T> type) {
		try {
			return new ObjectMapper()
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				.readValue(json, type);
		} catch (JsonProcessingException e) {
			return null;
		}
	}

	public static <T> List<T> Json2ListObject(String json, Class<T> type) {
		List<T> list = new ArrayList<>();
		if (!StringUtils.hasText(json)) {
			return list;
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			JavaType jt = mapper
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				.getTypeFactory().constructCollectionType(List.class, type);
			list.addAll(mapper.readValue(json, jt));
		} catch (Exception e) {
			log.error("Json to List Object Error: {}", e.getMessage());
		}
		return list;
	}

	public static String toJson(Object object) {
		if (object == null) {
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			return null;
		}
	}
}
