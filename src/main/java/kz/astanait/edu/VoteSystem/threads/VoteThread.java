package kz.astanait.edu.VoteSystem.threads;

import kz.astanait.edu.VoteSystem.models.VoteDB;
import kz.astanait.edu.VoteSystem.repo.VoteDBRepo;

public class VoteThread extends Thread{
    private VoteDBRepo voteDBRepository;
    private VoteDB voteDB;

    public VoteThread(VoteDBRepo voteDBRepository, VoteDB voteDB){
        this.voteDBRepository = voteDBRepository;
        this.voteDB = voteDB;
    }

    @Override
    public void run() {
        voteDBRepository.save(voteDB);
    }
}
