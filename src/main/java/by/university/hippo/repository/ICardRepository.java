package by.university.hippo.repository;

import by.university.hippo.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICardRepository extends JpaRepository<Card, Long> {
}
