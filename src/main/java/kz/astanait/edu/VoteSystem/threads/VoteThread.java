package kz.astanait.edu.VoteSystem.threads;

import kz.astanait.edu.VoteSystem.models.VoteDB;
import kz.astanait.edu.VoteSystem.repo.VoteDBRepository;

public class VoteThread extends Thread{
    private VoteDBRepository voteDBRepository;
    private VoteDB voteDB;

    public VoteThread(VoteDBRepository voteDBRepository,VoteDB voteDB){
        this.voteDBRepository = voteDBRepository;
        this.voteDB = voteDB;
    }

    @Override
    public void run() {
        voteDBRepository.save(voteDB);
    }
}
