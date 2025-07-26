
public class Passangers {
	    private String phonenumber;
	    private String password;
	    private String fullName;
	    private double wallet;
	    private String[] bookedFlights = new String[50];
	    private int bookedCount = 0;

	    public String getFullName() {
	        return fullName;
	    }

	    public void setFullName(String fullName) {
	        this.fullName = fullName;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }

	    public double getWallet() {
	        return wallet;
	    }

	    public void setWallet(double wallet) {
	        this.wallet = wallet;
	    }

	    public String getPhonenumber() {
	        return phonenumber;
	    }

	    public void setPhonenumber(String phonenumber) {
	        this.phonenumber = phonenumber;
	    }

	    public String[] getBookedFlights() {
	        return bookedFlights;
	    }

	    public int getBookedCount() {
	        return bookedCount;
	    }

	    public void addBookedFlight(String flightId){
	        if (bookedCount<bookedFlights.length){
	         bookedFlights[bookedCount]=flightId;
	         bookedCount++;
	        }
	    }

	    public boolean hasBookedFlight(String flightId){
	        for (int i = 0; i < bookedCount; i++) {
	        if (bookedFlights[i].equals(flightId)){
	            return true;
	        }
	        }
	        return false;
	    }

	    public boolean checkUp(String phonenumber, String password) {
	        return getPhonenumber().equals(phonenumber) && getPassword().equals(password);
	    }

}
