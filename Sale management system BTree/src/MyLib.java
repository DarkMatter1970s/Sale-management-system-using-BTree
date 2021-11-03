import java.util.*;
import java.io.*;
public class MyLib {
  Random r;
  MyLib() {
    r = new Random();  
  }
  int rand(int u, int v) {
    if(u>v) return(-1);  
    int x = u + r.nextInt(v-u+1);
    return(x);
  }
  int [] createArray(int u, int v, int n) {
     int [] a = new int[n];
     for(int i=0;i<n;i++) a[i] = rand(u,v);
     return(a);
  }
  int [] createArrayUnique(int u, int v, int n) {
     HashSet<Integer> h = new HashSet<Integer>();
     int i,k;
     for(i=0;i<n;i++) {
      k = rand(u,v);
      h.add(k);
     }
     int m = h.size();
     int [] a = new int[m];
     Iterator g = h.iterator();
     i = 0;
     while(g.hasNext()) {
      k = (Integer) g.next();
      a[i++] = k;
     }
     return(a);
  }

  void display(int [] a) {
    int n,m,i;
    n = a.length;
    if(n < 50)
        m = n;
       else {
        m = 50;
        System.out.println("\nList of first " + m + " elements of the array are:");
      }

    for(i=0;i<m;i++) System.out.print(a[i] + " ");
    System.out.println();
  }

  void saveFile(String fname, int [] a) {
    RandomAccessFile f;
    try {
      f = new RandomAccessFile(fname,"rw");
      for(int i=0;i<a.length;i++) f.writeInt(a[i]);
      f.close();
    }
    catch(Exception e) { }
  }
  void saveTextFile(String fname, int [] a) {
    RandomAccessFile f;
    int i,j;
    try {
      f = new RandomAccessFile(fname,"rw");
      j=0;
      for(i=0;i<a.length;i++) {
          f.writeBytes(" " + a[i]);
          j++;
          if(j%10==0) f.writeBytes("\r\n");
      }
      f.close();
    }
    catch(Exception e) { }
  }
  int [] loadFile(String fname) {
     RandomAccessFile f;
     ArrayList<Integer> t = new ArrayList<Integer>();
     int k,i,n;
     try {
       f = new RandomAccessFile(fname,"r");
       while(true) {
         k = f.readInt();
         if(k==-1) break;
         t.add(k);
       }
       f.close();
     }
     catch(Exception e) { }
     n = t.size();
     int [] a = new int[n];
     for(i=0;i<n;i++) a[i] = t.get(i);
     return(a);
  }
  int [] loadTextFile(String fname) {
     RandomAccessFile f;
     ArrayList<Integer> t = new ArrayList<Integer>();
     String s;
     int k,i,n; String [] u;String x;
     try {
       f = new RandomAccessFile(fname,"r");
       while(true) {
         s = f.readLine();
         if(s==null) break;
         u = s.split("[ ]+");
         for(i=0;i<u.length;i++) {
          x = u[i].trim();
          if(x.equals("")) continue;
          k = Integer.parseInt(x);
          t.add(k);
         }
       }
       f.close();
     }
     catch(Exception e) { }
     n = t.size();
     int [] a = new int[n];
     for(i=0;i<n;i++) a[i] = t.get(i);
     return(a);
  }
  void selectSort(int [] a) {
     int i,j,k,x,n;
     n = a.length;
     for(i=0;i<n-1;i++) {
       k = i; x = a[i];
       for(j=i+1;j<n;j++) {
        if(a[j]<x) {
        x = a[j];
        k = j;
       }  
      }
      if(i!=k) {
      x=a[i]; a[i]=a[k]; a[k]=x;  
     }
    }
   }
  void testRunningTime() {
     int n,M;
     Calendar t; long t1,t2;
     Scanner s = new Scanner(System.in);
     System.out.print("Enter the size of the array: ");
     n = s.nextInt();
     s.nextLine();
     M = 5*n; 
     int [] a = createArray(0,M,n);
     display(a);
     t = Calendar.getInstance();
     t1 = t.getTimeInMillis();
     selectSort(a);
     t = Calendar.getInstance();
     t2 = t.getTimeInMillis();
     display(a);
     System.out.println("Selection sort running time in milli seconds = " + (t2-t1));
     System.out.println();
  }
}
