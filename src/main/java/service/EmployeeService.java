package service;

import io.grpc.stub.StreamObserver;
import proto.EmployeeDetailsGrpc;
import proto.EmployeeDetailsOuterClass;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EmployeeService extends EmployeeDetailsGrpc.EmployeeDetailsImplBase {

    private final Map<String, EmployeeDetailsOuterClass.Employee> employeeMap = new HashMap<>();

    public void setEmployeeDetails(EmployeeDetailsOuterClass.Employee request, StreamObserver<EmployeeDetailsOuterClass.EmployeeID> responseObserver) {
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();
        employeeMap.put(uuidString, request);
        EmployeeDetailsOuterClass.EmployeeID id = EmployeeDetailsOuterClass.EmployeeID.newBuilder().setId(uuidString).build();
        responseObserver.onNext(id);
        responseObserver.onCompleted();
    }

    public void getEmployeeDetails(EmployeeDetailsOuterClass.EmployeeID request, StreamObserver<EmployeeDetailsOuterClass.Employee> responseObserver) {
        String id = request.getId();
        System.out.println("ID:"+id);
        if (employeeMap.containsKey(id)){
           responseObserver.onNext((EmployeeDetailsOuterClass.Employee) employeeMap.get(id));
           responseObserver.onCompleted();
        }
        else{
            responseObserver.onError(new Exception("No such employee exists"));
        }
    }
}
