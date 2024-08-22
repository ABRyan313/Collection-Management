package in.sp.itransition.repository;



import in.sp.itransition.model.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<Collection, Long> {
    // Custom query methods can be added here
}
