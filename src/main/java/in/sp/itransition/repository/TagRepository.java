package in.sp.itransition.repository;

import in.sp.itransition.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    // Custom query to find a tag by its name
    Optional<Tag> findByName(String name);
}
