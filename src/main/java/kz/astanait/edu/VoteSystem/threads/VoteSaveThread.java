package kz.astanait.edu.VoteSystem.threads;

import kz.astanait.edu.VoteSystem.models.Vote;
import kz.astanait.edu.VoteSystem.repo.VoteRepository;

public class VoteSaveThread implements Runnable{
    private VoteRepository voteRepository;
    private Vote vote;

    public VoteSaveThread(VoteRepository voteRepository,Vote vote){
        this.voteRepository = voteRepository;
        this.vote = vote;
    }

    @Override
    public void run() {
        voteRepository.save(vote);
    }
}
