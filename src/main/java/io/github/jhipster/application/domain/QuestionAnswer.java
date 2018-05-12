package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.github.jhipster.application.domain.enumeration.Category;

import io.github.jhipster.application.domain.enumeration.Subject;

import io.github.jhipster.application.domain.enumeration.Level;

/**
 * A QuestionAnswer.
 */
@Entity
@Table(name = "question_answer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "questionanswer")
public class QuestionAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "question_title", nullable = false)
    private String questionTitle;

    @NotNull
    @Column(name = "question", nullable = false)
    private String question;

    @NotNull
    @Column(name = "correct_answer", nullable = false)
    private String correctAnswer;

    @NotNull
    @Column(name = "wrong_answers", nullable = false)
    private String wrongAnswers;

    @Enumerated(EnumType.STRING)
    @Column(name = "catagory")
    private Category catagory;

    @Enumerated(EnumType.STRING)
    @Column(name = "subject")
    private Subject subject;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_level")
    private Level level;

    @OneToMany(mappedBy = "questionAnswer")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<History> solveds = new HashSet<>();

    @ManyToOne
    private AcademiaUsers acauser;

    @ManyToMany(mappedBy = "have")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TestAc> ques = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public QuestionAnswer questionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
        return this;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestion() {
        return question;
    }

    public QuestionAnswer question(String question) {
        this.question = question;
        return this;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public QuestionAnswer correctAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
        return this;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getWrongAnswers() {
        return wrongAnswers;
    }

    public QuestionAnswer wrongAnswers(String wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
        return this;
    }

    public void setWrongAnswers(String wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public Category getCatagory() {
        return catagory;
    }

    public QuestionAnswer catagory(Category catagory) {
        this.catagory = catagory;
        return this;
    }

    public void setCatagory(Category catagory) {
        this.catagory = catagory;
    }

    public Subject getSubject() {
        return subject;
    }

    public QuestionAnswer subject(Subject subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Level getLevel() {
        return level;
    }

    public QuestionAnswer level(Level level) {
        this.level = level;
        return this;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Set<History> getSolveds() {
        return solveds;
    }

    public QuestionAnswer solveds(Set<History> histories) {
        this.solveds = histories;
        return this;
    }

    public QuestionAnswer addSolved(History history) {
        this.solveds.add(history);
        history.setQuestionAnswer(this);
        return this;
    }

    public QuestionAnswer removeSolved(History history) {
        this.solveds.remove(history);
        history.setQuestionAnswer(null);
        return this;
    }

    public void setSolveds(Set<History> histories) {
        this.solveds = histories;
    }

    public AcademiaUsers getAcauser() {
        return acauser;
    }

    public QuestionAnswer acauser(AcademiaUsers academiaUsers) {
        this.acauser = academiaUsers;
        return this;
    }

    public void setAcauser(AcademiaUsers academiaUsers) {
        this.acauser = academiaUsers;
    }

    public Set<TestAc> getQues() {
        return ques;
    }

    public QuestionAnswer ques(Set<TestAc> testAcs) {
        this.ques = testAcs;
        return this;
    }

    public QuestionAnswer addQues(TestAc testAc) {
        this.ques.add(testAc);
        testAc.getHave().add(this);
        return this;
    }

    public QuestionAnswer removeQues(TestAc testAc) {
        this.ques.remove(testAc);
        testAc.getHave().remove(this);
        return this;
    }

    public void setQues(Set<TestAc> testAcs) {
        this.ques = testAcs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QuestionAnswer questionAnswer = (QuestionAnswer) o;
        if (questionAnswer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), questionAnswer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuestionAnswer{" +
            "id=" + getId() +
            ", questionTitle='" + getQuestionTitle() + "'" +
            ", question='" + getQuestion() + "'" +
            ", correctAnswer='" + getCorrectAnswer() + "'" +
            ", wrongAnswers='" + getWrongAnswers() + "'" +
            ", catagory='" + getCatagory() + "'" +
            ", subject='" + getSubject() + "'" +
            ", level='" + getLevel() + "'" +
            "}";
    }
}
