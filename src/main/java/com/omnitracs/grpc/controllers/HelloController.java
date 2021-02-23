package com.omnitracs.grpc.controllers;

import org.springframework.beans.factory.annotation.Value;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@RestController
public class HelloController {

//	@Autowired
//	private BuildProperties buildProperties;
	@Value("${spring.application.buildtime}")
	private String buildTime;
	@Value("${spring.application.comment}")
	private String[] comment;
//	@Autowired
//	private Environment environment;
//	@Value("${SPRING_PROFILE:#{environment.SPRING_PROFILE}}")
//	private String dbEnv;

//	@GetMapping("/hello")
//	public String greeting() {
//		return "{"
//				+ "\"buildTime\":" + "\"" + buildTime + "\""
//				+ ", \"buildProperties\":" + "\"name:" + 
//				// Artifact's name from the pom.xml file
//				buildProperties.getName() + ", version:" +
//				// Artifact version
//				buildProperties.getVersion() + ", time:" +
//				// Date and Time of the build
//				buildProperties.getTime() + ", artifact:" +
//				// Artifact ID from the pom file
//				buildProperties.getArtifact() + 
//				", dbenv: " + (dbEnv != null ? dbEnv : "unk")
//				+ ", group:" +
//				// Group ID from the pom file
//				buildProperties.getGroup() + "\","
//				+ "\"profile\":" + "\"" + (environment != null ?
//						Arrays.stream(environment.getActiveProfiles()).collect(Collectors.toList()) 
//						: "null") + "\""
//						+ ", \"comment\": \"" + (comment != null ?
//								Arrays.stream(comment).collect(Collectors.toList())
//								: "null") + "\""
//						+ "}";
//	}
	
}