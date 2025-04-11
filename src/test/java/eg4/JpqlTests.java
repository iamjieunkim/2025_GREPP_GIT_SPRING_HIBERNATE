package eg4;

import io.jieun.domain.eg4._1.Coffee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import util.TestUtils;

import static util.TestUtils.executeCommit;

@Slf4j
public class JpqlTests {

    static EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    @BeforeAll
    static void init() {
        entityManagerFactory =
                Persistence.createEntityManagerFactory("grepp-hibernate-exp2");
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
    @DisplayName("jpql select test")
    void jpql_select_test() throws Exception {

        // 아메리카노, 1, 1500를 추가하는 코드
        executeCommit(entityManager, () -> {

            Coffee coffee = Coffee.builder()
                    .name("아메리카노")
                    .amount(1)
                    .price(1500)
                    .build();

            entityManager.persist(coffee);
        });


    }

}
