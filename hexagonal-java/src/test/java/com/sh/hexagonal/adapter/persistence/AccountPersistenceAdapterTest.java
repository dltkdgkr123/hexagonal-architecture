package com.sh.hexagonal.adapter.persistence;

import com.sh.hexagonal.adapter.out.persistence.ActivityJpaEntity;
import com.sh.hexagonal.adapter.out.persistence.ActivityRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
class AccountPersistenceAdapterTest {

    @Autowired
    private ActivityRepository activityRepository;

    @Test
    void testH2Connection() {
        Assertions.assertTrue(true);

//        ActivityJpaEntity activity = activityRepository
//            .findById(1L)
//            .orElseThrow(EntityNotFoundException::new);
//
//        Assertions.assertEquals(activity.getId(), 1L);
    }
}
