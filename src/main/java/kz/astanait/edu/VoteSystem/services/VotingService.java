package kz.astanait.edu.VoteSystem.services;

import kz.astanait.edu.VoteSystem.repo.AnswerRepo;
import kz.astanait.edu.VoteSystem.repo.UserRepo;
import kz.astanait.edu.VoteSystem.repo.VoteDBRepo;
import kz.astanait.edu.VoteSystem.repo.VoteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VotingService {
    @Autowired
    private UserRepo userRepository;
    @Autowired
    private VoteRepo voteRepository;
    @Autowired
    private VoteDBRepo voteDBRepository;
    @Autowired
    private AnswerRepo answerRepository;

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
