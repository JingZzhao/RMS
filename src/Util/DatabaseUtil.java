package Util;

import com.sun.rowset.CachedRowSetImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.entity.*;

import java.sql.*;
import java.time.LocalDate;

public class DatabaseUtil {

    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/retailerdatabase?serverTimezone=UTC";

    // database username and password
    private static final String USER = "root";
    private static final String PASS = "zjSQL!";

    private static Connection conn = null;

    public static void dbConnect() throws SQLException, ClassNotFoundException {

        try {
            // register JDBC driver
            Class.forName(JDBC_DRIVER);
        }catch(ClassNotFoundException e) {
            System.out.print("Can't find mySql JDBC Driver!");
            e.printStackTrace();
            throw e;
        }

        // connecting
        System.out.println("Connecting...");
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        }catch(SQLException e){
            // JDBC error
            System.out.println("Connection Failed! Check output console: " + e);
            e.printStackTrace();
            throw e;
        }catch(Exception e){
            // Class.forName error
            e.printStackTrace();
        }
    }

    public static void dbDisconnect() throws SQLException {
        try {
            if(conn != null && !conn.isClosed())
            {
                conn.close();
                System.out.println("Closed database!");
            }
        }catch(SQLException e) {
            throw e;
        }
    }

    public static ResultSet dbExecuteQuery(String queryStmt) throws SQLException, ClassNotFoundException {
        ResultSet rs = null;
        Statement stmt = null;
        CachedRowSetImpl crs = null;
        try {
            dbConnect();
            // Query
            System.out.println(" Instantiation Statement object...");
            System.out.println("Select statement: " + queryStmt + "\n");

            stmt = conn.createStatement();
            rs = stmt.executeQuery(queryStmt);
            crs = new CachedRowSetImpl();
            crs.populate(rs);

        }catch(SQLException e){
            System.out.println("Execute query failed: " + e);
            throw e;
        }finally {
            if(rs != null) {
                rs.close();
            }
            if(stmt != null) {
                stmt.close();
            }

            dbDisconnect();
        }
        return crs;
    }

    public static void dbUpdateQuery(String sqlStmt) throws SQLException, ClassNotFoundException{
        Statement stmt = null;

        try {
            stmt = conn.createStatement();

            stmt.executeUpdate(sqlStmt);

        } catch (SQLException e) {
            System.out.println("Problem occurred at executeUpdate operation : " + e);
            throw e;
        } finally {
            if (stmt != null) {

                stmt.close();
            }
        }
    }

    /**
     * Check login customer
     * @param account
     * @param password
     * @return
     */
    public static boolean checkCustomer(String account, String password){
        boolean checkbool = false;
        ResultSet rs = null;
        try{
            dbConnect();

            String password_fromDb;
            String selectSql = "select customer_password from customer where email='"+account+"'";
            rs = dbExecuteQuery(selectSql);
            if(rs.next()){
                password_fromDb = rs.getString("customer_password");
                System.out.println("log: password=" + password_fromDb);
                if(password_fromDb.equals(password)){
                    checkbool = true;
                    //getCustomerId(account);
                }
            }

            dbDisconnect();

        }catch (Exception e){
            System.out.println("Check customer---MYSQL ERROR: " + e.getMessage());
        }
        return checkbool;
    }

    /**
     * Check login employee
     * @param account
     * @param password
     * @return
     */
    public static boolean checkEmployee(String account, String password){
        boolean checkbool = false;
        ResultSet rs = null;
        try{
            dbConnect();

            String password_fromDb;
            String selectSql = "select employee_password from employee where email = '" + account + "'";
            rs = dbExecuteQuery(selectSql);

            if(rs.next()){
                password_fromDb = rs.getString("employee_password");
                if(password_fromDb.equals(password)){
                    checkbool = true;
                }
            }
            dbDisconnect();
        }catch(Exception e){
            System.out.println("Check login user--MYSQL ERROR: " + e.getMessage());
        }
        return checkbool;
    }

    /**
     * Get product info
     * @param
     * @return
     */
    /*public static Product getProduct(String id){

    }*/


    /**
     * Get customer info
     * @param account
     * @return
     */
    public static Person getCustomer(String account){
        Person customer = null;
        ResultSet rs = null;
        try{
            dbConnect();

            String selectSql = "SELECT * FROM customer c NATURAL JOIN location l where c.email='"+account+"'";
            rs = dbExecuteQuery(selectSql);
            if(rs.next()){
                customer = new Person();
                customer.setId(rs.getInt("customer_id"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                customer.setGender(rs.getString("gender"));
                customer.setEmail(rs.getString("email"));
                customer.setPassword(rs.getString("customer_password"));
                customer.setStreet(rs.getString("address1") + " " + rs.getString("address2"));
                customer.setCity(rs.getString("city_name"));
                customer.setState(rs.getString("state_name"));
                customer.setCountry(rs.getString("country_name"));
                customer.setPostalCode(rs.getString("post_code"));
                customer.setType("Customer");
            }
            dbDisconnect();
            return customer;
        }catch (Exception e){
            System.out.println("Get customer---MYSQL ERROR: "+e.getMessage());
        }
        return customer;
    }

    /**
     * Get customer's credit cards
     * @param id
     * @return
     */
    public static ObservableList<CreditCard> getCreditCard(int id){
        final ObservableList<CreditCard> credit_cards = FXCollections.observableArrayList();
        ResultSet rs = null;
        try{
            dbConnect();
            CreditCard credit_card = null;

            String selectSql = "SELECT * FROM creditcard where customer_id=" + id;
            rs = dbExecuteQuery(selectSql);
            while(rs.next()){
                credit_card = new CreditCard();
                credit_card.setCredit_Card_Num(rs.getLong("card_number"));
                credit_card.setType(rs.getString("card_type"));
                credit_card.setExp_Date(rs.getString("expiration_time"));
                credit_cards.add(credit_card);
            }
            dbDisconnect();
            return credit_cards;

        }catch(Exception e){
            System.out.println("getCreditCard--MYSQL ERROR: " + e.getMessage());
        }
        return credit_cards;
    }

    public static ObservableList<Order> getOrder(int id){

        final ObservableList<Order> orders = FXCollections.observableArrayList();
        ResultSet rs = null;
        try{
            dbConnect();
            Order order = null;

            String selectSql = "select po.order_date, po.amount, po.upc_code, po.order_id, p.name, pt.name as type, b.name as brand, s.name as store "
                    +"from customer c join productorder po on c.customer_id = po.customer_id "
                    +"join product p on p.upc_code = po.upc_code "
                    +"join producttype pt on p.productType_id = pt.productType_id "
                    +"join brand b on b.brand_id = p.brand_id "
                    +"join store s on s.store_id = po.store_id "
                    +"where c.customer_id = '"+ id + "'";
            rs = dbExecuteQuery(selectSql);
            while(rs.next()){
                order = new Order();
                order.setOrder_id(rs.getInt(4));
                order.setUpc_code(rs.getString(3));
                order.setProduct_name(rs.getString(5));
                order.setBrand(rs.getString(7));
                order.setType(rs.getString(6));
                order.setAmount(rs.getInt(2));
                order.setOrder_date(rs.getDate(1));
                order.setStore(rs.getString(8));

                orders.add(order);
            }
            dbDisconnect();
            return orders;

        }catch(Exception e){
            System.out.println("getOrder--MYSQL ERROR: " + e.getMessage());
        }
        return orders;
    }


    /**
     * Get employee info
     * @param account
     * @return
     */
    public static Person getEmployee(String account){
        Person employee = null;
        ResultSet rs = null;
        try{
            dbConnect();

            String selectSql = "select * from employee where email = '"+account+"'";
            rs = dbExecuteQuery(selectSql);
            if(rs.next()){
                employee = new Person();
                employee.setId(rs.getInt("employee_id"));
                employee.setFirstName(rs.getString("first_name"));
                employee.setLastName(rs.getString("last_name"));
                employee.setGender(rs.getString("gender"));
                employee.setEmail(rs.getString("email"));
                employee.setPassword(rs.getString("employee_password"));
                employee.setType("Employee");

            }
            dbDisconnect();
            return employee;
        }catch (Exception e){
            System.out.println("Get customer---MYSQL ERROR: "+e.getMessage());
        }
        return employee;
    }

    /**
     * Get store info
     * @param account
     * @return
     */
    public static Store getStore(String account){
        Store store = null;
        ResultSet rs = null;
        try{
            dbConnect();

            String selectSql = "select * from employee e join store s on e.store_id = s.store_id "
                    +"join location l on s.location_id = l.location_id "
                    +"join capacity c on c.store_id = s.store_id "
                    +"where e.email = '"+account+"'";
            rs = dbExecuteQuery(selectSql);
            if(rs.next()){
                store = new Store();
                store.setStoreId(rs.getInt("store_id"));
                store.setStoreName(rs.getString("name"));
                store.setStreet(rs.getString("address1") + " " + rs.getString("address2"));
                store.setCity(rs.getString("city_name"));
                store.setState(rs.getString("state_name"));
                store.setCountry(rs.getString("country_name"));
                store.setPostalCode(rs.getString("post_code"));
                store.setOpenTime(rs.getString("open_time"));
                store.setCloseTime(rs.getString("close_time"));
                store.setCurrentNum(rs.getInt("current_amount"));
                store.setMaxNum(rs.getInt("max_amount"));
            }
            dbDisconnect();
            return store;
        }catch (Exception e){
            System.out.println("Get store---MYSQL ERROR: "+e.getMessage());
        }
        return store;
    }

    /**
     * Get store stock
     * @param id
     * @return
     */
    public static ObservableList<Product> getProduct(int id) {
        final ObservableList<Product> products = FXCollections.observableArrayList();
        ResultSet rs = null;
        try{
            dbConnect();
            Product product = null;

            String selectSql = "select i.upc_code, i.amount, i.store_price, p.name as product, pt.name as type, b.name as brand, su.supply_date, su.supply_price, v.name as vendor " +
                    "from inventory i join store st on st.store_id = i.store_id " +
                    "join product p on p.upc_code = i.upc_code " +
                    "join producttype pt on pt.productType_id = p.productType_id " +
                    "join brand b on b.brand_id = p.brand_id " +
                    "join supply su on su.upc_code = p.upc_code " +
                    "join vendor v on su.vendor_id = v.vendor_id " +
                    "where st.store_id in (select s.store_id from employee e join store s on e.store_id = s.store_id where employee_id = " + id + ") " +
                    "and su.store_id in (select s.store_id from employee e join store s on e.store_id = s.store_id where employee_id = " + id + ")";
            rs = dbExecuteQuery(selectSql);

            while(rs.next()){
                product = new Product();
                product.setUpc(rs.getString(1));
                product.setProduct(rs.getString(4));
                product.setType(rs.getString(5));
                product.setBrand(rs.getString(6));
                product.setPrice(rs.getDouble(3));
                product.setAmount(rs.getInt(2));
                product.setVendor(rs.getString(9));
                product.setLastedSupplyDate(rs.getString(7));
                product.setSupplyPrice(rs.getDouble(8));

                products.add(product);
            }
            dbDisconnect();
            return products;

        }catch(Exception e){
            System.out.println("Get product--MYSQL ERROR: " + e.getMessage());
        }
        return products;
    }

    /**
     * Edit customer's address information
     * @param street
     * @param city
     * @param state
     * @param country
     * @param postalCode
     * @param customerId
     */
    public static void updateAddress (String street, String city, String state, String country, String postalCode, int customerId){

        try{
            dbConnect();

            String updateSql = "update location set address1 = '" + street + "', city_name = '" + city + "', state_name = '" + state + "', country_name = '" + country
                    + "', post_code = '" + postalCode
                    + "' where location_id in (select location_id from customer where customer_id = '" + customerId + "')";
            dbUpdateQuery(updateSql);

            dbDisconnect();
        }catch (Exception e){
            System.out.println("Update Address---MYSQL ERROR: "+e.getMessage());
        }
    }

    /**
     * Add new credit card
     * @param cardNum
     * @param cardType
     * @param cardExpDate
     * @param customerId
     */
    public static void addCreditCard (Long cardNum, String cardType, String cardExpDate, int customerId){
        int id = 0;
        String selcSql = "select customer_id from customer where customer_id = '" + customerId + "'";
        try{
            ResultSet rs = dbExecuteQuery(selcSql);

            while(rs.next()){
                id = rs.getInt(1);
            }
        }catch (Exception e){
            System.out.println("Add new Credit Card: Get Customer ID---MYSQL ERROR: "+e.getMessage());
        }

        String insertSql = "insert into creditcard values (" + cardNum + ", '" + cardType + "', '" + cardExpDate + "', "
                + id + ")";
        try{
            dbConnect();
            dbUpdateQuery(insertSql);
            dbDisconnect();
        }catch (Exception e){
            System.out.println("Add new Credit Card---MYSQL ERROR: "+e.getMessage());
        }

    }

    /**
     * Delete selected credit card
     * @param cardNum
     */
    public static void deleteCreditCard(Long cardNum){
        String delSql = "delete from creditcard where card_number = '" + cardNum + "'";
        try{
            dbConnect();
            dbUpdateQuery(delSql);
            dbDisconnect();
        }catch (Exception e){
            System.out.println("Delete Credit Card---MYSQL ERROR: "+e.getMessage());
        }
    }

    /*public static void editCreditCard(Long cardNum, String cardType, String cardExpDate){
        String updateSql = "update creditcard set card_number = '" + cardNum + "', card_type = '" + cardType + "', expiration_time = '" + cardExpDate
                +"' where card_number = '" + cardNum + "'";
        try{
            dbConnect();
            dbUpdateQuery(updateSql);
            dbDisconnect();
        }catch (Exception e){
            System.out.println("Edit Credit Card---MYSQL ERROR: "+e.getMessage());
        }
    }*/
}
