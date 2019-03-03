package site.razz.service.garage;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NexxGarageEventRepository extends MongoRepository<NexxGarageEvent, String> {

  @Query("{ 'date' : ?0 }")
  List<NexxGarageEvent> findByDate(long date);
}
