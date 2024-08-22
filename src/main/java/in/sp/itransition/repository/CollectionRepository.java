package in.sp.itransition.repository;

import in.sp.itransition.model.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {

    // Custom method to find the top 5 largest collections by the number of items
    List<Collection> findTop5ByOrderByItemsSizeDesc();
}
