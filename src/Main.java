import java.io.IOException;
import java.util.Scanner;
public class Main {

	public static void main(String[] args) throws IOException {
		Data.loadPassengersFromFile();
		Data.loadFlightsFromFile();
		
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to the Airline Ticketing System\n1-> Log in\n2-> Create an account\n3-> Admin Panel\n4-> Exist");
            System.out.print("Enter your choice: ");
            if (scanner.hasNextInt()) {
                int listNumber = scanner.nextInt();
                if (listNumber > 0 && listNumber < 5) {
                    if (listNumber == 1) {
                        System.out.print("Enter your phonenumber: ");
                        String ph = scanner.next();
                        System.out.print("Enter your password: ");
                        String p = scanner.next();
                        if (Data.loginCheck(ph, p)) {
                            System.out.println("\nWelcome " + Data.currectPassenger.getFullName());
                            boolean loggedIn = true;
                            while (loggedIn) {
                                System.out.println("\n---Passangers Menu---\n1-> View available flights\n2-> Book a ticket\n3-> Cancel a ticket\n4-> View wallet balance\n5-> Recharge wallet\n6-> Logout");
                                System.out.print("Enter your choice: ");
                                int menuNumber = scanner.nextInt();
                                if (menuNumber == 1) {
                                    System.out.println("\n"+Data.veiwFlights());
                                } else if (menuNumber == 2) {
                                    System.out.println("\n"+Data.veiwFlights());
                                    System.out.print("Enter flight ID you want to book: ");
                                    String bTicketNumber = scanner.next();
                                    Data.bookTicket(bTicketNumber);
                                } else if (menuNumber == 3) {
                                    System.out.print("Enter flight ID you want to cancel: ");
                                    String cTicketNumber=scanner.next();
                                    Data.cancelTicket(cTicketNumber);
                                } else if (menuNumber == 4) {
                                    System.out.println("\n" + Data.currectPassenger.getWallet() + "$");
                                } else if(menuNumber==5){
                                	System.out.print("Enter the amount you want to recharge: ");
                                    Double walletNumber=scanner.nextDouble();
                                    Data.rechargeWallet(walletNumber);
                                }  else if (menuNumber == 6) {
                                	Data.savePassengersToFile();
                                    loggedIn = false;
                                    System.out.println("\n");
                                } else {
                                    System.out.println("\nNumber must be beetween 1 and 5");
                                }

                            }
                        } else {
                            System.out.println("\nPhonenumber and/or password are/is incorrect\n");
                        }

                    } else if (listNumber == 2) {
                        System.out.print("Enter your phonenumber: ");
                        String newPhonenumber = scanner.next();
                        scanner.nextLine();
                        System.out.print("Enter your fullname: ");
                        String newFullname = scanner.nextLine();
                        System.out.print("Enter your password: ");
                        String newPassword = scanner.next();
                        System.out.print("Enter wallet amount: ");
                        double newWallet = scanner.nextDouble();
                        if (Data.createPassengerAcc(newPhonenumber, newFullname, newPassword, newWallet)) {
                            System.out.println("\nYour account has been created successfully!\nPlease log in to continue.\n");
                        } else {
                            System.out.println("Memory full!");
                        }
                    } else if (listNumber==3) {
                        System.out.print("Enter your phonenumber: ");
                        String adminPh=scanner.next();
                        System.out.print("Enter your password: ");
                        String adminP=scanner.next();
                        if (Data.adminLogin(adminPh, adminP)) {
                            boolean flag=true;
                            while (flag) {
                                System.out.println("\n---Admin Panel---\n1-> Add flight\n2-> Remove flight\n3-> View all flights\n4-> View Registered Passangers\n5-> Logout");
                                System.out.print("Choose option: ");
                                int aListNumber = scanner.nextInt();
                                if (aListNumber==1){
                                    System.out.print("Enter flight ID: ");
                                    String newFlightId=scanner.next();
                                    System.out.print("Enter origin: ");
                                    String newOrigin=scanner.next();
                                    System.out.print("Enter destination: ");
                                    String newDestination=scanner.next();
                                    System.out.print("Enter departureTime: ");
                                    String newDepartureTime=scanner.next();
                                    System.out.print("Enter arrivalTime: ");
                                    String newArrivalTime=scanner.next();
                                    System.out.print("Enter capacity: ");
                                    int newCapacity=scanner.nextInt();
                                    System.out.print("Enter price: ");
                                    double newPrice=scanner.nextDouble();
                                    Data.addFlight(newFlightId,newOrigin,newDestination,newDepartureTime,newArrivalTime,newCapacity,newPrice);
                                    System.out.println("\nFlight added successfully!");
                                } else if (aListNumber==2) {
                                        System.out.print("\nEnter the flight ID you want to remove: ");
                                        String arnumber = scanner.next();
                                        Data.removeFlight(arnumber);
                                } else if (aListNumber==3) {
                                    System.out.println("\n"+Data.veiwFlights());
                                } else if (aListNumber==4) {
                                    System.out.println("\n"+Data.viewRegisteredPassengers());
                                } else if (aListNumber==5) {
                                    flag=false;
                                    System.out.println("\n");
                                } else {
                                    System.out.println("\nNumber must be beetween 1 and 5");
                                }
                            }
                        } else {
                            System.out.println("\nPhonenumber and/or password are/is incorrect\n");
                        }
                    } else {
                    	Data.savePassengersToFile();
                    	Data.saveFlightsToFile();
                        break;
                    }
                } else {
                    System.out.println("\nNumber must be beetween 1 and 4\n");
                }
            } else {
                System.out.println("\nYou should enter an Integer number\n");
                scanner.next();
            }

        }
        scanner.close();
	}

}
