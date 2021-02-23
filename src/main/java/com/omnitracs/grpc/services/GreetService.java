package com.omnitracs.grpc.services;

import com.omnitracs.proto.greet.GreetEveryoneRequest;
import com.omnitracs.proto.greet.GreetEveryoneResponse;
import com.omnitracs.proto.greet.GreetManyTimesRequest;
import com.omnitracs.proto.greet.GreetManyTimesResponse;
import com.omnitracs.proto.greet.GreetRequest;
import com.omnitracs.proto.greet.GreetResponse;
import com.omnitracs.proto.greet.GreetServiceGrpc.GreetServiceImplBase;
import com.omnitracs.proto.greet.GreetWithDeadlineRequest;
import com.omnitracs.proto.greet.GreetWithDeadlineResponse;
import com.omnitracs.proto.greet.Greeting;
import com.omnitracs.proto.greet.LongGreetRequest;
import com.omnitracs.proto.greet.LongGreetResponse;

import io.grpc.Context;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "greetingService")
public class GreetService extends GreetServiceImplBase{

	@Override
	public void greet(GreetRequest request, StreamObserver<GreetResponse> responseObserver) {
//		super.greet(request, responseObserver);
		Greeting greeting = request.getGreeting();
		GreetResponse value = GreetResponse.newBuilder()
				.setResult("Hello " + greeting.getFirstName())
				.build();
		log.info("Request received " + greeting.toString());
		responseObserver.onNext(value);
		responseObserver.onCompleted();
	}
	

    @Override
    public void greetManyTimes(GreetManyTimesRequest request, StreamObserver<GreetManyTimesResponse> responseObserver) {
        String firstName = request.getGreeting().getFirstName();

        try {
            for (int i = 0; i < 10; i++) {
                String result = "Hello " + firstName + ", response number: " + i;
                GreetManyTimesResponse response = GreetManyTimesResponse.newBuilder()
                        .setResult(result)
                        .build();

                responseObserver.onNext(response);
                Thread.sleep(1000L);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            responseObserver.onCompleted();
        }
    }

    @Override
    public StreamObserver<LongGreetRequest> longGreet(StreamObserver<LongGreetResponse> responseObserver) {
        // we create the requestObserver that we'll return in this function
        StreamObserver<LongGreetRequest> requestObserver = new StreamObserver<LongGreetRequest>() {

            String result = "";

            @Override
            public void onNext(LongGreetRequest value) {
                // client sends a message
                result += "Hello " + value.getGreeting().getFirstName() + "! ";
            }

            @Override
            public void onError(Throwable t) {
                // client sends an error
            }

            @Override
            public void onCompleted() {
                // client is done
                responseObserver.onNext(
                        LongGreetResponse.newBuilder()
                                .setResult(result)
                                .build()
                );
                responseObserver.onCompleted();
            }
        };


        return requestObserver;
    }

    @Override
    public StreamObserver<GreetEveryoneRequest> greetEveryone(StreamObserver<GreetEveryoneResponse> responseObserver) {
        StreamObserver<GreetEveryoneRequest> requestObserver = new StreamObserver<GreetEveryoneRequest>() {
            @Override
            public void onNext(GreetEveryoneRequest value) {
                String result = "Hello " + value.getGreeting().getFirstName();
                GreetEveryoneResponse greetEveryoneResponse = GreetEveryoneResponse.newBuilder()
                        .setResult(result)
                        .build();

                responseObserver.onNext(greetEveryoneResponse);
            }

            @Override
            public void onError(Throwable t) {
                // do nothing
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };

        return requestObserver;
    }

    @Override
    public void greetWithDeadline(GreetWithDeadlineRequest request, StreamObserver<GreetWithDeadlineResponse> responseObserver) {

        Context current = Context.current();

        try {

            for (int i = 0; i < 3; i++) {
                if (!current.isCancelled()) {
                    System.out.println("sleep for 100 ms");
                    Thread.sleep(100);
                } else {
                    return;
                }
            }

            System.out.println("send response");
            responseObserver.onNext(
                    GreetWithDeadlineResponse.newBuilder()
                            .setResult("hello " + request.getGreeting().getFirstName())
                            .build()
            );

            responseObserver.onCompleted();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
