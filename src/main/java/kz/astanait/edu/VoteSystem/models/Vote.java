package kz.astanait.edu.VoteSystem.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
public class Vote {
    @Id
    @GeneratedValue(generator = "sequence-vote-generator")
    @GenericGenerator(
            name = "sequence-vote-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "vote_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "100"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    private Long id;
    private String questions;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public void addAnswer(Answer answer){
        answers.add(answer);
    }

    public void updateAnswer(Answer updatedAnswer){
        for(Answer oldAnswer:answers){
            if(oldAnswer.getId()==updatedAnswer.getId()){
                oldAnswer.setAnswer(updatedAnswer.getAnswer());
            }
        }
    }

    public void remove(Answer answerDelete){
        for(Answer answer : answers){
            if(answer.getId()==answerDelete.getId()){
                answers.remove(answer);
                break;
            }
        }
    }


}
