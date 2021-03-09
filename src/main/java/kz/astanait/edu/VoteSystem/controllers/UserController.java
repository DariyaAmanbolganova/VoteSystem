package kz.astanait.edu.VoteSystem.controllers;

import kz.astanait.edu.VoteSystem.models.User;
import kz.astanait.edu.VoteSystem.models.VoteDB;
import kz.astanait.edu.VoteSystem.repo.AnswerRepository;
import kz.astanait.edu.VoteSystem.repo.UserRepository;
import kz.astanait.edu.VoteSystem.repo.VoteDBRepository;
import kz.astanait.edu.VoteSystem.repo.VoteRepository;
import kz.astanait.edu.VoteSystem.services.VotingService;
import kz.astanait.edu.VoteSystem.threads.VoteThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('USER')")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private VotingService votingService;

    @Autowired
    private VoteDBRepository voteDBRepository;

    @GetMapping("/profile")
    public String getUserProfile(Model model){
        return "profile";
    }
    @GetMapping("/update/profile")
    public String getUpdateProfilePage(@AuthenticationPrincipal User user,Model model){
        model.addAttribute("oldUser",user);
        return "update-profile";
    }
    @PostMapping("/update/profile")
    public String updateProfile(Model model,User userNew,@AuthenticationPrincipal User userOld){
        userOld.setFname(userNew.getFname());
        userOld.setLname(userNew.getLname());
        userOld.setBorn(userNew.getBorn());
        userOld.setGroupName(userNew.getGroupName());
        userOld.setInterests(userNew.getInterests());
        userRepository.save(userOld);
        return "redirect:/";
    }

    @GetMapping("/update/password")
    public String getUpdatePasswordPage(Model model){
        model.addAttribute("user",new User());
        return "update-password";
    }

    @PostMapping("/update/password")
    public String updatePassword(@AuthenticationPrincipal User oldUser,User user){
        oldUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(oldUser);
        return "redirect:/";
    }

    @GetMapping("/votes")
    public String getVotingPage(Model model){
        model.addAttribute("votes",voteRepository.findAll());
        model.addAttribute("votingService",votingService);
        return "voting-page";
    }
    @PostMapping("/votes")
    public String Voting(Model model,Long voteId,Long answerId,@AuthenticationPrincipal User user){
        if(!votingService.isVoted(user.getId(),voteId)){
            VoteDB voteDB = new VoteDB(user,voteRepository.getById(voteId),answerRepository.getById(answerId));
            VoteThread voteThread = new VoteThread(voteDBRepository,voteDB);
            voteThread.start();
        }
        return "redirect:/user/votes";

    }

}