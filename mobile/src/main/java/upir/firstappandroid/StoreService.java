package upir.firstappandroid;

import java.util.List;

/**
 * Created by vital on 27/10/2015.
 */
public interface StoreService {

    public List<Product> getAllStore();
    public Product getStoreById(long id);
    public Product getStoreByName(String name);

    public long save(Product product);
    public int update(Product product);

    public int delete(long id);

}
