package kz.astanait.edu.VoteSystem.repo;

import kz.astanait.edu.VoteSystem.models.Answer;
import kz.astanait.edu.VoteSystem.models.VoteDB;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface VoteDBRepository extends CrudRepository<VoteDB,Long> {
    @Transactional
    void deleteAllByAnswerId(Long answerId);

    Boolean existsByVoteIdAndUserId(Long voteId,Long UserId);

    VoteDB getByUserIdAndVoteId(Long userId,Long voteId);

    int countAllByVoteId(Long voteId);

    int countAllByAnswerId(Long AnswerId);


    int countAllByUserGroupNameAndAnswerId(String groupName,Long answerId);
}
