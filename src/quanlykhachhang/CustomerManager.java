import java.util.ArrayList;
import java.util.Scanner;

public class CustomerManager {
    private ArrayList<Customer> customers = new ArrayList<>();

    public void addCustomer(Customer c) {
        customers.add(c);
        System.out.println("Khach hang da duoc them thanh cong.");
    }

    public void listCustomers() {
        if (customers.isEmpty()) {
            System.out.println("Khong co khach hang.");
            return;
        }
        for (Customer c : customers) {
            c.displayInfo();
        }
    }

    public void removeCustomer(String username) {
        boolean removed = customers.removeIf(c -> c.getUsername().equals(username));
        if (removed) {
            System.out.println("Khach hang da bi xoa.");
        } else {
            System.out.println("Khong tim thay khach hang.");
        }
    }

    public void editCustomerInteractive(Scanner sc, String username) {
        Customer c = getCustomer(username);
        if (c == null) {
            System.out.println("Khong tim thay khach hang.");
            return;
        }

        System.out.print("Ten moi (enter de giu nguyen): ");
        String name = sc.nextLine();
        if (!name.isEmpty())
            c.setFullName(name);

        System.out.print("SDT moi (enter de giu nguyen): ");
        String phone = sc.nextLine();
        if (!phone.isEmpty())
            c.setPhoneNumber(phone);

        System.out.print("Email mới (enter de giu nguyen): ");
        String email = sc.nextLine();
        if (!email.isEmpty())
            c.setEmail(email);

        System.out.print("Dia chi moi (enter de giu nguyen): ");
        String address = sc.nextLine();
        if (!address.isEmpty())
            c.setAddress(address);

        System.out.println("Cap nhat thong tin thanh cong.");
    }

    public boolean exists(String username) {
        for (Customer c : customers) {
            if (c.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public Customer getCustomer(String username) {
        for (Customer c : customers) {
            if (c.getUsername().equals(username)) {
                return c;
            }
        }
        return null;
    }
}