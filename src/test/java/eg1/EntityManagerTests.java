package eg1;

import io.jieun.domain.eg1.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import util.TestUtils;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
public class EntityManagerTests {

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
    @DisplayName("")
    void test1() throws Exception {

        Map<String, Object> properties = entityManagerFactory.getProperties();

        String url = properties.get("jakarta.persistence.jdbc.url").toString();
        String driver = properties.get("jakarta.persistence.jdbc.driver").toString();

        log.info("url = {}", url);
        log.info("driver = {}", driver);

        assertThat(url).isEqualTo("jdbc:mysql://localhost:3306/grepp_hibernate_test");
        assertThat(driver).isEqualTo("com.mysql.cj.jdbc.Driver");

    }

    @Test
    @DisplayName("Save test")
    void save_test() throws Exception {

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {

            // Transient
            Member member = genMember(genMemberName());

            // Managed
            entityManager.persist(member);

        } catch ( Exception e ) {
            transaction.rollback();
        } finally {
            transaction.commit();
        }




    }

    private Member genMember(String memberName) {
        return Member.builder()
                .id(1L)
                .name(memberName)
                .build();
    }

    private static String genMemberName() {
        return "member" + TestUtils.genNumStr();
    }

}
