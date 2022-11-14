package com.example.venus.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.example.venus.Planeta;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
public class PropriedadesController {
	
	private static final Double MASSA_VENUS = 4.867 * Math.pow(10, 24);
	private static final Double GRAVIDADE_VENUS = 8.87;
	
	Planeta planeta;
	
	@GetMapping("/massa")
	@ResponseBody
	public ResponseEntity<Double> massaVenus() {
		return new ResponseEntity<>(MASSA_VENUS, HttpStatus.OK);
	}
	
	@PostMapping("/pesoCorporalVenus") 
	public ResponseEntity<Object> pesoVenus(@RequestBody String json) {	
		ObjectMapper mapper = new ObjectMapper();
		try {
			planeta = mapper.readValue(json, Planeta.class);
			double pesoVenus = planeta.getPeso() * GRAVIDADE_VENUS;
			return new ResponseEntity<Object>(pesoVenus, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}
	
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Mensagem do Kanye chamada com sucesso"),
		    @ApiResponse(code = 401, message = "Assessor não quer que fale"),
		    @ApiResponse(code = 403, message = "Alguém não quer que Kanye fale"),
		    @ApiResponse(code = 404, message = "Kanye sumiu"),
		    @ApiResponse(code = 500, message = "Kanye foi cancelado"),
		})
	@GetMapping(value = "/callKanye", produces="application/json", consumes="application/json")
	public ResponseEntity<String> getInfoMarte() {
		String uri = "https://api.kanye.rest";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("user-agent", "Application");
		HttpEntity<String> entity = new HttpEntity("body", headers);
		restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
		ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
		return result;
	}


}
