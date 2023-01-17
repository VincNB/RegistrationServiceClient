package nmbai;

public class Program {
    public static void main(String[] args) {
        int port = -1;
        try {
            port = Integer.parseInt(args[0]);
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("expecting port number as one argument");
        } catch (NumberFormatException ex) {
            System.out.println("arg should be an integer port number");
        }
        if (port > 0) {
            Driver driver = new Driver(port);
            driver.start();
            driver.run();
            driver.stop();
        }
    }
}
