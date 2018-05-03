package io.github.jhipster.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

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
    @Column(name = "question", nullable = false)
    private String question;

    @NotNull
    @Column(name = "correct_answer", nullable = false)
    private String correctAnswer;

    @NotNull
    @Column(name = "wrong_answers", nullable = false)
    private String wrongAnswers;

    @ManyToOne
    private Usersdemo user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Usersdemo getUser() {
        return user;
    }

    public QuestionAnswer user(Usersdemo usersdemo) {
        this.user = usersdemo;
        return this;
    }

    public void setUser(Usersdemo usersdemo) {
        this.user = usersdemo;
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
            ", question='" + getQuestion() + "'" +
            ", correctAnswer='" + getCorrectAnswer() + "'" +
            ", wrongAnswers='" + getWrongAnswers() + "'" +
            "}";
    }
}
