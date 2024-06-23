package com.varshitrajput.employeeDetails;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import service.EmployeeService;

import java.io.IOException;

public class EmployeeDetailsServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 50051;
        Server server = ServerBuilder.forPort(port).addService(new EmployeeService()).build().start();
        System.out.println("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("shutting down gRPC server since JVM is shutting down");
            if (server != null){
                server.shutdown();
            }
            System.err.println("server shut down");
        }));
        server.awaitTermination();
    }
}
