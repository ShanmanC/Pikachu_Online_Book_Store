Design Decisions
my online book store has these main functions.
1. Home page
    Users can browse the books, select book category and search the books via keywords. Besides, there are two buttons under each book cover, one is used to add book(s) to shopping cart, the other is used to check book details and user comments.
2. Register, Login and Reset Password
   Visitors are only allowed to register a normal customer account. Normal customers will jump back to the previous page after login. Administrator and Partners can access their functional pages(Analytics.jspx and Partner.jspx) after login. These two types of users cannot be registered. All kinds of users are allowed to reset their password via entering correct user information.
3. Book Detail page
  This page is generated by the program. After user click the “Detail” button under a specific book, servlet will receive the request, acquire the book information from database and generate the corresponding web page.
4. Shopping-Cart
  Visitors and normal customers can check their shopping cart, update the book quantity or delete the book(s).
5. Payment
  Users must login before check out. When accessing payment page, users must enter correct payment information(i.e. Credit card information). System will acquire the default shipping and billing address from database, which is the address user entered when they registered. Users are allowed to update their shipping address or billing address in payment page.
6. Order Summary
  After successfully pay for the order, users can get an order summary and the shopping-cart will be emptied. Otherwise, the order will be denied, items in shopping-cart can be remained.
7. Analytics
  Administrators can go to Analytics page after login, they can get three kinds of reports: Sales history, top books and sales statistics.
8. Product Catalog
  External partners can go to Partner page after login, they can get detailed product information as an XML file and get a list of orders by part number.

UI Design
We want the website is friendly to users, so we focused much on the UI design to make the layout more clear and pretty. Our CSS codes can provide many special effects.
Restriction on use
In order to guide users to use our products correctly, strict JS restrictions are needed. Users’ every input value should be validated by JS before being sent to the server. For example, when user register, the length and format of password will be checked by JS, and system will pop-up an alert to users if the password they set is invalid.

In addition, users are not allowed to access the web content by typing the name of servlet or jsp path directly, except home page and shopping-cart page. Such operation is handled in java. For example, users will be blocked when they are trying to type an URL like: “​http://localhost:8080/Pikachu_Online_Book_Store/Payment.jspx​” or “​http://localhost:8080/Pikachu_Online_Book_Store/RegisterAndLogin​” Where Payment.jspx is the jsp file in WebContent folder, RegisterAndLogin is the servlet class name that control the register, login and reset password functions.


Java Coding
We use MVC pattern as our main pattern. And there are three main type of classes: controller(servlet), model and bean. Besides, we also have listener and filter classes to complete the special functions.
  
 Analytics.java
    The servlet to control Analytics page for administrator.
 CheckBookDetails.java
    The servlet to generate the content of bookDetail page for each book.
 Payment.java
    The servlet to control the payment function and generate order summary.
 RegisterAndLogin.java
   The servlet to control the login and register functions.
 Start.java
   The servlet to control the home page.
 UpdateShoppingCart.java
   The servlet to control shopping-cart page.
 
 src/bean
 AddressBean.java
    The class to instantiate address object.
 BookBean.java
     The class to instantiate book address object.
 PoBean.java
    The class to instantiate PO object.
 PoItemBean.java
    The class to instantiate POItem object.
 ReviewBean.java
    The class to instantiate book review object.
 StatisticsBean.java
    The class to instantiate statistics object.
 UserInfoBean.java
    The class to instantiate user object.
 VisitEventBean.java
    The class to instantiate visit event object.
  
 src/filter
 StatisticsFilter.java
    Provide the annomized reports in Analytics page. Hide user names.
 BlockJxps.java
    Block users to access the content by typing
 
 web file names(e.g. Payment.jspx) in URL directly.
 src/listener
 MostPopularBook.java
    Provide real time analytics with most popular 3 books.
  
 src/model
 OnlineBookStore.java
    The main model class.
 AddressDAO.java
    The class to retrieve data from ADDRESS database.
 BookDAO.java
    The class to retrieve data from BOOK database.
 PoDAO.java
    The class to retrieve data from PO database.
 PoItemDAO.java
    The class to retrieve data from POITEM database.
 ReviewDAO.java
    The class to retrieve data from REVIEW database.
 UserInfoDAO.java
   The class to retrieve data from USERINFO database.
 Trade-offs
1. To make the implementation on model side as clear as possible, we choose to create new tables(such as USERINFO and REVIEW) and add necessary columns(e.g. Add DATE column in PO table) in our database instead of using JOIN frequently while retrieving the tables. This decision makes our database more complicated, which might increase the response time and reduce the performance.
2. To make the UI more pretty and friendly, we have image items for books and web headers. Picture files increase the size of our application and loading pictures takes more time.
3. According to the design of database, USERINFO table and ADDRESS table are in one-to-one relation. Which means we only store one address for each user. For the users changes their shipping address frequently, they have to update the address each time they check out.
Limitation
1. Although we can restrict users to access the web content by typing the servlet class name or web file name directly in URL, they still can access the pages if they type a correct query. For instance, user will be blocked while input “​http://localhost:8080/Pikachu_Online_Book_Store/RegisterAndLogin​” But they can access the register page while input
 
“​http://localhost:8080/Pikachu_Online_Book_Store/RegisterAndLogin?go_to_register ='true'​” Therefore, to avoid such situation, we hide the attribute names by using POST method instead of GET. However, we cannot really restrict such illegal operation, we need to learn more about security.
2. Users cannot reset their password via email, only checking their register information is not a safe way.
