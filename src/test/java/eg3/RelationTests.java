package eg3;

import io.jieun.domain.eg3._1.Player;
import io.jieun.domain.eg3._1.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import util.TestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static util.TestUtils.executeCommit;

@Slf4j
public class RelationTests {

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
    @DisplayName("test1")
    void test_1() throws Exception {


        //TestUtils.executeCommit(entityManager, () -> {
            /*
            select *
            from players p
            left join teams t on t.team_id = p.team_id
            where t.name = '한화_이글스';
            */
//            Player findPlayer = entityManager.find(Player.class, 1);
//            Team findTeam = findPlayer.getTeam();

            /*
            select *
            from teams t
            left join players p on t.team_id = p.team_id
            where t.name = '한화_이글스';
             */
            //Team hw_e = entityManager.find(Team.class, "한화_이글스"); //방향성 문제 발생
        //});

        List<Player> bus = new ArrayList<>();

        executeCommit(entityManager, ()->{

            Player player = entityManager.find(Player.class, 1);
            Team team = player.getTeam();

            log.info("team.getName() = {}", team.getName());

            bus.add(player);

        });

        Player findPlayer = bus.get(0);

        log.info("findPlayer.getName() = {}", findPlayer.getName());
        //log.info("findPlayer.getTeam().getName() = {}", findPlayer.getTeam().getName());

    }

    @Test
    @DisplayName("proxy obj")
    void proxy_obj_test() throws Exception {

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Player player = null;

        try {

            player = entityManager.find(Player.class, 1);
            log.info("player.getName() = {}", player.getName());

            entityManager.detach(player); //준영속성 상태로 만들었다.

            transaction.commit();

        } catch (Exception e){
            transaction.rollback();
        }

        entityManager.close(); //강제로 영속성 컨텍스트를 종료시키겠다.

        Team team = player.getTeam();
        log.info("team = {}", team);

        assertThatThrownBy(
                () -> {
                    team.getName();
                }
        ).isInstanceOf(LayerInstantiationException.class);

    }

    @Test
    @DisplayName("getReference")
    void get_reference_exception_test() throws Exception {

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Player player = null;

        try{

            player = entityManager.getReference(Player.class, 1); //엔티티 타입의 프록시 객체를 만들어서 할당

        } catch (Exception e){
            transaction.rollback();
        }

        transaction.commit();
        //entityManager.close();

        log.info("player.getName() = {}", player.getName());

    }

    @Test
    @DisplayName("proxy check")
    void proxy_check() throws Exception {

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Player player = null;

        try{

            player = entityManager.getReference(Player.class, 1); //엔티티 타입의 프록시 객체를 만들어서 할당

        } catch (Exception e){
            transaction.rollback();
        }

        transaction.commit();
        entityManager.close();

        boolean result = entityManagerFactory.getPersistenceUnitUtil().isLoaded(player);
        log.info("result = {}", result);

        player.getName();

    }
}
