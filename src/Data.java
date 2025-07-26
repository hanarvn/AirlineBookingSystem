
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Data {
    private static Passangers[] allPassengers = new Passangers[100];
    private static int aptkp = 0;
    private static Flights[] allFlights = new Flights[100];
    private  static int aftkp=0;
    public static Passangers currectPassenger=null;
    
    public static void savePassengersToFile() throws IOException {
        File file=new File("passengers.txt");
        FileWriter fileWriter=new FileWriter(file,false);
        for (int i = 0; i < aptkp; i++) {
        Passangers p=allPassengers[i];
        String booked="";
            for (int j = 0; j < p.getBookedCount(); j++) {
                booked += p.getBookedFlights()[j];
                if (j<p.getBookedCount()-1){
                    booked+=";";
                }
            }
            String line=p.getPhonenumber()+","+p.getPassword()+","+p.getFullName()+","+p.getWallet()+","+booked+"\n";
            fileWriter.write(line);
        }
        fileWriter.close();

    }
    public static void loadPassengersFromFile() throws FileNotFoundException{
        File file=new File("passengers.txt");
        if (!file.exists()){
            return;
        }
        Scanner sc=new Scanner(file);
        aptkp=0;
        while (sc.hasNextLine()){
            String line=sc.nextLine();
            String [] parts=line.split(",");
            Passangers p=new Passangers();
            p.setPhonenumber(parts[0]);
            p.setPassword(parts[1]);
            p.setFullName(parts[2]);
            p.setWallet(Double.parseDouble(parts[3]));
            if (parts.length>4){
                String[] booked=parts[4].split(";");
                for (int i = 0; i < booked.length; i++) {
                    p.addBookedFlight(booked[i]);
                }
            }
            allPassengers[aptkp]=p;
            aptkp++;
        }
        sc.close();
    }

    public static void saveFlightsToFile() throws IOException{
        File file=new File("flights.txt");
        FileWriter fileWriter=new FileWriter(file,false);
        for (int i = 0; i < aftkp; i++) {
        Flights f=allFlights[i];
        fileWriter.write(f.getFlightId()+","+f.getOrigin()+","+f.getDestination()+","+f.getDepartureTime()+","+f.getArrivalTime()+","+f.getCapacity()+","+f.getAvailableSeats()+","+f.getPrice()+"\n");
        }
        fileWriter.close();
    }
    public static void loadFlightsFromFile() throws FileNotFoundException{
        File file=new File("flights.txt");
        if (!file.exists()){
            return;
        }
        Scanner sc=new Scanner(file);
        aftkp=0;
        while (sc.hasNextLine()){
            String line= sc.nextLine();
            String [] parts= line.split(",");
            Flights f=new Flights();
            f.setFlightId(parts[0]);
            f.setOrigin(parts[1]);
            f.setDestination(parts[2]);
            f.setDepartureTime(parts[3]);
            f.setArrivalTime(parts[4]);
            f.setCapacity(Integer.parseInt(parts[5]));
            f.setAvailableSeats(Integer.parseInt(parts[6]));
            f.setPrice(Double.parseDouble(parts[7]));
            allFlights[aftkp]=f;
            aftkp++;
        }
        sc.close();
    }
    
   
    public static boolean loginCheck(String phonenumber, String password) {
        for (int i = 0; i < aptkp; i++) {
            if (allPassengers[i].checkUp(phonenumber, password)) {
                currectPassenger=allPassengers[i];
                return true;
            }
        }
        return false;
    }

    public static boolean createPassengerAcc(String phonenumber, String fullname, String password,double wallet) throws IOException {
        if (aptkp == 100) {
            return false;
        } else {
            Passangers temp = new Passangers();
            temp.setPhonenumber(phonenumber);
            temp.setFullName(fullname);
            temp.setPassword(password);
            temp.setWallet(wallet);
            allPassengers[aptkp] = temp;
            aptkp++;
            savePassengersToFile();
            return true;
        }
    }
    
    public  static String veiwFlights(){
        if (aftkp==0){
            return "No flights are currently available.";

        } else {
            String viewFlightsData="";
            for (int i = 0; i < aftkp; i++) {
                viewFlightsData += +(i+1)+". "+allFlights[i].getFlightId()+" | "+allFlights[i].getOrigin()+" -> "+allFlights[i].getDestination()+" | "+allFlights[i].getDepartureTime()+" | "+allFlights[i].getArrivalTime()+" | "+allFlights[i].getPrice()+"$ | "+allFlights[i].getAvailableSeats()+"/"+allFlights[i].getCapacity()+"\n";
            }
            return viewFlightsData;
        }
    }

    public static boolean adminLogin(String phonenumber,String password){
        return Admin.getPhonenumber().equals(phonenumber) && Admin.getPassword().equals(password);
    }

    public static void addFlight(String flightId,String  origin,String destination,String departureTime,String arrivalTime,int capacity,double price ) throws IOException{
        Flights temp=new Flights();
        temp.setFlightId(flightId);
        temp.setOrigin(origin);
        temp.setDestination(destination);
        temp.setDepartureTime(departureTime);
        temp.setArrivalTime(arrivalTime);
        temp.setCapacity(capacity);
        temp.setPrice(price);
        temp.setAvailableSeats(capacity);
        allFlights[aftkp]=temp;
        aftkp++;
        saveFlightsToFile();
    }

    public static void bookTicket(String flightId) throws IOException{
        boolean flag=true;
        for (int i = 0; i < aftkp; i++) {
            if (allFlights[i].getFlightId().equals(flightId)){
                if (allFlights[i].getAvailableSeats()>0){
                    if (currectPassenger.getWallet()>=allFlights[i].getPrice()){
                        System.out.println("\nTicket booked successfully!");
                        allFlights[i].setAvailableSeats(allFlights[i].getAvailableSeats()-1);
                        currectPassenger.setWallet(currectPassenger.getWallet()-allFlights[i].getPrice());
                        currectPassenger.addBookedFlight(flightId);
                        savePassengersToFile();
                        saveFlightsToFile();
                        flag=false;
                        return;
                    } else {
                        System.out.println("\nYou don't have enough balance.");
                        return;
                    }
                } else {
                    System.out.println("This flight has no capacity.");
                    return;

                }
            }

        }
        if (flag){
            System.out.println("\nNo flight found with this ID");
        }

    }

    public static void cancelTicket(String flightId) throws IOException{
        boolean flag=true;
         if (!currectPassenger.hasBookedFlight(flightId)){
             System.out.println("\nThis flight has not booked by you.");
             return;
         }
       for (int i = 0; i < aftkp; i++) {
        if (allFlights[i].getFlightId().equals(flightId)){
          System.out.println("\nTicked canceled successfully!");
            allFlights[i].setAvailableSeats(allFlights[i].getAvailableSeats()+1);
          currectPassenger.setWallet(currectPassenger.getWallet()+allFlights[i].getPrice());
          savePassengersToFile();
          saveFlightsToFile();
           flag=false;
           return;
        }
       }
       if (flag){
           System.out.println("No flight found with this ID");
       }
     }

    public static void removeFlight(String flightId) throws IOException {
        if (aftkp == 0) {
            System.out.println("\nNo flights are currently available.");
        } else {
            boolean flag = true;
            for (int i = 0; i < aftkp; i++) {
                if (allFlights[i].getFlightId().equals(flightId)) {
                    for (int j = 0; j < aptkp; j++) {
                        if (allPassengers[j].hasBookedFlight(flightId)) {
                            allPassengers[j].setWallet(allPassengers[j].getWallet() + allFlights[i].getPrice());
                        }
                    }
                    System.out.println("\nFlight has been successfully removed.\nFlight ID: " + allFlights[i].getFlightId() + "\nFrom: " + allFlights[i].getOrigin() + "\nTo: " + allFlights[i].getDestination() + "\nDeparture Time: " + allFlights[i].getDepartureTime() + "\nArrival Time: " + allFlights[i].getArrivalTime());
                    allFlights[i] = allFlights[aftkp - 1];
                    aftkp--;
                    saveFlightsToFile();
                    flag = false;
                    break;
                }
            }
            if (flag) {
                System.out.println("\nFlight not found.");
            }
        }

    }

    public static String viewRegisteredPassengers(){
        if (aptkp==0){
            return "No passengers have registered yet.";
        } else {
            String viewPassengers="";
            for (int i = 0; i < aptkp; i++) {
                viewPassengers+="passenger "+(i+1)+" : "+allPassengers[i].getFullName()+"\nPhonenumber: "+allPassengers[i].getPhonenumber()+"\nWallet: "+allPassengers[i].getWallet()+"\n\n";
            }
            return viewPassengers;
        }

    }

    public static void rechargeWallet(double rechargeAmount) throws IOException{
        if (rechargeAmount<=0){
            System.out.println("\nPlease enter a positive number.");
            return;
        }
        currectPassenger.setWallet(currectPassenger.getWallet()+rechargeAmount);
        System.out.println("\nWallet recharged successfully.");
        savePassengersToFile();
    }
}
