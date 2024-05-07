package com.github.springprac.respository.storeSales;

public interface StoreSalesRepository {
    StoreSales findStoreSalesById(Integer storeId);

    void updateSalesAmount(Integer storeId, Integer stock);
}
