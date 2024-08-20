package com.gogeteat.web.service;

import com.gogeteat.web.entity.Store;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class StoreServiceTest {

    @Autowired
    StoreService service;

    @Test
    void saveStore() {
        Store store = Store.builder()
                .storeName("testName")
                .address("testAddress")
                .tel("testTel")
                .x(1.0)
                .y(1.0)
                .build();

        Store savedStore = service.saveStore(store);

        assertThat(savedStore.getStoreName()).isEqualTo("testName");
    }

    @Test
    void getStore() {
        Store store = Store.builder()
                .storeName("testName")
                .address("testAddress")
                .tel("testTel")
                .x(1.0)
                .y(1.0)
                .build();

        Store savedStore = service.saveStore(store);
        Store result = service.getStore(savedStore.getStoreId());

        System.out.println(" result.getStoreId() = " + result.getStoreId());
        assertThat(result.getStoreName()).isEqualTo(store.getStoreName());
    }

    @Test
    void updateStore() {
        Store newStore = Store.builder()
                .storeName("testNewName")
                .address("testNewAddress")
                .tel("testNewTel")
                .x(1.0)
                .y(1.0)
                .build();

        Store updatedStore = service.updateStore(1L, newStore);

        assertThat(updatedStore.getStoreName()).isEqualTo(newStore.getStoreName());
    }

    @Test
    void deleteStore() {
    }
}