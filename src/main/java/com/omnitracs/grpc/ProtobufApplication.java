package com.omnitracs.grpc;

import java.io.IOException;

import com.omnitracs.grpc.services.BlogService;
import com.omnitracs.grpc.services.CalculatorService;
import com.omnitracs.grpc.services.GreetService;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@SpringBootApplication
@Slf4j
@EnableSwagger2
//@EnableWebMvc
//@EnableConfigurationProperties(value = {AppProperties.class})
//@ConfigurationPropertiesScan
//@EnableAsync
public class ProtobufApplication {//extends SpringBootServletInitializer {

	public static void main(String[] args) throws IOException, InterruptedException {
//		SpringApplication.run(ProtobufApplication.class, args);
		log.info("initializing ProtobufApplication");
		Server server = ServerBuilder.forPort(9090)
				.addService(new GreetService())
				.addService(new BlogService())
				.addService(new CalculatorService())
				.build();
		
		server.start();
		log.info("ProtobufApplication started");
		
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			log.info("Received Shutdown Request");
			server.shutdown();
			log.info("Successfully stopped the server");
		}));
		
		
		server.awaitTermination();
		log.info("ProtobufApplication finished");
	}
	

//	@Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//	    System.setProperty("spring.devtools.restart.enabled", "true");
//        return application.sources(ProtobufApplication.class);
//    }

}
