import java.util.Scanner;
public class Costumer {
    private String name;
    private int pin;

    public Costumer(String name, int pin) {
        this.name = name;
        this.pin = pin;
    }

    public void updatePin(int newPin) {
        this.pin = newPin;
        System.out.println("PIN updated successfully!");
    }

    public String getName() {
        return name;
    }

    public int getPin() {
        return pin;
    }
}
