package by.university.hippo.repository;

import by.university.hippo.entity.PriceList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPriceListRepository extends JpaRepository<PriceList, Long> {

    List<PriceList> findByEnabledIs(int enabled);
//    List<PriceList> findAllByOrderByTitleAsc();
}
