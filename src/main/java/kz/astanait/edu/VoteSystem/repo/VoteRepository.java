package kz.astanait.edu.VoteSystem.repo;

import kz.astanait.edu.VoteSystem.models.Vote;
import org.springframework.data.repository.CrudRepository;

public interface VoteRepository extends CrudRepository<Vote,Long> {
    Vote getById(Long id);
}
