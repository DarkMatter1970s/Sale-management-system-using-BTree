# Sale-management-system-using-BTree
## Knowledge require
- Java
- B-tree data structure (I attach the slides about this structure in [04B-B-tree.ppt](https://github.com/DarkMatter1970s/Sale-management-system-using-BTree/blob/main/04B-B-tree.ppt))
## Details
The folder [Sale management system BTree](https://github.com/DarkMatter1970s/Sale-management-system-using-BTree/tree/main/Sale%20management%20system%20BTree) is a whole netbeans project (here I used Netbeans IDE 8.2).
Functions in this program:
- For products:
    - Adding new product
    - Search a product with productID
    - Update product information (except productID)
    - Delete product from database
    - Traverse all productIDs
- For orders:
    - Create an order
    - Search an order with orderID
    - Update an order
    - Delete an order from database
    - Traverse all OrderID
 
Each order contains 1 or many products so that handling the amount/quantity of a product when adding to or remove from an order is important.
 
The 2 files product_data.txt and order_data.txt are 2 files to save all the information of products and orders. They are read when the program starts and saved when it terminates.
