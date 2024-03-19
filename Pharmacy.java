import java.util.*;
import banking.Account;

public class Pharmacy {
    static Vector<Customer> customers = new Vector<Customer> ();
    static Vector<Admin> admins = new Vector<Admin> ();
    static Scanner s = new Scanner(System.in);
    static int currAdmin = -1;
    static int currCustomer = -1;
    static Vector<Medicine> medicineStock = new Vector<Medicine> ();
    static Account acc = new Account(100000);

    public static void main(String[] args) {
        System.out.println("Welcome to Pharmacy Management System");
        int choice = 1;

        while (true) {
            System.out.println("Choose an option\n 1. Register \n 2. Sign In\n 3. Exit");

            choice = s.nextInt();
            if (choice == 1) {
                register();
            } else if (choice == 2) {
                loginMenu();
            } else if (choice == 3) {
                System.out.println("Thank you for using Pharmacy Management System");
                return;
            }
        }
    }

    static void loginMenu() {
        String e, p;
        System.out.println("Enter the email of the user");
        s.nextLine();
        e = s.nextLine();
        System.out.println("Enter the password of the user");
        p = s.nextLine();
        int role = login(e, p);
        if (role == 1) {
            currAdmin = -1;
            currCustomer = -1;
            System.out.println("Successfully Logged in!\n\n");
            for (int i = 0; i < admins.size(); i++) {
                if (admins.get(i).email.equals(e)) currAdmin = i;
            }
            adminMenu();
        } else if (role == 2) {
            currAdmin = -1;
            currCustomer = -1;
            System.out.println("Successfully Logged in!\n\n");
            for (int i = 0; i < customers.size(); i++) {
                if (customers.get(i).email.equals(e)) currCustomer = i;
            }
            customerMenu();
        } else {
            System.out.println("\nInvalid Credentials");
        }
    }

    static void adminMenu() {
        while (true) {
            System.out.println("\nOptions\n1. View Stocked Medicines\n2. Stock Medicines\n3. View Financial Info\n4. Edit Medicine Stock Prices\n5. View Customer List\n6. Log Out");
            int adChoice = s.nextInt();
            switch (adChoice) {
                case 1:
                    viewStock();
                    break;
                case 2:
                    stockMedicine();
                    break;
                case 3:
                    viewFinances();
                    break;
                case 4:
                    editStock();
                    break;
                case 5:
                    viewCustomerList();
                    break;
                case 6:
                    currAdmin = -1;
                    return;
            }
        }
    }

    static void customerMenu() {
        while (true) {
            System.out.println("\nOptions\n1. View Medicine List\n2. Add to Cart\n3. View Cart\n4. Buy\n5. Log Out");
            int customerChoice = s.nextInt();
            switch (customerChoice) {
                case 1:
                    MedicineList.displayAll();
                    break;
                case 2:
                    addToCart();
                    break;
                case 3:
                    viewCart();
                    break;
                case 4:
                    buy();
                    break;
                case 5:
                    currCustomer = -1;
                    return;
            }
        }
    }

    static void register() {
        String n;
        String e;
        String p;
        int role;

        System.out.println("Enter the name of the user");
        s.nextLine();
        n = s.nextLine();
        System.out.println("Enter the email of the user");
        e = s.nextLine();
        System.out.println("Enter the password of the user");
        p = s.nextLine();
        System.out.println("Enter the role of the user:\n 1 for Admin\n 2 for Customer");
        role = s.nextInt();

        if(role == 1)
        {
            Admin a = new Admin(n, e, p);
            admins.add(a);
        }
        if(role == 2)
        {
            String pn;
            String d;
            System.out.println("Enter the phone number");
            s.nextLine();
            pn = s.nextLine();
            System.out.println("Enter the doctor's name");
            d = s.nextLine();
            Customer a = new Customer(n, e, p, pn, d);
            customers.add(a);
        }

    }

    static int login(String email, String password){
        try{
        for(int i = 0; i < admins.size(); i++)
        {
            
            if(admins.get(i).email.equals(email) && admins.get(i).password.equals(password))return 1;
        }
        for(int i = 0; i < customers.size(); i++)
        {
            if(customers.get(i).email.equals(email) && customers.get(i).password.equals(password))return 2;
        }
    } catch(Exception e){
        System.out.println("An error occurred: " + e.getMessage());
    }
        return 0;
    }

    static void viewStock() {
        System.out.println("Medicine Stock:");
        for (Medicine medicine : medicineStock) {
            System.out.println("Name: " + medicine.name + ", Price: " + medicine.price + ", Quantity: " + medicine.quantity);
        }
    }

    static void stockMedicine() {
       
        System.out.println("Available Medicines:");
        MedicineList.displayAll();

        System.out.println("Enter the name of the medicine you want to buy: ");
        s.nextLine();
        String medicineName = s.nextLine();

        System.out.println("Enter the quantity you want to buy: ");
        int quant = s.nextInt();
        System.out.println(quant);

        int j = 1;
        for (Medicine medicine : MedicineList.arr) {
            if (medicine.name.equals(medicineName)) {
                if (true) {
                    j = 0;
                    medicine.quantity -= quant;
                    Medicine stockMedicine = new Medicine(medicine.name, medicine.price, quant);
                    medicineStock.add(stockMedicine);
                    System.out.println("Medicine added to your stock.");
                    Account.balance -= quant * medicine.price;
                    Account.expenditure += quant * medicine.price;
                } 
                return;
            }
        }
        if(j == 1)
        {
            System.out.println("Wrong Medicine Name");
        }
   
    }

        static void viewFinances() {
                    System.out.println("Financial Information:");
                    System.out.println("Balance: " + Account.balance);
                    System.out.println("Expenditure: " + Account.expenditure);
                    System.out.println("Revenue: " + Account.revenue);
    }
    static void editStock() {
        try{
            System.out.println("Select a medicine to edit the price:");

            for (int i = 0; i < medicineStock.size(); i++) {
                System.out.println((i + 1) + ": " + medicineStock.get(i).name);
            }

            int choice = s.nextInt();
            if (choice >= 1 && choice <= medicineStock.size()) {
                System.out.println("Enter the new price for the medicine:");
                double newPrice = s.nextDouble();
                medicineStock.get(choice - 1).price = newPrice;
                System.out.println("Price updated successfully.");
            } else {
                throw new IllegalArgumentException("Invalid choice.");
            }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                s.next();
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
        }
    }

    static void viewCustomerList() {
        System.out.println("Customer List:");
        for (int i = 0; i < customers.size(); i++) {
            System.out.println("Name: " + customers.get(i).name + ", Email: " + customers.get(i).email);
        }
    }

    static void addToCart() {
        try{
            System.out.println("Available Medicines:");
            viewStock();

            System.out.println("Enter the name of the medicine you want to add to your cart: ");
            s.nextLine();
            String medicineName = s.nextLine();

            System.out.println("Enter the quantity you want to add to your cart: ");
            int quantityToAdd = s.nextInt();

            for (Medicine medicine : medicineStock) {
                if (medicine.name.equals(medicineName)) {
                    if (quantityToAdd <= medicine.quantity) {
                        medicine.quantity -= quantityToAdd;
                        Customer customer = customers.get(currCustomer);
                        customer.addToCart(medicineName, medicine.price, quantityToAdd);
                        System.out.println("Medicine added to your cart.");
                    } else {
                        System.out.println("Not enough quantity available.");
                    }
                    return;
                }
            }

            System.out.println("Medicine not found in the available list.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            s.next();
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
    }
    }

    static void viewCart() {
        
        Customer customer = customers.get(currCustomer);
        customer.viewCart();
        
    }

    static void buy() {
        Customer customer = customers.get(currCustomer);
        try{
        
        if(customer.cart.size() != 0) customer.buy();
        else throw new Exception("Cart is empty");
        
        } catch(Exception e){
            System.out.println("An error occurred while making the purchase: " + e.getMessage());
        }
        
    }

    
}
class User {
    String name;
    String email;
    String password;
    int role = -1;

    String getName() {
        return this.name;
    }
}

class Admin extends User {
    Admin(String n, String e, String p) {
        name = n;
        email = e;
        password = p;
        role = 1;
    }
}

class Customer extends User {
    String phone_number;
    String doctor_name;
    Vector<Medicine> cart = new Vector<Medicine>();

    Customer(String n, String e, String p, String pn, String d) {
        name = n;
        email = e;
        password = p;
        phone_number = p;
        doctor_name = d;
        role = 2;
    }

    void addToCart(String medicineName, double price, int quantity) {
        try {
            Medicine cartMedicine = new Medicine(medicineName, price, quantity);
            cart.add(cartMedicine);
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }


    void viewCart() {
        try{
        System.out.println("Your Cart:");
        for (Medicine cartMedicine : cart) {
            System.out.println("Medicine: " + cartMedicine.name + ", Price: " + cartMedicine.price + ", Quantity: " + cartMedicine.quantity);
        }
        } catch(Exception e){
            System.out.println("An error occurred while viewing the cart: " + e.getMessage());
        }
    }

    void buy() {
        try{
        double total = 0.0;
        for (Medicine cartMedicine : cart) {
            total += cartMedicine.price * cartMedicine.quantity;
        }

        Account.balance += total;
        Account.revenue += total;
        cart.clear();
        System.out.println("Purchase successful. Total cost: " + total);
        }catch(Exception e){
            System.out.println("An error occurred while making the purchase: " + e.getMessage());
        }
    }

}


class MedicineList {
    static int n = 5;
    static Medicine arr[] = new Medicine[n];
    
    static {
        arr[0] = new Medicine("Crocin", 100.00, 100000);
        arr[1] = new Medicine("Paracetamol", 80.00, 80000);
        arr[2] = new Medicine("Aspirin", 120.00, 90000);
        arr[3] = new Medicine("Ibuprofen", 150.00, 75000);
        arr[4] = new Medicine("Antacid", 50.00, 120000);
    }

    static void displayAll() {
        System.out.println("The medicines available are:");
        for (int i = 0; i < n; i++) {
            System.out.println(arr[i].name + " " + arr[i].price);
        }
    }
}


class Medicine {
    String name;
    double price;
    int quantity;

    Medicine(String n, double p, int q) {
        name = n;
        price = p;
        quantity = q;
    }
}

