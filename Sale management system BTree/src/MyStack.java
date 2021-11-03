import java.util.*;
public class MyStack<T> {
  LinkedList<T> t;
  MyStack() {
      t = new LinkedList<T>();
    }
  void clear() {
      t.clear();
    }
  boolean isEmpty() {
      return(t.isEmpty());
    }
  void push(T x) {
      t.addFirst(x);
  }
  T pop() { 
    if(isEmpty()) return(null);
      return(t.removeFirst());
    }
  T top() { 
    if(isEmpty()) return(null);
    return(t.getFirst());
   }  
}
