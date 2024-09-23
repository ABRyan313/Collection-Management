package in.sp.itransition.service;

import in.sp.itransition.model.Collection;
import in.sp.itransition.repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CollectionService {

    @Autowired
    private CollectionRepository collectionRepository;

    public Set<Collection> getAllCollections() {
        return Set.copyOf(collectionRepository.findAll());
    }
    

    public Optional<Collection> getCollectionById(Long id) {
        return collectionRepository.findById(id);
    }

    public Collection saveCollection(Collection collection) {
        return collectionRepository.save(collection);
    }

    public void deleteCollection(Long id) {
        collectionRepository.deleteById(id);
    }

    public List<Collection> getTopCollections() {
        return collectionRepository.findTop5ByOrderByItemsSizeDesc();
    }
}
