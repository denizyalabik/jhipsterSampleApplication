package io.github.jhipster.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A History.
 */
@Entity
@Table(name = "history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "history")
public class History implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "solved_date")
    private LocalDate solvedDate;

    @Column(name = "given_answer")
    private String givenAnswer;

    @ManyToOne
    private QuestionAnswer questionAnswer;

    @ManyToOne
    private AcademiaUsers academiaUsers;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getSolvedDate() {
        return solvedDate;
    }

    public History solvedDate(LocalDate solvedDate) {
        this.solvedDate = solvedDate;
        return this;
    }

    public void setSolvedDate(LocalDate solvedDate) {
        this.solvedDate = solvedDate;
    }

    public String getGivenAnswer() {
        return givenAnswer;
    }

    public History givenAnswer(String givenAnswer) {
        this.givenAnswer = givenAnswer;
        return this;
    }

    public void setGivenAnswer(String givenAnswer) {
        this.givenAnswer = givenAnswer;
    }

    public QuestionAnswer getQuestionAnswer() {
        return questionAnswer;
    }

    public History questionAnswer(QuestionAnswer questionAnswer) {
        this.questionAnswer = questionAnswer;
        return this;
    }

    public void setQuestionAnswer(QuestionAnswer questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    public AcademiaUsers getAcademiaUsers() {
        return academiaUsers;
    }

    public History academiaUsers(AcademiaUsers academiaUsers) {
        this.academiaUsers = academiaUsers;
        return this;
    }

    public void setAcademiaUsers(AcademiaUsers academiaUsers) {
        this.academiaUsers = academiaUsers;
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
        History history = (History) o;
        if (history.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), history.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "History{" +
            "id=" + getId() +
            ", solvedDate='" + getSolvedDate() + "'" +
            ", givenAnswer='" + getGivenAnswer() + "'" +
            "}";
    }
}
