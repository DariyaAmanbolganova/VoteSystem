package kz.astanait.edu.VoteSystem.repo;

import kz.astanait.edu.VoteSystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepo extends JpaRepository<User,Long> {
    User findByUsername(String username);
    User getById(Long id);
    @Query("select DISTINCT groupName from User")
    List<String>   findDistinctGroupName();
}
