package kz.astanait.edu.VoteSystem.controllers;

import kz.astanait.edu.VoteSystem.models.*;
import kz.astanait.edu.VoteSystem.repo.AnswerRepo;
import kz.astanait.edu.VoteSystem.repo.UserRepo;
import kz.astanait.edu.VoteSystem.repo.VoteDBRepo;
import kz.astanait.edu.VoteSystem.repo.VoteRepo;
import kz.astanait.edu.VoteSystem.services.VotingService;
import kz.astanait.edu.VoteSystem.threads.VoteSaveThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    @Autowired
    private UserRepo userRepository;

    @Autowired
    private VoteRepo voteRepository;

    @Autowired
    private AnswerRepo answerRepository;

    @Autowired
    private VoteDBRepo voteDBRepository;

    @Autowired
    private VotingService votingService;

    @GetMapping("/users")
    public String getAllUsers(Model model){
        model.addAttribute("users",userRepository.findAll());
        return "users";
    }
    @GetMapping("/users/{id}/update")
    public String getUpdateUserPage(@PathVariable(value = "id") long id, Model model){
        model.addAttribute("newUser",new User());
        model.addAttribute("user",userRepository.getById(id));
        model.addAttribute("roles", Role.values());
        return "user-update";
    }

    @PostMapping("/users/{id}/update")
    public String updateUser(@PathVariable(value = "id") long id,User newUser,Model model){
        User user = userRepository.getById(id);
        user.setRoles(newUser.getRoles());
        userRepository.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/votes")
    public String getAllVotes(Model model){
        model.addAttribute("votes",voteRepository.findAll());
        model.addAttribute("vote",new Vote());
        model.addAttribute("votingService",votingService);
        return "votes";
    }

    @PostMapping("/votes/add")
    public String addVote(Vote vote, Model model){
        System.out.println(vote);
        VoteSaveThread voteSaveThread = new VoteSaveThread(voteRepository,vote);
        Thread thread = new Thread(voteSaveThread);
        thread.start();
        return "redirect:/admin/votes";
    }

    /*@GetMapping("/votes/{id}/update")
    public String getUpdateVotePage(@PathVariable(value = "id")long id, Model model){
        Vote vote = voteRepository.getById(id);
        model.addAttribute("voteOld",vote);
        model.addAttribute("voteNew",new Vote());
        model.addAttribute("counter",new Counter());
        return "update-vote";
    }

    @PostMapping("/votes/{id}/update")
    public String updateVote(@PathVariable(value = "id")long id,Vote voteOld,Model model){
        Vote vote = voteRepository.getById(id);
        for (int i=0;i<vote.getAnswers().size();i++){
            vote.getAnswers().get(i).setAnswer(voteOld.getAnswers().get(i).getAnswer());
        }
        vote.setQuestions(voteOld.getQuestions());
        System.out.println(vote);
        *//*voteRepository.save(vote);*//*
        return "redirect:/admin/votes";
    }*/

    @GetMapping("/votes/{id}")
    public String updateVote(@PathVariable(value = "id") long id,Model model){
        model.addAttribute("vote",voteRepository.getById(id));
        model.addAttribute("votingService",votingService);
        model.addAttribute("groups",userRepository.findDistinctGroupName());
        return "vote-info";
    }

    @GetMapping("/votes/{voteId}/update/{answerId}")
    public String getUpdateAnswerPage(Model model,@PathVariable long voteId,@PathVariable long answerId){
        model.addAttribute("answer",answerRepository.getById(answerId));
        model.addAttribute("vote",voteRepository.getById(voteId));
        return "update-answer";
    }

    @PostMapping("/votes/{voteId}/update/{answerId}")
    public String updateAnswer(@RequestParam String answerText, Model model, @PathVariable long voteId, @PathVariable long answerId){
        Answer answer = answerRepository.getById(answerId);
        answer.setAnswer(answerText);
        answerRepository.save(answer);
        return "redirect:/admin/votes/"+voteId;
    }

    @GetMapping("/votes/{voteId}/delete/{answerId}")
    public String deleteAnswer(Model model,@PathVariable long voteId,@PathVariable long answerId){
        Vote vote = voteRepository.getById(voteId);
        vote.remove(answerRepository.getById(answerId));
        VoteSaveThread voteSaveThread = new VoteSaveThread(voteRepository,vote);
        Thread thread = new Thread(voteSaveThread);
        thread.start();
        voteDBRepository.deleteAllByAnswerId(answerId);
        /*answerRepository.deleteById(answerId);*/
        return "redirect:/admin/votes/"+voteId;
    }

    @GetMapping("/votes/{voteId}/add")
    public String getAddAnswerToVotePage(Model model,@PathVariable long voteId){
        model.addAttribute("voteId",voteId);
        return "vote-add-answer";
    }

    @PostMapping("/votes/{voteId}/add")
    public String addAnswerToVote(Model model,@PathVariable long voteId,@RequestParam String answerText){
        Vote vote = voteRepository.getById(voteId);
        Answer answer = new Answer(answerText);
        vote.addAnswer(answer);
        voteRepository.save(vote);
        return "redirect:/admin/votes/"+voteId;
    }

    @GetMapping("/votes/{voteId}/update")
    public String getUpdateVotePage(Model model,@PathVariable long voteId){
        model.addAttribute("vote",voteRepository.getById(voteId));
        return "vote-update-question";
    }

    @PostMapping("/votes/{voteId}/update")
    public String updateVote(@RequestParam String question, Model model,@PathVariable long voteId){
        Vote vote = voteRepository.getById(voteId);
        vote.setQuestions(question);
        voteRepository.save(vote);
        return "redirect:/admin/votes";
    }

    @GetMapping("/votes/{voteId}/delete")
    public String getDeleteVotePage(Model model,@PathVariable long voteId){
        model.addAttribute("vote",voteRepository.getById(voteId));
        return "vote-delete-question";
    }

    @DeleteMapping("/votes/{voteId}/delete")
    public String deleteVote(@RequestParam String question, Model model,@PathVariable long voteId){
        Vote vote = voteRepository.getById(voteId);
        vote.setQuestions(question);
        voteRepository.deleteById(voteId);
        return "redirect:/admin/votes";
    }


}
