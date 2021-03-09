package kz.astanait.edu.VoteSystem.services;

import kz.astanait.edu.VoteSystem.models.VoteDB;
import kz.astanait.edu.VoteSystem.repo.AnswerRepository;
import kz.astanait.edu.VoteSystem.repo.UserRepository;
import kz.astanait.edu.VoteSystem.repo.VoteDBRepository;
import kz.astanait.edu.VoteSystem.repo.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VotingService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private VoteDBRepository voteDBRepository;
    @Autowired
    private AnswerRepository answerRepository;

    public boolean isVoted(Long userId,Long voteId){
        if(voteDBRepository.existsByVoteIdAndUserId(voteId,userId)){
            return true;
        }
        return false;
    }

    public String getAnswer(Long userId,Long voteId){
        return voteDBRepository.getByUserIdAndVoteId(userId,voteId).getAnswer().getAnswer();
    }

    public int getVotedCount(Long voteId){
        return voteDBRepository.countAllByVoteId(voteId);
    }

    public int getVotedAnswerCount(Long answerId){
        return voteDBRepository.countAllByAnswerId(answerId);
    }

    public int getCountByGroupNameAndVote(String groupName,Long answerId){
        return voteDBRepository.countAllByUserGroupNameAndAnswerId(groupName,answerId);
    }

}
