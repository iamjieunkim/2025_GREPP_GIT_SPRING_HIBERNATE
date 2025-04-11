package util;

import io.jieun.domain.eg1.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TestUtils {

    public static String genNumStr() {
        int number = (int) (Math.random() * 100_000);
        return Integer.toString(number);
    }

    public static void executeCommit(EntityManager entityManager, Runnable action) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            action.run();
        } catch ( Exception e ) {
            transaction.rollback();
            log.error(e.getMessage(), e);
            e.printStackTrace();
        } finally {
            transaction.commit();
        }
    }

}
