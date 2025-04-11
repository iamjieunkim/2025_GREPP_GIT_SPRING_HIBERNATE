package eg2;

import io.jieun.domain.eg2._3.GymMemberShip;
import io.jieun.domain.eg2._3.Level;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;
import util.TestUtils;

public class EntityObjTests {

    static EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    @BeforeAll
    static void init() {
        entityManagerFactory =
                Persistence.createEntityManagerFactory("grepp-hibernate-exp1");
    }

    @BeforeEach
    void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterEach
    void close() {
        entityManager.close();
    }

    @AfterAll
    static void tearDown() {
        entityManagerFactory.close();
    }

    @Test
    @DisplayName("TABLE STRATEGY_TEST")
    void table_stg_test() throws Exception {

    }

    @Test
    @DisplayName("ENUM TEST")
    void enum_test() throws Exception {

        TestUtils.executeCommit(entityManager, () -> {

            GymMemberShip memberShip1 = new GymMemberShip();
            memberShip1.setMembershipLevel(Level.GOLD);

            GymMemberShip memberShip2 = new GymMemberShip();
            memberShip2.setMembershipLevel(Level.SILVER);

            GymMemberShip memberShip3 = new GymMemberShip();
            memberShip3.setMembershipLevel(Level.GENERAL);

            entityManager.persist(memberShip1);
            entityManager.persist(memberShip2);
            entityManager.persist(memberShip3);

        });

    }

}
