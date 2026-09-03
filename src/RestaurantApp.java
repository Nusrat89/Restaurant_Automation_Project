
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RestaurantApp {
    public static List<Table> tables = new ArrayList<>();
    public static List<Waiter> waiters = new ArrayList<>();
    public static List<MenuItem> menu = new ArrayList<>();
    public static Queue<String> kitchenQueue = new LinkedList<>();

    public static void main(String[] args) {
        loadWaiters();  // Load waiter data from a file
        initializeTables();  // Initialize 30 tables
        initializeMenu();  // Populate menu items
        new LoginFrame();  // Show the login frame for waiters
    }

    // Load waiters from file (waiters.txt should be formatted as: name,password,table1|table2|...)
    public static void loadWaiters() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("waiters.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 3) {
                    System.out.println("Invalid line in waiters.txt: " + line);
                    continue;  // Skip invalid lines
                }

                String name = parts[0];
                String pass = parts[1];
                String[] tableParts = parts[2].split("\\|");
                List<Integer> tables = new ArrayList<>();
                for (String s : tableParts) {
                    tables.add(Integer.parseInt(s));
                }

                // Debugging: Print each waiter loaded from the file
                System.out.println("Loaded waiter: " + name + ", Password: " + pass + ", Tables: " + tables);

                waiters.add(new Waiter(name, pass, tables));
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Error reading waiters.txt: " + e.getMessage());
        }
    }


    // Create 30 tables
    public static void initializeTables() {
        for (int i = 1; i <= 30; i++) {
            tables.add(new Table(i));
        }
    }

    // Add sample menu items
    public static void initializeMenu() {
        String[] categories = {"Appetizers", "Entrees", "Desserts", "Drinks", "Salads"};
        for (String category : categories) {
            for (int i = 1; i <= 4; i++) {
                menu.add(new MenuItem(category, category + " Item " + i));
            }
        }
    }
}

// Table class
class Table {
    int number;
    String status = "Open";  // Can be Open, Occupied, or Dirty
    List<String> orders = new ArrayList<>();

    Table(int n) {
        number = n;
    }
}

// Waiter class
class Waiter {
    String name, password;
    List<Integer> assignedTables;

    Waiter(String n, String p, List<Integer> t) {
        name = n;
        password = p;
        assignedTables = t;
    }
}

// MenuItem class
class MenuItem {
    String category, name;

    MenuItem(String c, String n) {
        category = c;
        name = n;
    }
}

//LoginFrame
class LoginFrame extends JFrame {
    LoginFrame() {
        setTitle("Login");
        setSize(300, 150);
        setLayout(new GridLayout(3, 2));

        JTextField user = new JTextField();
        JPasswordField pass = new JPasswordField();
        JButton login = new JButton("Login");

        add(new JLabel("Username:"));
        add(user);
        add(new JLabel("Password:"));
        add(pass);
        add(new JLabel());  // Empty label for spacing
        add(login);

        // Adding ActionListener to login button
        login.addActionListener(e -> {
            String u = user.getText();
            String p = new String(pass.getPassword());

            // Validate credentials
            for (Waiter w : RestaurantApp.waiters) {
                if (w.name.equals(u) && w.password.equals(p)) {
                    dispose();  // Close login window
                    new FloorFrame(w);  // Open FloorFrame for the waiter
                    return;
                }
            }

            // Show error message if login fails
            JOptionPane.showMessageDialog(this, "Login failed");
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}


// Floor status frame (waiter assigned tables)
class FloorFrame extends JFrame {
    FloorFrame(Waiter waiter) {
        setTitle("Floor - " + waiter.name);
        setLayout(new GridLayout(5, 6));
        setSize(600, 400);

        for (Table t : RestaurantApp.tables) {
            JButton b = new JButton("Table " + t.number);
            if (waiter.assignedTables.contains(t.number)) {
                if (t.status.equals("Open")) b.setBackground(Color.GREEN);
                if (t.status.equals("Occupied")) b.setBackground(Color.YELLOW);
                if (t.status.equals("Dirty")) b.setBackground(Color.RED);
                b.addActionListener(e -> new TableFrame(t));  // Click to view table details
            } else {
                b.setEnabled(false);  // Disable tables not assigned to the waiter
            }
            add(b);
        }

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}

// Table details frame (where waiter can add items)
class TableFrame extends JFrame {
    TableFrame(Table t) {
        setTitle("Table " + t.number);
        setSize(300, 150);
        JButton add = new JButton("Add Item");
        add(add);
        add.addActionListener(e -> new MenuFrame(t));  // Open menu to add item to the table
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}

// Menu frame (where waiter selects menu items for a table)
class MenuFrame extends JFrame {
    MenuFrame(Table t) {
        setTitle("Menu for Table " + t.number);
        setSize(300, 300);
        JComboBox<String> catBox = new JComboBox<>();
        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> itemList = new JList<>(model);
        JButton add = new JButton("Add to Order");

        // Populate categories in combo box
        for (MenuItem m : RestaurantApp.menu) catBox.addItem(m.category);

        catBox.addActionListener(e -> {
            model.clear();
            for (MenuItem m : RestaurantApp.menu) {
                if (m.category.equals(catBox.getSelectedItem())) {
                    model.addElement(m.name);
                }
            }
        });

        add.addActionListener(e -> {
            String item = itemList.getSelectedValue();
            if (item != null) {
                t.orders.add(item);
                t.status = "Occupied";  // Change table status to occupied
                RestaurantApp.kitchenQueue.add("Table " + t.number + ": " + item);  // Add to kitchen queue
                System.out.println("Queued: " + item);
                dispose();
            }
        });

        setLayout(new BorderLayout());
        add(catBox, BorderLayout.NORTH);
        add(new JScrollPane(itemList), BorderLayout.CENTER);
        add(add, BorderLayout.SOUTH);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
