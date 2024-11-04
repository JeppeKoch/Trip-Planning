package dat.daos;

import java.util.Set;

public interface ITripGuide<T> {
    void addGuideToTrip(Long tripId, Long guideId);
    Set<T> getTripsByGuide(Long guideId);
}
