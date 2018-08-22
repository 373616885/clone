package com.qin.common;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

@Configuration
public class JsonConfig {

	@Bean
	public HttpMessageConverters fastJsonHttpMessageConverters() {
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		fastConverter.setFastJsonConfig(fastJsonConfig);
		List<MediaType> mediaType = new ArrayList<>();
		mediaType.add(MediaType.APPLICATION_JSON_UTF8);
		fastConverter.setSupportedMediaTypes(mediaType);
		HttpMessageConverter<?> converter = fastConverter;
		return new HttpMessageConverters(converter);
	}

}
