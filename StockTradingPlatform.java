import java.util.ArrayList;
import java.util.Scanner;

class Stock {
    String name;
    double buyPrice;
    int quantity;

    Stock(String name, double buyPrice, int quantity) {
        this.name = name;
        this.buyPrice = buyPrice;
        this.quantity = quantity;
    }
}

public class StockTradingPlatform {

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Stock> portfolio = new ArrayList<>();
    static double balance = 100000; // Virtual money

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n===== STOCK TRADING PLATFORM =====");
            System.out.println("Available Balance: ₹" + balance);
            System.out.println("1. Buy Stock");
            System.out.println("2. Sell Stock");
            System.out.println("3. View Portfolio");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    buyStock();
                    break;
                case 2:
                    sellStock();
                    break;
                case 3:
                    viewPortfolio();
                    break;
                case 4:
                    System.out.println("Thank you for trading!");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void buyStock() {
        sc.nextLine();
        System.out.print("Enter Stock Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Stock Price: ");
        double price = sc.nextDouble();

        System.out.print("Enter Quantity: ");
        int qty = sc.nextInt();

        double totalCost = price * qty;

        if (totalCost > balance) {
            System.out.println("Insufficient balance!");
            return;
        }

        portfolio.add(new Stock(name, price, qty));
        balance -= totalCost;

        System.out.println("Stock purchased successfully!");
    }

    static void sellStock() {
        if (portfolio.isEmpty()) {
            System.out.println("No stocks to sell!");
            return;
        }

        viewPortfolio();

        System.out.print("Enter stock number to sell: ");
        int index = sc.nextInt() - 1;

        if (index < 0 || index >= portfolio.size()) {
            System.out.println("Invalid selection!");
            return;
        }

        System.out.print("Enter current market price: ");
        double marketPrice = sc.nextDouble();

        Stock s = portfolio.get(index);
        double sellValue = marketPrice * s.quantity;
        double profitLoss = sellValue - (s.buyPrice * s.quantity);

        balance += sellValue;
        portfolio.remove(index);

        System.out.println("Stock sold successfully!");
        System.out.println("Profit/Loss: ₹" + profitLoss);
    }

    static void viewPortfolio() {
        if (portfolio.isEmpty()) {
            System.out.println("Portfolio is empty!");
            return;
        }

        System.out.println("\n----- YOUR PORTFOLIO -----");
        int i = 1;
        for (Stock s : portfolio) {
            System.out.println(i++ + ". " + s.name +
                    " | Buy Price: ₹" + s.buyPrice +
                    " | Quantity: " + s.quantity);
        }
    }
}
