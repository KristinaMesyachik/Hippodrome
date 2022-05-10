package by.university.hippo.repository;

import by.university.hippo.entity.PriceList;
import by.university.hippo.entity.Service;
import by.university.hippo.entity.enums.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface IServiceRepository extends JpaRepository<Service, Long> {
    List<Service> findByEnabledIs(int enabled);

    @Query("SELECT t FROM Service t INNER JOIN t.priceList p where t.time = ?1 AND t.place = ?2 AND p = ?3")
    Service findByTimeAndPlaceAndPriceList(LocalDateTime time, Place place, PriceList priceList);
}
