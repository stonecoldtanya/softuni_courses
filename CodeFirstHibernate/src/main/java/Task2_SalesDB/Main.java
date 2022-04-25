package Task2_SalesDB;

import Task2_SalesDB.entities.Customer;
import Task2_SalesDB.entities.Product;
import Task2_SalesDB.entities.Sale;
import Task2_SalesDB.entities.StoreLocation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory =
                Persistence.createEntityManagerFactory("CodeFirstEx");

        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        Product product = new Product("GOONG SECRET CALMING BATH", 20, new BigDecimal("742.47"));
        Customer customer = new Customer("Natalie Poli", "poli@email.kekw", "YEPPERS");
        StoreLocation location = new StoreLocation("you wish you knew it");
        Sale sale = new Sale(product, customer, location, LocalDateTime.now());

        entityManager.persist(product);
        entityManager.persist(customer);
        entityManager.persist(location);
        entityManager.persist(sale);

    }
}
