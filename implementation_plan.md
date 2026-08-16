# Implementation Plan - Convert E-Commerce to Spring MVC

This plan describes the process of migrating the E-Commerce CLI application into a Spring MVC Web Application inside a new project folder named `EcommerceSpringMVC`, utilizing the architecture and database configurations from the trainer's `SpringMVCProject` demo.

---

## User Review Required

> [!IMPORTANT]
> 1. **Project Directory:** We will create a brand new project directory named `EcommerceSpringMVC` in the workspace root.
> 2. **PostgreSQL Configuration:** We will set up the Spring DataSource to connect to the local PostgreSQL database (`ecommerce_db`) using user `postgres` and password `PG@1234`.
> 3. **40% Scope Target:** We will implement the core business flow:
>    * Customer Registration & Login.
>    * Product Browsing (displaying available products).
>    * Placing an Order (inputting quantity, executing stock/credit validation, and updating the database).
>    * Generating Order Receipt.

---

## Proposed Changes

### Configuration Files

#### [NEW] [web.xml](file:///e:/CT/Assignment3/EcommerceSpringMVC/src/main/webapp/WEB-INF/web.xml)
* Configure DispatcherServlet named `mvc` mapped to `/webmvc/*`.

#### [NEW] [mvc-servlet.xml](file:///e:/CT/Assignment3/EcommerceSpringMVC/src/main/webapp/WEB-INF/mvc-servlet.xml)
* Set up prefix `/WEB-INF/pages/` and suffix `.jsp` for view resolution.
* Declare `DriverManagerDataSource` bean connecting to the PostgreSQL database.
* Configure component-scan for `com.ecommerce.*`.
* Enable `<mvc:annotation-driven />`.

---

### Model & DAO (Repository) Layer

We will copy the models and convert the DAOs into Spring-managed Repositories:

#### [NEW] Model Classes
* Copy `Customer`, `Product`, `Order`, `OrderDetails`, `Invoice` to `com.ecommerce.model`.

#### [NEW] Repository Classes (`com.ecommerce.repository`)
* Annotate `JdbcCustomerDao`, `JdbcProductDao`, and `JdbcOrderDao` with `@Repository`.
* Autowire the `javax.sql.DataSource` bean to get database connections (`dataSource.getConnection()`) dynamically instead of calling `DatabaseConnection` directly.

---

### Service Layer (`com.ecommerce.service`)

* Convert `DefaultCustomerService`, `DefaultProductService`, and `DefaultOrderService` into Spring-managed services using `@Service`.
* Autowire repositories/DAOs into these services.

---

### Controller Layer (`com.ecommerce.controller`)

We will replace the CLI controllers with Spring MVC Controllers:

#### [NEW] [CustomerController.java](file:///e:/CT/Assignment3/EcommerceSpringMVC/src/main/java/com/ecommerce/controller/CustomerController.java)
* `@GetMapping("/login")`: Renders `login.jsp`.
* `@PostMapping("/login")`: Validates credentials, saves the customer object in the session, and redirects to the Customer Portal (`portal.jsp`).
* `@GetMapping("/register")`: Renders `register.jsp`.
* `@PostMapping("/register")`: Registers a new customer and redirects to login.

#### [NEW] [ProductController.java](file:///e:/CT/Assignment3/EcommerceSpringMVC/src/main/java/com/ecommerce/controller/ProductController.java)
* `@GetMapping("/list")`: Retrieves available products and lists them on `products.jsp`.

#### [NEW] [OrderController.java](file:///e:/CT/Assignment3/EcommerceSpringMVC/src/main/java/com/ecommerce/controller/OrderController.java)
* `@GetMapping("/checkout")`: Renders a simple form to confirm product quantity selection.
* `@PostMapping("/place")`: Performs validation (stock & credit checks) and places an order. Renders `receipt.jsp` on success, or redirects with error message on failure.

---

### JSP Views (`EcommerceSpringMVC/src/main/webapp/WEB-INF/pages`)

#### [NEW] JSP Files
* `login.jsp`: Email and password input form.
* `register.jsp`: Form for registering Customer ID, Email, Password, and Initial Credit.
* `portal.jsp`: User portal landing page showing credit balance and navigation links.
* `products.jsp`: Tabular list of products with links to check out.
* `checkout.jsp`: Simple order form displaying selected product price and stock, with quantity input.
* `receipt.jsp`: Confirmation page showing the details of the newly created order.

---

## Verification Plan

### Manual Verification
1. Deploy the compiled WAR file or run the project in Eclipse on Tomcat Server.
2. Access `http://localhost:8080/EcommerceSpringMVC/webmvc/customer/login` in the browser.
3. Test Customer Registration (`/customer/register`) and verify insertion in PostgreSQL.
4. Test Customer Login, Product Browsing, and Order Placement, validating that the product stock and customer credit are updated in pgAdmin / PostgreSQL.
