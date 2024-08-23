package in.sp.itransition.repository;

import in.sp.itransition.model.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {

    // Custom query to find the top 5 largest collections based on the number of items
    @Query("SELECT c FROM Collection c ORDER BY SIZE(c.items) DESC")
    List<Collection> findTop5ByOrderByItemsSizeDesc();
}
