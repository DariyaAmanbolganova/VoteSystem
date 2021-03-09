package kz.astanait.edu.VoteSystem.repo;

import kz.astanait.edu.VoteSystem.models.Answer;
import org.springframework.data.repository.CrudRepository;

public interface AnswerRepository extends CrudRepository<Answer,Long> {
    Answer getById(Long id);
}
