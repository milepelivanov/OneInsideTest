import java.util.ArrayList;
import java.util.List;


abstract class ParkingRequest {
    protected Integer spaceID;
    protected String plateNumber;
    protected Integer totalCostService;

    public ParkingRequest(Integer spaceID, String plateNumber) {
        this.spaceID = spaceID;
        this.plateNumber = plateNumber;
        this.totalCostService = 0;
    }

    public Integer getSpaceID() {
        return this.spaceID;
    }

    abstract Integer calculateTotalCostParkingRequest();

}

class Prepaid extends ParkingRequest {
    private Type type;

    public Prepaid(Integer spaceID, String plateNumber, Type type) {
        super(spaceID, plateNumber);
        this.type = type;
    }

    @Override
    Integer calculateTotalCostParkingRequest() {
        if (this.type.equals(Type.Month)) {
            return 3000;
        } else {
            return 35000;
        }
    }
}


class Hourly extends ParkingRequest {
    private Integer totalHours;

    public Hourly(Integer spaceID, String plateNumber, Integer totalHours) {
        super(spaceID, plateNumber);
        this.totalHours = totalHours;
    }

    @Override
    Integer calculateTotalCostParkingRequest() {
        return 35 * totalHours;
    }
}

class ParkingSpace {

    private Integer spaceID;
    private boolean status;
    private Integer totalEarningFromParkingSpace;
    private List<ParkingRequest> requests;

    public Integer getSpaceID() {
        return this.spaceID;
    }

    public ParkingSpace(Integer spaceID) {
        this.spaceID = spaceID;
        this.status = true;
        this.totalEarningFromParkingSpace = 0;
        this.requests = new ArrayList<>();
    }

    public boolean checkAvailability() {
        if (this.status) {
            return true;
        }
        return false;
    }

    public void changeAvailability() {
        if (this.status) {
            this.status = false;
        } else {
            this.status = true;
        }
    }


    public Integer calculateTotalEarningsFromParkingSpace() {
        Integer sum = 0;
        for (ParkingRequest req : requests) {
            sum += req.calculateTotalCostParkingRequest();
        }
        return sum;
    }

    public void createRequest(ParkingRequest request) {
        this.requests.add(request);
        changeAvailability();
    }

    public void deleteRequest(ParkingRequest request) {
        for (ParkingRequest request1 : requests) {
            if (request1.spaceID == request.spaceID) {
                requests.remove(request);
                changeAvailability();
                return;
            }
        }
    }
}


class ParkingLot {
    private List<ParkingSpace> spaces;
    private Integer total;

    public ParkingLot() {
        this.spaces = new ArrayList<>();
        this.total = 0;
    }

    public Integer calculateTotal() {
        Integer sum = 0;
        for (ParkingSpace parkingSpace : spaces) {
            System.out.println("Parking Space with ID " + parkingSpace.getSpaceID() + " - " + parkingSpace.calculateTotalEarningsFromParkingSpace());
            sum += parkingSpace.calculateTotalEarningsFromParkingSpace();
        }
        return sum;
    }

    public void createParkingSpace(ParkingSpace parkingSpace) {
        for (ParkingSpace pSpace : spaces) {
            if (pSpace.getSpaceID() == parkingSpace.getSpaceID()) {
                System.out.println("Parking Space Already Exists");
                return;
            }
        }
        spaces.add(parkingSpace);
    }

    public void createParkingRequest(ParkingRequest request) {

        for (ParkingSpace space1 : spaces) {
            if (space1.getSpaceID() == request.getSpaceID()) {
                if (space1.checkAvailability()) {
                    space1.createRequest(request);
                    return;
                } else {
                    System.out.println("THE PARKING SPOT IS NOT AVAILABLE");
                    return;
                }
            }
        }
        System.out.println("THE PARKING SPOT ID IS WRONG");

    }

    public void deleteParkingRequest(ParkingRequest parkingRequest) {
        for (ParkingSpace space : spaces) {
            if (space.getSpaceID() == parkingRequest.spaceID) {
                space.deleteRequest(parkingRequest);
                return;
            }
        }
    }

    public void parkingSpotNowFree(ParkingSpace parkingSpace) {
        parkingSpace.changeAvailability();
    }
}

public class Main {
    public static void main(String[] args) {
        ParkingSpace parkingSpace1 = new ParkingSpace(1);
        ParkingSpace parkingSpace2 = new ParkingSpace(2);
        ParkingSpace parkingSpace3 = new ParkingSpace(3);


        Hourly request1 = new Hourly(1, "ASDASD", 23);
        Prepaid request2 = new Prepaid(2, "ASDASD", Type.Month);
        Prepaid request3 = new Prepaid(3, "ASDASD", Type.Year);


        ParkingLot parkingLot = new ParkingLot();

        // PRESMETUVANJE NA TOTAL ZARABOTKA NA PARKING LOT PRI
        // ISPRAKJANJE PO EDNO PARKING REQUEST DO SITE PARKING SPACE
        System.out.println("--------------------");
        System.out.println("Scenario 1");
        parkingLot.createParkingSpace(parkingSpace1);
        parkingLot.createParkingSpace(parkingSpace2);
        parkingLot.createParkingSpace(parkingSpace3);

        parkingLot.createParkingRequest(request1);
        parkingLot.createParkingRequest(request2);
        parkingLot.createParkingRequest(request3);

        System.out.println(parkingLot.calculateTotal());
        System.out.println("--------------------");


        //PRESMETUVANJE NA TOTAL ZARABOTKA NA PARKING LOT PRI
        // ISPRAKANJE NA PARKING REQUEST NA VEKJE ZAFATENO PARKING SPACE
        System.out.println("Scenario 2");
        parkingSpace1 = new ParkingSpace(1);
        request1 = new Hourly(1, "ASDASD", 23);
        ParkingLot parkingLot2 = new ParkingLot();

        parkingLot2.createParkingSpace(parkingSpace1);
        parkingLot2.createParkingRequest(request1);
        parkingLot2.createParkingRequest(request1);
        System.out.println(parkingLot2.calculateTotal());
        System.out.println("--------------------");

        //PRESMETUVANJE NA TOTAL ZARABOTKA NA PARKING LOT
        // PIR ISPRAKANJE NA POVEKJE PARKING REQUESTS KON EDNO PARKING SPACE,
        // ODKOGA KE SE OSLOBODI TOA PARKING SPACE - STATUS = FREE
        System.out.println("Scenario 3");
        parkingSpace1 = new ParkingSpace(1);
        request1 = new Hourly(1, "ASDASD", 23);
        ParkingLot parkingLot3 = new ParkingLot();

        parkingLot3.createParkingSpace(parkingSpace1);
        parkingLot3.createParkingRequest(request1);
        parkingLot3.parkingSpotNowFree(parkingSpace1);
        parkingLot3.createParkingRequest(request1);
        parkingLot3.parkingSpotNowFree(parkingSpace1);
        request2 = new Prepaid(1, "ASDASD", Type.Month);
        parkingLot3.createParkingRequest(request2);
        System.out.println(parkingLot3.calculateTotal());
        System.out.println("--------------------");


    }
}