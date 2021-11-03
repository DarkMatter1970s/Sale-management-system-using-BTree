
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class BTree<T> {

    private final int M = 2;   // Minimum number of keys in each node, except the root
    private final int N = 2 * M; // Maximum number of keys in each node
    private final int N1 = N + 1; // Maximum number of children in each node (Thus N1 is the order of the tree
    private final int N2 = N + 2; // 1 place added to contain extra son
    Node root;
    int total = 0;

    BTree() {
        root = null;
    }

    void clear() {
        root = null;
    }

    boolean isEmpty() {
        return (root == null);
    }

//============================================================================
    // we add 1 extra position to the key and son arrays to contain the overflowed key 
    class Node<T> {

        int keynum; // number of keys in the node.
        Entry<T>[] key; // Contains keys, in general contains objects with key
        Node[] son; // References to sons

        Node() {
            key = new Entry[N1]; // add 1 extra position to contain overflowed key
            son = new Node[N2]; // add 1 extra position to contain overflowed son
            keynum = 0;
            int i;
            for (i = 0; i < N1; i++) {
                key[i] = new Entry<>(null, null);
            }
            for (i = 0; i < N2; i++) {
                son[i] = null;
            }
        }

        boolean isOverFlow() {
            return (keynum > N);
        }

        boolean isUnderFlow() {
            return (keynum < M);
        }

    }

//============================================================================
    void visit(Node p) {
        if (p == null) {
            return;
        }
//        System.out.println("\nkeynum = " + p.keynum);
        for (int i = 0; i < p.keynum; i++) {
            System.out.print(p.key[i].key + " ");
        }
        System.out.println();
    }

    void visit(SNode u) {
        if (u == null) {
            return;
        }
        visit(u.p);
//        System.out.println("k = " + u.k);
    }

    void traverse(Node p) {
        if (p == null) {
            return;
        }
        int n, i;
        n = p.keynum;
        for (i = 0; i < n; i++) {
            traverse(p.son[i]);
            System.out.print(p.key[i].key + " ");
        }
        traverse(p.son[n]);
    }

    void saveToFile(Node p, String fileName, Class<T> type) throws IOException {
        

        File file = new File(fileName);
        
        if (p == null) {
            return;
        }
        
        FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter out = null;
        int n = 0, i;
        try {
            fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
            out = new PrintWriter(bw);

            n = p.keynum;
            for (i = 0; i < n; i++) {
                saveToFile(p.son[i],fileName,type);
                Object ob = p.key[i].value;
                if (type == Product.class) {
                    Product product = (Product) ob;
                    out.print(product.displayTofile());
                }else if (type == Order.class) {
                    Order product = (Order) ob;
                    out.print(product.displayToFile());
                }

            }
            out.close();
        } catch (IOException e) {
            System.out.println("Error:" + e.getMessage());
        } finally {
            fw.close();
            bw.close();
        }
        saveToFile(p.son[n],fileName,type);
    }

//============================================================================
    /* Find on the node the position i, where p.key==xKey or we can find xKey on p.son[i]
     if i=p.keynum then xKey is not found in the node p*/
    int NodeSearch(Node p, String xKey) {
        if (p == null) {
            return (-1);
        }
        int i;
        i = 0;
        while (i < p.keynum && p.key[i].key.compareTo(xKey) < 0) {
            i++;
        }
        return (i);
    }

//============================================================================
    // Find on the tree with root proot the node p and position k, where p.key[k] == x 
    SNode search(Node proot, String xKey) {
        SNode u = new SNode();
        int k;
        Node fp, p;   // fp is the father, p is the son
        if (proot == null) {
            return (null);
        }
        k = NodeSearch(proot, xKey);
        if (k < proot.keynum && xKey.equals(proot.key[k].key)) // found
        {
            u.k = k;
            u.p = proot;
            return (u);
        }
        return (search(proot.son[k], xKey));
    }

//============================================================================
/* Split the overflowed node p(p.keynum = 2M+1
   The node is splited to 2 node p and p2. p contains M keys: from p.key[0] to p.key[M-1],
   p2 contains M keys: from p.key[M+1] to p.key[2*M].
   The middle key p.key[M] is inserted into p's father fp.
     */
    void split(Node p, Node fp, int k) {
        if (p == null || !p.isOverFlow() || fp == null) {
            return;
        }
        int i;
        Node p2 = new Node();
        //Start spliting the node p;
        Entry newkey = p.key[M];
        //copy from M+1 to p2
        for (i = 0; i < M; i++) {
            p2.key[i] = p.key[M + i + 1];
            p2.son[i] = p.son[M + i + 1];
        }
        p2.son[M] = p.son[N1];//the last son

        p.keynum = p2.keynum = M;
        for (i = fp.keynum; i > k; i--) {
            fp.son[i + 1] = fp.son[i];
            fp.key[i] = fp.key[i - 1];
        }
        fp.keynum++;
        fp.key[k] = newkey;
        fp.son[k] = p;
        fp.son[k + 1] = p2;
    }

    void update(Entry x) {
        Node p, p1, fp;
        int i, j, k;

        fp = null;
        p = root;
        while (p != null) {
            k = NodeSearch(p, x.key);
            if (k < p.keynum && x.key.equals(p.key[k].key)) {    // found
                p.key[k] = x;
                return;
            }
            fp = p;
            p = p.son[k];
        }
    }
//============================================================================

    void insert(Entry x) {
        Node p, p1, fp;
        int i, j, k;
        if (isEmpty()) {
            this.total++;
            root = new Node();
            root.keynum = 1;
            root.key[0] = x;//The root has 1 key only
            return;
        }
        //Starting from the root, find the key x, add all nodes met on the path to stack 
        SNode sp;
        MyStack<SNode> st = new MyStack<>();
        //Starting from the root

        fp = null;
        p = root;
        while (p != null) {
            k = NodeSearch(p, x.key);
            if (k < p.keynum && x.key.equals(p.key[k].key)) {    // found
                System.out.println("The key already exists, no insertion");
                return;
            }
            sp = new SNode();
            sp.p = p;
            sp.k = k;
            st.push(sp);
            fp = p;
            p = p.son[k];
        }
        this.total++;
        sp = st.pop(); // the node at the top is fp
        p = sp.p;
        k = sp.k;
        for (i = p.keynum; i > k; i--) {
            p.key[i] = p.key[i - 1];
        }
        p.key[k] = x;
        p.keynum++;
        if (!p.isOverFlow()) {
            return;
        }
        if (p == root) {
            root = new Node();
            split(p, root, 0);
            return;
        }
        while (!st.isEmpty()) {
            sp = st.pop();
            fp = sp.p;
            k = sp.k;
            split(p, fp, k);
            p = fp;
            if (!p.isOverFlow()) {
                return;
            }
            if (p == root) {
                root = new Node();
                split(p, root, 0);
                break;
            }
        }
    }

//============================================================================
/*Suppose the node p misses 1 key, i.e. p.keynum=M-1. 
  This method is called by the method remove().
  If the neighbor node of p has more than  M key, then we borrow 1 key
  from that and add to  p.
     */
    void merge(Node p, Node fp, int k) {
        if (p.keynum >= M) {
            return;
        }
        // For the simplicity, we consider the left neighbor pl,fp.key[k-1] and p.

        Node pl, pr;
        int i, j;
        if (k > 0) {
            pl = fp.son[k - 1];

            if (pl == null) {
//                System.out.println("fp.son[k-1] is null, k = " + k);
                visit(fp);
                return;
            }

            if (pl.keynum > M) {  //pl has more than M keys
                for (i = p.keynum; i > 0; i--) {
                    p.son[i + 1] = p.son[i];
                    p.key[i] = p.key[i - 1];
                } //Move keys key[0],son[1],key[1],son[2],... to right
                p.son[1] = p.son[0];//Don son[0] sang son[1]

                p.key[0] = fp.key[k - 1];
                p.son[0] = pl.son[pl.keynum];
                fp.key[k - 1] = pl.key[pl.keynum - 1];
                pl.keynum--;
                p.keynum++;
                return;
            }
            //pl.keynum<=M, merge pl, fp.key[k-1] and p, remove p and process fp
            pl.key[pl.keynum] = fp.key[k - 1];
            for (i = 0; i < p.keynum; i++) {
                pl.key[pl.keynum + i + 1] = p.key[i];
                pl.son[pl.keynum + i + 1] = p.son[i];
            }
            pl.son[pl.keynum + p.keynum + 1] = p.son[p.keynum];
            for (i = k - 1; i < fp.keynum - 1; i++) {
                fp.key[i] = fp.key[i + 1];
                fp.son[i + 1] = fp.son[i + 2];
            }
            fp.keynum--;
            pl.keynum = pl.keynum + p.keynum + 1;
            return;
        }//if(k>0)

        //if(k==0) merge fp.son[k] with fp.son[k+1]
        if (k == 0) {
            pr = fp.son[k + 1];

            if (pr == null) {
//                System.out.println("fp.son[k+1] is null, k = " + k);
                visit(fp);
                return;
            }

            if (pr.keynum > M) { //pr has more than M keys
                p.key[M - 1] = fp.key[k];
                p.son[M] = pr.son[0];
                fp.key[k] = pr.key[0];
                for (i = 0; i < pr.keynum - 1; i++) {
                    pr.son[i] = pr.son[i + 1];
                    pr.key[i] = pr.key[i + 1];
                }
                pr.son[pr.keynum - 1] = pr.son[pr.keynum];
                p.keynum++;
                pr.keynum--;
                return;
            }
            //pr.keynum<=M, merge p, fp.key[k] and pr, delete pr and process fp
            p.key[p.keynum] = fp.key[k];
            for (i = 0; i < pr.keynum; i++) {
                p.key[p.keynum + i + 1] = pr.key[i];
                p.son[p.keynum + i + 1] = pr.son[i];
            }
            p.son[p.keynum + pr.keynum + 1] = pr.son[pr.keynum];
            for (i = k; i < fp.keynum - 1; i++) {
                fp.key[i] = fp.key[i + 1];
                fp.son[i + 1] = fp.son[i + 2];
            }
            fp.keynum--;
            p.keynum = p.keynum + pr.keynum + 1;
        } //end of if(k==0)
    }

//============================================================================
    // Remove the key x
    void remove(String x) {
        Node p, fp;
        int i, j, k;

        /*Starting from the root, find the key x, 
       add all nodes on the finding path to the Stack*/
        SNode sp = new SNode();
        MyStack<SNode> st = new MyStack<SNode>();

        fp = null;
        p = root;
        k = -1;
        while (p != null) {
            k = NodeSearch(p, x);
            sp.p = p;
            sp.k = k;
            st.push(sp);
            if (k < p.keynum && x.equals(p.key[k].key)) {
                break;  // found
            }
            fp = p;
            p = p.son[k];
        }
        if (p == null) {
            System.out.println("The id " + x + " is not found");
            return;
        }
        this.total--;
        boolean isLeaf;
        if (p.son[0] == null) {
            isLeaf = true;
        } else {
            isLeaf = false;
        }
        if (isLeaf) { //delete the k'th key
            for (i = k; i < p.keynum - 1; i++) {
                p.key[i] = p.key[i + 1];
            }
            p.keynum--;

            if (p == root) {
                return;
            }

        }
        if (!isLeaf) { // Find the right-most on the left son
            Node pp = p;
            fp = p;
            p = p.son[k];
            while (p != null) {
                sp.p = p;
                sp.k = p.keynum;
                st.push(sp);
                fp = p;
                p = p.son[p.keynum];
            }
            //fp is a leaf, the key of the right-most node will replace x
            pp.key[k] = fp.key[fp.keynum - 1];
            fp.keynum--;
        }

        //Start to rearrange the tree.
        sp = st.pop();
        fp = sp.p;
        k = sp.k;
        if (fp.keynum >= M) {
            return;// fp is not underflow
        }
        if (fp == root) { // the tree contains only root
            if (fp.keynum == 0) {
                root = null;
            }
            return;
        }
        while (!st.isEmpty()) {
            p = fp;
            if (p == root || !p.isUnderFlow()) {
                return;
            }
            sp = st.pop();
            fp = sp.p;
            k = sp.k;
            merge(p, fp, k);
            if (fp.keynum >= M) {
                return;//fp is not underflow
            }
            if (fp == root) {// If the root does not contain key then remove it 
                if (fp.keynum == 0) {
                    root = p;
                    return;
                }
                return;
            }
        }
    }
//============================================================================

    void insertMany(Entry[] a) {
        for (int i = 0; i < a.length; i++) {
            insert(a[i]);
        }
    }
}

class Entry<T> implements Comparable<Entry> {

    String key;
    T value;

    public Entry(String key, T value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    public void setValue(T x) {
        this.value = x;
    }

    @Override
    public int compareTo(Entry o) {
        return key.compareTo(o.getKey());
    }
}
//============================================================================
// Continue to go on the son[k] of the node p,

class SNode<T> {

    BTree.Node p;
    int k;
}
