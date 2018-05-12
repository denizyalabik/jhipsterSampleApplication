package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.github.jhipster.application.domain.enumeration.Role;

/**
 * A AcademiaUsers.
 */
@Entity
@Table(name = "academia_users")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "academiausers")
public class AcademiaUsers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "jhi_password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "u_role")
    private Role uRole;

    @OneToMany(mappedBy = "acauser")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<QuestionAnswer> creates = new HashSet<>();

    @OneToMany(mappedBy = "acauser")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TestAc> creates = new HashSet<>();

    @OneToMany(mappedBy = "acauser")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TestAc> solves = new HashSet<>();

    @OneToMany(mappedBy = "academiaUsers")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<History> have = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public AcademiaUsers name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public AcademiaUsers surname(String surname) {
        this.surname = surname;
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUserName() {
        return userName;
    }

    public AcademiaUsers userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public AcademiaUsers email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public AcademiaUsers password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getuRole() {
        return uRole;
    }

    public AcademiaUsers uRole(Role uRole) {
        this.uRole = uRole;
        return this;
    }

    public void setuRole(Role uRole) {
        this.uRole = uRole;
    }

    public Set<QuestionAnswer> getCreates() {
        return creates;
    }

    public AcademiaUsers creates(Set<QuestionAnswer> questionAnswers) {
        this.creates = questionAnswers;
        return this;
    }

    public AcademiaUsers addCreates(QuestionAnswer questionAnswer) {
        this.creates.add(questionAnswer);
        questionAnswer.setAcauser(this);
        return this;
    }

    public AcademiaUsers removeCreates(QuestionAnswer questionAnswer) {
        this.creates.remove(questionAnswer);
        questionAnswer.setAcauser(null);
        return this;
    }

    public void setCreates(Set<QuestionAnswer> questionAnswers) {
        this.creates = questionAnswers;
    }

    public Set<TestAc> getCreates() {
        return creates;
    }

    public AcademiaUsers creates(Set<TestAc> testAcs) {
        this.creates = testAcs;
        return this;
    }

    public AcademiaUsers addCreates(TestAc testAc) {
        this.creates.add(testAc);
        testAc.setAcauser(this);
        return this;
    }

    public AcademiaUsers removeCreates(TestAc testAc) {
        this.creates.remove(testAc);
        testAc.setAcauser(null);
        return this;
    }

    public void setCreates(Set<TestAc> testAcs) {
        this.creates = testAcs;
    }

    public Set<TestAc> getSolves() {
        return solves;
    }

    public AcademiaUsers solves(Set<TestAc> testAcs) {
        this.solves = testAcs;
        return this;
    }

    public AcademiaUsers addSolve(TestAc testAc) {
        this.solves.add(testAc);
        testAc.setAcauser(this);
        return this;
    }

    public AcademiaUsers removeSolve(TestAc testAc) {
        this.solves.remove(testAc);
        testAc.setAcauser(null);
        return this;
    }

    public void setSolves(Set<TestAc> testAcs) {
        this.solves = testAcs;
    }

    public Set<History> getHave() {
        return have;
    }

    public AcademiaUsers have(Set<History> histories) {
        this.have = histories;
        return this;
    }

    public AcademiaUsers addHas(History history) {
        this.have.add(history);
        history.setAcademiaUsers(this);
        return this;
    }

    public AcademiaUsers removeHas(History history) {
        this.have.remove(history);
        history.setAcademiaUsers(null);
        return this;
    }

    public void setHave(Set<History> histories) {
        this.have = histories;
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
        AcademiaUsers academiaUsers = (AcademiaUsers) o;
        if (academiaUsers.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), academiaUsers.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AcademiaUsers{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            ", userName='" + getUserName() + "'" +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            ", uRole='" + getuRole() + "'" +
            "}";
    }
}
