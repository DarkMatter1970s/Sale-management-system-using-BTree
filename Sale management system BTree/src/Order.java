
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Order {

    private String orderID;
    private String ordDate;
    private String shipDate;
    private Map<String, Integer> amount;

    public Order(String orderID, String ordDate, String shipDate, Map<String, Integer> amount) {
        this.orderID = orderID;
        this.ordDate = ordDate;
        this.shipDate = shipDate;
        this.amount = amount;
    }

    public Order() {
        this.orderID = null;
        this.amount = new HashMap<>();
        this.ordDate = null;
        this.shipDate = null;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getOrdDate() {
        return ordDate;
    }

    public String getShipDate() {
        return shipDate;
    }

    public Set<String> getProductIDs() {
        return amount.keySet();
    }

    public Map<String, Integer> getAmount() {
        return amount;
    }

    public int getSingleAmount(String productID) {
        if (getProductIDs().contains(productID)) {
            return this.amount.get(productID);
        }
        System.out.println("ProductID not found!");
        return -1;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public void setShipDate(String shipDate) {
        this.shipDate = shipDate;
    }

    public void setAmount(Map<String, Integer> amount) {
        this.amount = amount;
    }

    public void setSingleAmount(String id, int amount) {
        if (getProductIDs().contains(id)) {
            this.amount.put(id, amount);
            return;
        }
        System.out.println("ProductID not found!");
    }

    public void addSingleProduct(String id, int amount, BTree<Product> p) {
        SNode<Product> proNode = p.search(p.root, id);
        if (proNode == null) {
            System.out.println("Product id not found!");
            return;
        }
        Entry<Product> proEntry = proNode.p.key[proNode.k];
        Product pro = proEntry.value;
        int proAmount = pro.getTotalAmount();
        if (proAmount >= amount) {
            if (getProductIDs().contains(id)) {
                int old = this.amount.get(id);
                this.amount.put(id, amount + old);
                System.out.println("\nUpdated amount of productID = " + id + " from " + old + " to " + (amount + old));
            } else {
                this.amount.put(id, amount);
                System.out.println("\nAdded new product into order: ");
                System.out.println("ProductID = " + id + "\tamount = " + amount);
            }
        }else{
            System.out.println("Not enought quantity! For product: " + id);
            return;
        }

        SNode<Product> productNode = p.search(p.root, id);
        Entry<Product> entry = productNode.p.key[productNode.k];
        entry.value.setTotalAmount(entry.value.getTotalAmount() - amount);
        p.update(entry);
    }

    public void removeSingleProduct(String productID, BTree<Product> p) {
        if (getProductIDs().contains(productID)) {
            SNode<Product> productNode = p.search(p.root, productID);
            if (productNode == null) {
                return;
            }
            Entry<Product> entry = productNode.p.key[productNode.k];
            int ordAmount = getSingleAmount(productID);
            entry.value.setTotalAmount(entry.value.getTotalAmount() + ordAmount);
//            System.out.println("Entry amount: " + entry.value.getTotalAmount());
            p.update(entry);
            this.amount.remove(productID);
        } else {
            System.out.println("ProductID not found in this order!");
        }
    }

    public void updateQuan(String id, int quan, BTree<Product> p) {
        if (getProductIDs().contains(id)) {
            SNode<Product> productNode = p.search(p.root, id);
            Entry<Product> entry = productNode.p.key[productNode.k];
            int oldQuan = getSingleAmount(id);
            if (oldQuan > quan) {
                entry.value.setTotalAmount(entry.value.getTotalAmount() + (oldQuan - quan));
            } else if (oldQuan < quan) {
                entry.value.setTotalAmount(entry.value.getTotalAmount() - (oldQuan - quan));
            }
            p.update(entry);
            this.amount.put(id, quan);
        } else {
            System.out.println("ProductID not found in this order!");
        }
    }

    @Override
    public String toString() {
        String ids = "\n";
        for (String i : getProductIDs()) {
            ids = ids + "\t" + i + "\t\t" + amount.get(i) + "\n";
        }
        return "orderID: " + orderID + "\n\tordDate: " + ordDate + "\n\tshipDate: " + shipDate + "\n\tproductIDs--\t\tTotal amount: " + ids;
    }

    public String displayToFile() {
        String s = "";
        for (String i : getProductIDs()) {
            s = s + String.format("%-20s | %-30s | %-20s | %-30s | %-7d", orderID, ordDate, shipDate, i, amount.get(i)) + "\n";
        }
        return s;
    }
}
