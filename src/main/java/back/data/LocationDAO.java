package back.data;

import java.util.List;

public interface LocationDAO {
    List<String> getLocationNamesByStartOfName(String substring);
}
