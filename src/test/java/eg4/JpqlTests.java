package eg4;

import io.jieun.domain.eg4._1.Coffee;
import io.jieun.domain.eg4._1.CoffeeDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import util.TestUtils;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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

        executeCommit(entityManager, () -> {
            Coffee coffee = Coffee.builder()
                    .name("아메리카노")
                    .amount(1)
                    .price(1500)
                    .build();

            entityManager.persist(coffee);

            coffee = Coffee.builder()
                    .name("바닐라라떼")
                    .amount(1)
                    .price(2000)
                    .build();

            entityManager.persist(coffee);

            coffee = Coffee.builder()
                    .name("아포가토")
                    .amount(1)
                    .price(3000)
                    .build();

            entityManager.persist(coffee);

            coffee = Coffee.builder()
                    .name("말차라떼")
                    .amount(1)
                    .price(3500)
                    .build();

            entityManager.persist(coffee);

            coffee = Coffee.builder()
                    .name("밀크티")
                    .amount(1)
                    .price(3500)
                    .build();

            entityManager.persist(coffee);

            coffee = Coffee.builder()
                    .name("카푸치노")
                    .amount(1)
                    .price(3500)
                    .build();

            entityManager.persist(coffee);

            coffee = Coffee.builder()
                    .name("아인슈페너")
                    .amount(1)
                    .price(3500)
                    .build();

            entityManager.persist(coffee);

            coffee = Coffee.builder()
                    .name("슈크림라떼")
                    .amount(1)
                    .price(3500)
                    .build();

            entityManager.persist(coffee);

            coffee = Coffee.builder()
                    .name("카페모카")
                    .amount(1)
                    .price(3500)
                    .build();

            entityManager.persist(coffee);

            coffee = Coffee.builder()
                    .name("콜드브루")
                    .amount(1)
                    .price(3500)
                    .build();

            entityManager.persist(coffee);
        });

        executeCommit(entityManager, () -> {

            // select * from coffee c
            String jpql = "select c from Coffee c";

            List<Coffee> coffeeList = entityManager.createQuery(jpql, Coffee.class).getResultList();

            log.info("====== 커피 목록 ======");

            for (Coffee coffee : coffeeList) {
                log.info("coffee.getName() = {}", coffee.getName());
            }

            log.info("======================");

        });

        executeCommit(entityManager, () -> {

            // select c.coffee_name from coffee c
            String jpql = "select c.name from Coffee c";
            List<String> coffeeNameList = entityManager.createQuery(jpql, String.class).getResultList();

            log.info("====== 커피 이름 목록 ======");

            for (String coffeeName : coffeeNameList) {
                log.info("coffeeName = {}", coffeeName);
            }

            log.info("======================");

        });

        executeCommit(entityManager, () -> {

            String jpql = "select c from Coffee c where c.name = :name";
            String jpql2 = "select c from Coffee c where c.name = ?1";

            TypedQuery<Coffee> query = entityManager.createQuery(jpql, Coffee.class);
            query.setParameter("name", "아메리카노");

            TypedQuery<Coffee> query2 = entityManager.createQuery(jpql2, Coffee.class);
            query2.setParameter(1, "아메리카노");

//            List<Coffee> resultList = query.getResultList();
//            resultList.get(0);

            Coffee 아메리카노 = query.getSingleResult();
            Coffee 아메리카노2 = query2.getSingleResult();

            log.info("====== 아메리카노 가격 ======");
            log.info("아메리카노.getPrice() = {}", 아메리카노.getPrice());
            log.info("========================");

            log.info("====== 아메리카노2 가격 ======");
            log.info("아메리카노2.getPrice() = {}", 아메리카노2.getPrice());
            log.info("========================");

        });


        executeCommit(entityManager, () -> {

//            String jpql = "select c.name, c.price from Coffee c";
//
//            List<Object[]> resultList = entityManager.createQuery(jpql).getResultList();
//
//            for (Object[] row : resultList) {
//                String name = (String) row[0];
//                Integer price = (Integer) row[1];
////                log.info("name = {}", name);
////                log.info("price = {}", price);
//                CoffeeDto coffeeDto = new CoffeeDto(name, price);
//                log.info("coffeeDto = {}", coffeeDto);
//            }

            String jpql = "select new io.jieun.domain.eg4._1.CoffeeDto(c.name, c.price) from Coffee c";

            List<CoffeeDto> resultList = entityManager.createQuery(jpql, CoffeeDto.class).getResultList();

            log.info("====== 커피 목록 ======");
            for (CoffeeDto coffeeDto : resultList) {

                log.info("{} : {}", coffeeDto.getName(), coffeeDto.getPrice());

            }
            log.info("======================");


        });

        executeCommit(entityManager, () -> {

            String jpql = "select c from Coffee c";

            TypedQuery<Coffee> query = entityManager.createQuery(jpql, Coffee.class);

            query.setFirstResult(5); // OFFSET
            query.setMaxResults(5); // LIMIT

            List<Coffee> resultList = query.getResultList();

            assertThat(resultList.size()).isEqualTo(5);

            for (Coffee coffee : resultList) {
                log.info("coffee.getName() = {}", coffee.getName());
            }

        });

    }



}
