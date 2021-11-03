
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import static javafx.scene.input.KeyCode.T;

public class Main {

    public static void main(String[] args) throws IOException {
        BTree<Product> p = new BTree<>();
        readProductFile("product_data.txt", p);
        BTree<Order> o = new BTree<>();
        readOrderFile("order_data.txt", o);
        int choice;
        while (true) {
            System.out.println("\n\n --- Sale management system ---");
            System.out.println("Product: ");
            System.out.println("\t1. Add product(s) to database");
            System.out.println("\t2. Search a product");
            System.out.println("\t3. Update a product");
            System.out.println("\t4. Delete a product");
            System.out.println("\t5. Traverse");
            System.out.println("Order: ");
            System.out.println("\t6. Create an order");
            System.out.println("\t7. Search an order");
            System.out.println("\t8. Update an order");
            System.out.println("\t9. Delete an order");
            System.out.println("\t10. Traverse");
            System.out.println("--- To exit: press 0 ---");
            System.out.print("Your selection (0 -> 10): ");
            choice = getInt("");
            if (choice == 0) {
                saveFile("order_data.txt", o, Order.class);
                saveFile("product_data.txt", p, Product.class);
                break;
            }
            switch (choice) {
                case 1:
                    int choice2;
                    while (true) {
                        System.out.println("Add prodcut(s) to database: ");
                        System.out.println("\t1. Manually");
                        System.out.println("\t2. From a file");
                        System.out.println("--- To stop adding: press 0 ---");
                        choice2 = getInt("Your option: ");
                        if (choice2 == 0) {
//                            saveFile("product_data.txt", p, Product.class);
                            break;
                        }
                        switch (choice2) {
                            case 1:
                                int n;
                                n = getInt("Number of elemens to be inserted: ");

                                for (int i = 0; i < n; i++) {
                                    System.out.println("Enter " + (i + 1) + "-th product: ");
                                    System.out.print("\tProduct ID: ");
                                    String ID = takeInputString("[0-9]+");
                                    if (p.search(p.root, ID) != null) {
                                        System.out.println("Product ID exist!");
                                        continue;
                                    }
                                    System.out.print("\tProduct name: ");
                                    String name = takeInputString("");
                                    System.out.print("\tImport date: ");
                                    String importDate = getDate("import");
                                    System.out.print("\tManufacturer: ");
                                    String manufacturer = takeInputString("");
                                    int yearOfPublish = getInt("\tYear of publish: ");
                                    Double unitPrice = getDouble("\tUnit price: ");
                                    int totalAmount = getInt("\tTotal amount: ");
                                    Product a = new Product(ID, name, importDate, manufacturer, unitPrice, yearOfPublish, totalAmount);
                                    Entry entry = new Entry(ID, a);
                                    p.insert(entry);
                                }
                                System.out.println("\nInsert more?");
                                break;
                            case 2:
                                System.out.print("Enter file name: ");
                                Scanner sc = new Scanner(System.in);
                                String fileName = sc.nextLine();
                                if (checkValidFile(fileName)) {
                                    readProductFile(fileName, p);
                                }
                                break;
                            default:
                                System.out.println("Invalid option!");
                                break;
                        }
                    }
                    break;
                case 2:
                    System.out.print("Enter productID to search: ");
                    String productID = takeInputString("[0-9]+");
                    SNode<Product> node = p.search(p.root, productID);
                    if (node != null) {
                        Entry<Product> a = node.p.key[node.k];
                        System.out.println(a.value.toString());
                    } else {
                        System.out.println("Product not found!");
                    }
                    break;
                case 3:
                    System.out.print("Enter productID to update: ");
                    String ID = takeInputString("[0-9]+");
                    SNode<Product> n = p.search(p.root, ID);
                    if (n != null) {
                        Entry<Product> en = n.p.key[n.k];
                        Product product = en.value;
                        System.out.print("\tProduct name: ");
                        String name = takeInputString("");
                        if (name.equals("")) {
                            name = product.getName();
                        }
                        System.out.print("\tImport date: ");
                        String importDate = getDate("import");
                        if (importDate.equals("")) {
                            importDate = product.getImportDate();
                        }
                        System.out.print("\tManufacturer: ");
                        String manufacturer = takeInputString("");
                        if (manufacturer.equals("")) {
                            manufacturer = product.getManufacturer();
                        }
                        int yearOfPublish = getInt("\tYear of publish: ");
                        if (yearOfPublish == -1) {
                            yearOfPublish = product.getYearOfPublish();
                        }
                        Double unitPrice = getDouble("\tUnit price: ");
                        if (unitPrice == -1.0) {
                            unitPrice = product.getUnitPrice();
                        }
                        int totalAmount = getInt("\tTotal amount: ");
                        if (totalAmount == -1) {
                            totalAmount = product.getTotalAmount();
                        }
                        Product a = new Product(ID, name, importDate, manufacturer, unitPrice, yearOfPublish, totalAmount);
                        en.setValue(a);
                        p.update(en);
                    } else {
                        System.out.println("Product ID not found!");
                    }
                    break;
                case 4:
                    System.out.print("Enter productID to remove: ");
                    String id = takeInputString("[0-9]+");
                    p.remove(id);
//                    saveFile("product_data.txt", p, Product.class);
                    break;
                case 5:
                    System.out.println("\nTotal products in database: " + p.total);
                    p.traverse(p.root);
                    break;
                case 6:
                    int choice3;
                    while (true) {
                        System.out.println("Creat an order");
                        System.out.println("\t1. Manually");
                        System.out.println("\t2. From a file");
                        System.out.println("--- To stop creating: press 0 ---");
                        choice3 = getInt("Your option: ");
                        if (choice3 == 0) {
//                            saveFile("order_data.txt", o, Order.class);
//                            saveFile("product_data.txt", p, Product.class);
                            break;
                        }
                        switch (choice3) {
                            case 1:
                                int noOfOrder = getInt("Number of order(s) to create: ");
                                for (int i = 0; i < noOfOrder; i++) {
                                    Date a = new Date();
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
                                    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                    String ordID = sdf.format(a);
                                    String ordDate = sdf2.format(a);
                                    Calendar c = Calendar.getInstance();
                                    c.setTime(a);
                                    c.add(Calendar.DAY_OF_MONTH, 3);    //auto delay 3 days after orderDate
                                    String shipDate = sdf2.format(c.getTime());

                                    Map<String, Integer> amount = getProductAmount(p);

                                    Order ord = new Order(ordID, ordDate, shipDate, amount);
                                    Entry<Order> entry = new Entry<>(ordID, ord);
                                    o.insert(entry);
                                }
                                break;
                            case 2:
                                System.out.print("Enter file name: ");
                                Scanner sc = new Scanner(System.in);
                                String fileName = sc.nextLine();
                                if (checkValidFile(fileName)) {
                                    readOrderFile(fileName, o);
                                }
                                break;
                            default:
                                System.out.println("Invalid option!");
                                break;
                        }
                    }
                    System.out.println("Create more?");
                    break;
                case 7:
                    System.out.print("Enter orderID to search: ");
                    String ordID = takeInputString("[0-9]+");
                    SNode<Order> ordNode = o.search(o.root, ordID);
                    if (ordNode != null) {
                        Entry<Order> a = ordNode.p.key[ordNode.k];
                        System.out.println(a.value.toString());
                    } else {
                        System.out.println("Order not found!");
                    }
                    break;
                case 8:
                    System.out.print("Enter ordID to update: ");
                    String orderID = takeInputString("[0-9]+");
                    SNode<Order> orderNode = o.search(o.root, orderID);
                    Entry<Order> orderEntry = orderNode.p.key[orderNode.k];
                    Order order = orderEntry.value;
                    int choice5;
                    while (true) {
                        System.out.println("\nUpdate an order");
                        System.out.println("\t1. Update product quantity");
                        System.out.println("\t2. Add a product");
                        System.out.println("\t3. Remove a product");
                        System.out.println("--- To stop updating: press 0 ---");
                        choice5 = getInt("Your option: ");
                        if (choice5 == 0) {
//                            saveFile("order_data.txt",o, Order.class);
//                            saveFile("product_data.txt",p, Product.class);
                            break;
                        }
                        switch (choice5) {
                            case 1:
                                System.out.print("Enter productID to update: ");
                                String proIDupdate = takeInputString("[0-9]+");
                                if (!order.getProductIDs().contains(proIDupdate)) {
                                    System.out.println("Product id not found in this order");
                                    break;
                                }
                                int newAmount = getInt("Amount: ");
                                if (newAmount == 0) {
                                    order.removeSingleProduct(proIDupdate, p);
                                } else if (newAmount > 0) {
                                    order.updateQuan(proIDupdate, newAmount, p);
                                } else {
                                    System.out.println("New amount < 0, no update!");
                                }
                                orderEntry.setValue(order);
                                o.update(orderEntry);
                                break;
                            case 2:
                                System.out.print("Enter productID to add: ");
                                String proID = takeInputString("[0-9]+");
                                int amount = getInt("Amount: ");
                                if (amount < 1) {
                                    System.out.println("No insertion!");
                                    break;
                                } else {
                                    order.addSingleProduct(proID, amount, p);
                                    orderEntry.setValue(order);
                                    o.update(orderEntry);
                                }
                                break;
                            case 3:
                                System.out.print("ProductID to remove: ");
                                String removeID = takeInputString("[0-9]+");
                                order.removeSingleProduct(removeID, p);
                                orderEntry.setValue(order);
                                o.update(orderEntry);
                                break;
                            default:
                                System.out.println("Invalid option!");
                                break;
                        }
                    }
                    break;
                case 9:
                    System.out.print("Enter orderID to remove: ");
                    String ordId = takeInputString("[0-9]+");

                    SNode<Order> nodeRemove = o.search(o.root, ordId);
                    if (nodeRemove == null) {
                        System.out.println("Order ID not found!");
                        break;
                    }
                    Entry<Order> entryRemove = nodeRemove.p.key[nodeRemove.k];
                    Order orderRemove = entryRemove.value;

                    Object[] array = orderRemove.getProductIDs().toArray();
                    String[] ids = new String[array.length];
                    for (int i = 0; i < array.length; i++) {
                        ids[i] = (String) array[i];
                    }
                    for (String product : ids) {
                        orderRemove.removeSingleProduct(product, p);
                    }
                    o.remove(ordId);
//                    saveFile("order_data.txt", o, Order.class);
//                    saveFile("product_data.txt", p, Product.class);
                    break;
                case 10:
                    System.out.println("\nTotal orders in database: " + o.total);
                    o.traverse(o.root);
                    break;
                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }
        System.out.println();
    }

    private static Map<String, Integer> getProductAmount(BTree<Product> p) {
        Map<String, Integer> amount = new HashMap<>();
        int choice;
        String proID;
        int quan;
        while (true) {
            System.out.print("Enter product ID: ");
            proID = takeInputString("[0-9]+");
            SNode<Product> proNode = p.search(p.root, proID);
            if (proNode == null) {
                System.out.println("Product id not found!");
                break;
            }
            quan = getInt("Enter amount: ");

            Entry<Product> proEntry = proNode.p.key[proNode.k];
            Product pro = proEntry.value;
            int proAmount = pro.getTotalAmount();
            if (proAmount >= quan) {
                pro.setTotalAmount(proAmount - quan);
                proEntry.setValue(pro);
                p.update(proEntry);
                if (amount.containsKey(proID)) {
                    quan = quan + amount.get(proID);
                }
                amount.put(proID, quan);
            } else {
                System.out.println("Not enought quantity! For product: " + proID);
                continue;
            }
            choice = getInt("Want to add more? (Enter 0 to cancel else 1)\nYour choice: ");
            if (choice == 0) {
                break;
            }
        }
        return amount;
    }

    private static int getInt(String message) {
        Scanner sc = new Scanner(System.in);
        int num;
        System.out.print(message);
        while (true) {
            try {
                String str = sc.nextLine();
                if (str.equals("")) {
                    return -1;
                }
                num = Integer.parseInt(str);
                if (num >= 0) {
                    if (message.equals("\tYear of publish: ")) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(new Date());
                        int year = calendar.get(calendar.YEAR);
                        if (year < num) {
                            System.out.print("Publish year must before or equal to current year!");
                            continue;
                        }
                    }
                    return num;
                }
                System.out.print("Invalid input! Re-enter: ");
            } catch (Exception e) {
                System.out.print("Invalid input! Re-enter: ");

            }
        }
    }

    private static Double getDouble(String message) {
        Scanner sc = new Scanner(System.in);
        Double db;
        System.out.print(message);
        while (true) {
            try {
                String str = sc.nextLine();
                if (str.equals("")) {
                    return -1.0;
                }
                db = Double.parseDouble(str);
                if (db >= 0) {
                    return db;
                } else {
                    System.out.print("Must be non-negative number! Enter again: ");
                    sc.nextLine();
                }
            } catch (Exception e) {
                System.out.print("Must be number! Enter again: ");
                sc.nextLine();
            }
        }
    }

    private static String getDate(String a) {
        Scanner sc = new Scanner(System.in);
        String date = null;
        while (true) {
            date = sc.nextLine();
            if (date.equals("")) {
                return date;
            } else if (checkValidDate(date, a)) {
                return date;
            }
            System.out.print("Invalid date! Re-enter: ");
        }
    }

    private static String takeInputString(String regex) {
        Scanner sc = new Scanner(System.in);
        String str = null;
        while (true) {
            str = sc.nextLine();
            if (regex.equals("")) {
                return str;
            } else if (str.matches(regex)) {
                return str;
            }
            System.out.print("Invalid input! Re-enter: ");
        }
    }

    private static boolean checkValidFile(String filename) {
        File file = new File(filename);
        return file.exists();
    }

    private static boolean checkValidDate(String date, String a) {
        String[] split = date.split(" ");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setLenient(false);
        simpleDateFormat2.setLenient(false);
        if (split.length >= 1) {
            try {
                Date d = simpleDateFormat2.parse(split[0]);

                if (!d.before(new Date()) && a.equals("import")) {
                    System.out.println("Date must before the current date!");
                    return false;
                } else if (d.before(new Date()) && a.equals("ship")) {
                    System.out.println("Ship date must equal or after current date!");
                    return false;
                }

                if (split.length == 2) {
                    simpleDateFormat.parse(split[1]);
                }
                return true;
            } catch (Exception e) {
                return false;
            }
        } else if (date.equals("")) {
            return true;
        }

        return false;
    }

    private static void readProductFile(String fileName, BTree<Product> product) {
        Entry[] entries;
        try {
            File input = new File(fileName);
            Scanner sc = new Scanner(input);

            Map<String, Product> m = new HashMap<>();
            while (sc.hasNextLine()) {
                String data = sc.nextLine();

                String[] split = data.split("\\s+");
//                System.out.println(split.length);
                if (split.length < 13) {
                    break;
                }
                String id = split[0];
                String name = split[2];
                String importDate = split[4];
                if (!checkValidDate(importDate, "import")) {
                    importDate = "null";
                }

                Double price;
                try {
                    price = Double.parseDouble(split[split.length - 5]);
                } catch (Exception e) {
                    System.out.println("Invalid price, no insertion");
                    continue;
                }

                String manufac = split[6];

                int year;
                try {
                    year = Integer.parseInt(split[10]);
                } catch (Exception e) {
                    year = 0;
                }

                int amount;
                try {
                    amount = Integer.parseInt(split[split.length - 1]);
                } catch (Exception e) {
                    System.out.println("Invalid amount, no insertion");
                    continue;
                }

                if (m.containsKey(id)) {
                    Product p = m.get(id);
                    int totalAmount = p.getTotalAmount() + amount;
                    p.setTotalAmount(totalAmount);
                    if (importDate.compareTo(p.getImportDate()) > 0) {
                        p.setYearOfPublish(year);
                        p.setManufacturer(manufac);
                        p.setUnitPrice(price);
                    }
                    m.put(id, p);
                } else {
                    Product p = new Product(id, name, importDate, manufac, price, year, amount);
                    m.put(id, p);
                }
            }

            entries = new Entry[m.keySet().size()];
            int count = 0;
            for (String i : m.keySet()) {
                Entry<Product> en = new Entry<>(i, m.get(i));
                entries[count] = en;
                count++;
            }
            product.insertMany(entries);
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("===> File not found <===");
        }
    }

    private static void readOrderFile(String fileName, BTree<Order> order) {
        LinkedList<Entry> entries = new LinkedList<>();
        try {
            File input = new File(fileName);
            Scanner sc = new Scanner(input);
            Map<String, Order> m = new HashMap<>();

            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                String[] split = data.split("\\s+");
                if (split.length == 0) {
                    break;
                }
                String id = split[0];
                String ordDate = split[2] + " " + split[3];
                if (!checkValidDate(ordDate, "order")) {
                    ordDate = "null";
                }

                String shipDate = split[5] + " " + split[6];
                if (!checkValidDate(shipDate, "ship")) {
                    shipDate = "null";
                }

                String proId = split[8];

                Integer amount;
                try {
                    amount = Integer.parseInt(split[split.length - 1]);
                } catch (Exception e) {
                    System.out.println("Invalid amount, no insertion to order");
                    continue;
                }
                if (m.containsKey(id)) {
                    Order ord = m.get(id);
                    Map<String, Integer> proAmount = ord.getAmount();
                    if (ord.getProductIDs().contains(proId)) {
                        proAmount.put(proId, proAmount.get(proId) + amount);
                    } else {
                        proAmount.put(proId, amount);
                    }
                    ord.setAmount(proAmount);
                    m.put(id, ord);
                } else {
                    Map<String, Integer> proAmount = new HashMap<>();
                    proAmount.put(proId, amount);
                    Order ord = new Order(id, ordDate, shipDate, proAmount);
                    m.put(id, ord);
                }
            }

            for (String i : m.keySet()) {
                Entry<Order> en = new Entry<>(i, m.get(i));
                entries.add(en);
            }
            Object[] array = entries.toArray();
            Entry[] entries_arr = new Entry[array.length];

            for (int i = 0; i < array.length; i++) {
                entries_arr[i] = (Entry) array[i];
            }
            order.insertMany(entries_arr);
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error in reading order: " + e.getMessage());
        }
    }

    private static void saveFile(String fileName, BTree p, Class type) throws IOException {
        File file = new File(fileName);

        if (file.exists()) {
            file.delete();

        }
        file.createNewFile();

        p.saveToFile(p.root, fileName, type);
    }
}
