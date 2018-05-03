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
 * A Usersdemo.
 */
@Entity
@Table(name = "usersdemo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "usersdemo")
public class Usersdemo implements Serializable {

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

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<QuestionAnswer> qanswers = new HashSet<>();

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

    public Usersdemo name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public Usersdemo surname(String surname) {
        this.surname = surname;
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUserName() {
        return userName;
    }

    public Usersdemo userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public Usersdemo email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public Usersdemo password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getuRole() {
        return uRole;
    }

    public Usersdemo uRole(Role uRole) {
        this.uRole = uRole;
        return this;
    }

    public void setuRole(Role uRole) {
        this.uRole = uRole;
    }

    public Set<QuestionAnswer> getQanswers() {
        return qanswers;
    }

    public Usersdemo qanswers(Set<QuestionAnswer> questionAnswers) {
        this.qanswers = questionAnswers;
        return this;
    }

    public Usersdemo addQanswer(QuestionAnswer questionAnswer) {
        this.qanswers.add(questionAnswer);
        questionAnswer.setUser(this);
        return this;
    }

    public Usersdemo removeQanswer(QuestionAnswer questionAnswer) {
        this.qanswers.remove(questionAnswer);
        questionAnswer.setUser(null);
        return this;
    }

    public void setQanswers(Set<QuestionAnswer> questionAnswers) {
        this.qanswers = questionAnswers;
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
        Usersdemo usersdemo = (Usersdemo) o;
        if (usersdemo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), usersdemo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Usersdemo{" +
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
