package com.rest.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@SpringBootApplication
@RestController
public class RestApiApplication {

	private HashMap<Integer, String> database = new HashMap<>();

	public static void main(String[] args) {
		SpringApplication.run(RestApiApplication.class, args);
	}

	@PostMapping
	public String postData(@RequestBody String body){

		database.put(database.size(), body);
		return body;

	}

	@GetMapping("{id}")
	public String getData(@PathVariable("id") int id) {
		return database.get(id);
	}

	@DeleteMapping("/delete")
	public String deleteData(@RequestParam int id) {
		return database.remove(id);
	}

	@PutMapping("/{id}")
	public String updateData(@PathVariable int id, @RequestBody String body) {
		return database.put(id, body);
	}

}
