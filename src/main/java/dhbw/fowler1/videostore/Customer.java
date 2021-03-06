package dhbw.fowler1.videostore;

import java.util.Enumeration;
import java.util.Vector;

public class Customer {

    private String _name;
    private Vector _rentals = new Vector();

    public Customer(String name) {
        _name = name;
    }

    public String statement() {
        return new TextStatement().value(this);
    }

    public void addRental(Rental arg) {
        _rentals.addElement(arg);
    }

    public Enumeration getRentals() {
        return _rentals.elements();
    }

    public String getName() {
        return _name;
    }

    private double getTotalCharge() {
        double result = 0;
        Enumeration rentals = _rentals.elements();
        while (rentals.hasMoreElements()) {
            Rental each = (Rental) rentals.nextElement();
            result += each.getCharge();
        }
        return result;
    }

    private int getTotalFrequentRenterPoints() {
        int result = 0;
        Enumeration rentals = _rentals.elements();
        while (rentals.hasMoreElements()) {
            Rental each = (Rental) rentals.nextElement();
            result += each.getFrequentRenterPoints();
        }
        return result;
    }

    public String htmlStatement() {
        return new HtmlStatement().value(this);
    }

    abstract class Statement {

        public String value(Customer aCustomer) {
            Enumeration rentals = aCustomer.getRentals();
            String result = headerString(aCustomer);
            while (rentals.hasMoreElements()) {
                Rental each = (Rental) rentals.nextElement();
                result += eachRentalString(each);
            }
            result += footerString(aCustomer);
            return result;
        }

        abstract String headerString(Customer aCustomer);

        abstract String eachRentalString(Rental aRental);

        abstract String footerString(Customer aCustomer);

    }

    class TextStatement extends Statement {

        @Override
        String headerString(Customer aCustomer) {
            return "Rental Record for " + aCustomer.getName() + "\n";
        }

        @Override
        String eachRentalString(Rental aRental) {
            return "\t" + aRental.getMovie().getTitle() + "\t"
                    + String.valueOf(aRental.getCharge()) + "\n";
        }

        @Override
        String footerString(Customer aCustomer) {
            return "Amount owed is "
                    + String.valueOf(aCustomer.getTotalCharge()) + "\n"
                    + "You earned "
                    + String.valueOf(aCustomer.getTotalFrequentRenterPoints())
                    + " frequent renter points";
        }
    }

    class HtmlStatement extends Statement {

        @Override
        String headerString(Customer aCustomer) {
            return "<H1>Rentals for <EM>" + aCustomer.getName() + "</EM></H1><P>\n";
        }

        @Override
        String eachRentalString(Rental aRental) {
            return aRental.getMovie().getTitle() + ": "
                    + String.valueOf(aRental.getCharge()) + "<BR>\n";
        }

        @Override
        String footerString(Customer aCustomer) {
            return "<P>You owe <EM>" + String.valueOf(getTotalCharge())
                    + "</EM><P>\n"
                    + "On this rental you earned <EM>"
                    + String.valueOf(getTotalFrequentRenterPoints())
                    + "</EM> frequent renter points<P>";
        }
    }

}
