package upir.firstappandroid;

import java.util.List;

/**
 * Created by vital on 27/10/2015.
 */
public interface StoreService {

    public List<Store> getAllStore();
    public Store getStoreById(int id);
    public Store getStoreByName(String name);

    public int save();

    public void delete(int id);

}
