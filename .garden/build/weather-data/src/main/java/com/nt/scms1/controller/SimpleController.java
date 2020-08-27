package com.nt.scms1.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator; 
import java.util.Map;
import java.util.Random;

import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/nt-ms1")
public class SimpleController {
	
	private static final MapperFeature ALLOW_COMMENTS = null;
	private static final boolean TRUE = true;
	@Value("classpath:data/weatherdata.json")
	Resource resourceFile;

	@GetMapping(value = "/get-data")
	public Mono<String> getData(ServerHttpRequest request, ServerHttpResponse response) {
		System.out.println("Inside SC-MS1 getData method");
		HttpHeaders headers = request.getHeaders();
		
		headers.forEach((k,v)->{
			System.out.println(k + " : " + v);
		});
		
		ResponseCookie.ResponseCookieBuilder builder = ResponseCookie.from("nt-ms1-cookie", "ntMs1CookieValue");
		ResponseCookie cookie = builder.build();
		response.addCookie(cookie);
		ObjectMapper objectMapper = new ObjectMapper();
		//objectMapper.configure(ALLOW_COMMENTS, TRUE);
		
		
		
		//String json = "{ \"color\" : \"Black\", \"type\" : \"FIAT\" }";
		
		JsonNode jsonNode = null;
		try {
			//Resource resource = new ClassPathResource("weatherdata.json");
			File file = resourceFile.getFile();
		
		       jsonNode = objectMapper.readTree(file);
		
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Random rand = new Random();
		
		int sec= rand.nextInt(40);
		String time = jsonNode.get(sec).get("datetime").asText();
		String vancouver = jsonNode.get(sec).get("Vancouver").asText();
		
		Mono<String> data = Mono.just("Weather Data"+time+" "+vancouver );
		return data;
	}
}