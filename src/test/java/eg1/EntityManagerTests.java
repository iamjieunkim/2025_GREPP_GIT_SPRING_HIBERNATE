package eg1;

import io.jieun.domain.eg1.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import util.TestUtils;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static util.TestUtils.executeCommit;

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
        /*
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
         */
        //TestUtils.executeCommit()인데 단축키 눌러 import해줘서 이렇게 쓸 수 있도록 함
        executeCommit(entityManager, () ->{
            Member member = genMember(genMemberName());
            entityManager.persist(member);
        });

    }

    private Member genMember(String memberName) {
        return Member.builder()
                .id(memberName)
                .name(memberName)
                .build();
    }

    private static String genMemberName() {
        return "member" + TestUtils.genNumStr();
    }

    @Test
    @DisplayName("Select Test")
    void select_test() throws Exception {

        Member member = genMember(genMemberName());

        executeCommit(entityManager, () -> {

            entityManager.persist(member); //이때쯤에 영속성 컨택스트안에 들어감 -> manage상태 -> 아직 커밋안됐으니, 1차 캐시안에 든걸 반환

            Member findMember = entityManager.find(Member.class, member.getId());//where문을 만들어서 조회를 해온다.

            assertThat(findMember).isEqualTo(member);//1차 캐시, 아직 커밋을 안했기 때문에 안넘었갔고, 저장했다치고 넘겨주는 것임

            log.info("member = {}", member);
            log.info("findMember = {}", findMember);

        });

        executeCommit(entityManager, () -> {

            Member findMember = entityManager.find(Member.class, member.getId()); //새롭게 select 쿼리가 나감
            assertThat(findMember.getId()).isEqualTo(member.getId());

            findMember.setName("ADMIN");

        });

        executeCommit(entityManager, () -> {
            Member findMember = entityManager.find(Member.class, member.getId()); //새롭게 select 쿼리가 나감
            assertThat(findMember.getName()).isEqualTo("ADMIN");

            entityManager.detach(findMember); //영속화 되어있던 상태를 준영속화를 시켰다.
            findMember.setName("MEMBER");

        });

        executeCommit(entityManager, () -> {
            Member findMember = entityManager.find(Member.class, member.getId()); //새롭게 select 쿼리가 나감
            //entityManager.merge(member); //만약 detach를 영속성으로 바꿔서 사용하고 싶다고 merge하면됨

            assertThat(findMember.getName()).isNotEqualTo("MEMBER");
            assertThat(findMember.getName()).isEqualTo("ADMIN");

        });

    }

    @Test
    @DisplayName("Write-behind Test")
    void write_behind_test() throws Exception {

        executeCommit(entityManager, () -> {

            Member member1 = genMember(genMemberName());
            Member member2 = genMember(genMemberName());

            entityManager.persist(member1);
            entityManager.persist(member2);

            log.info("아직 쿼리가 실행되지 않았습니다!");

        });

    }

}
