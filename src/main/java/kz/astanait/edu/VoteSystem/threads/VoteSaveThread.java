package kz.astanait.edu.VoteSystem.threads;

import kz.astanait.edu.VoteSystem.models.Vote;
import kz.astanait.edu.VoteSystem.repo.VoteRepo;

public class VoteSaveThread implements Runnable{
    private VoteRepo voteRepository;
    private Vote vote;

    public VoteSaveThread(VoteRepo voteRepository, Vote vote){
        this.voteRepository = voteRepository;
        this.vote = vote;
    }

    @Override
    public void run() {
        voteRepository.save(vote);
    }
}
