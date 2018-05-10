package io.github.jhipster.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TestAc.
 */
@Entity
@Table(name = "test_ac")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "testac")
public class TestAc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_time")
    private LocalDate createdTime;

    @Column(name = "test_name")
    private String testName;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "test_ac_has",
               joinColumns = @JoinColumn(name="test_acs_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="have_id", referencedColumnName="id"))
    private Set<QuestionAnswer> have = new HashSet<>();

    @ManyToOne
    private AcademiaUsers acauser;

    @ManyToOne
    private AcademiaUsers acauser;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreatedTime() {
        return createdTime;
    }

    public TestAc createdTime(LocalDate createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedTime(LocalDate createdTime) {
        this.createdTime = createdTime;
    }

    public String getTestName() {
        return testName;
    }

    public TestAc testName(String testName) {
        this.testName = testName;
        return this;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Set<QuestionAnswer> getHave() {
        return have;
    }

    public TestAc have(Set<QuestionAnswer> questionAnswers) {
        this.have = questionAnswers;
        return this;
    }

    public TestAc addHas(QuestionAnswer questionAnswer) {
        this.have.add(questionAnswer);
        questionAnswer.getQues().add(this);
        return this;
    }

    public TestAc removeHas(QuestionAnswer questionAnswer) {
        this.have.remove(questionAnswer);
        questionAnswer.getQues().remove(this);
        return this;
    }

    public void setHave(Set<QuestionAnswer> questionAnswers) {
        this.have = questionAnswers;
    }

    public AcademiaUsers getAcauser() {
        return acauser;
    }

    public TestAc acauser(AcademiaUsers academiaUsers) {
        this.acauser = academiaUsers;
        return this;
    }

    public void setAcauser(AcademiaUsers academiaUsers) {
        this.acauser = academiaUsers;
    }

    public AcademiaUsers getAcauser() {
        return acauser;
    }

    public TestAc acauser(AcademiaUsers academiaUsers) {
        this.acauser = academiaUsers;
        return this;
    }

    public void setAcauser(AcademiaUsers academiaUsers) {
        this.acauser = academiaUsers;
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
        TestAc testAc = (TestAc) o;
        if (testAc.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), testAc.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TestAc{" +
            "id=" + getId() +
            ", createdTime='" + getCreatedTime() + "'" +
            ", testName='" + getTestName() + "'" +
            "}";
    }
}
