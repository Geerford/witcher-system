package com.nevdev.witcher.core;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.nevdev.witcher.enums.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity(name = "Users")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(unique = true, length = 32)
    private String username;

    @JsonProperty(access = Access.WRITE_ONLY)
    @Size(min = 6, max = 50)
    private String password;

    private String resetToken;

    @Enumerated(EnumType.ORDINAL)
    private Role role;

    private String firstName;

    private String lastName;

    private String email;

    @NotNull
    private Boolean enabled;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date lastPasswordResetDate;

    @ManyToMany(
            fetch = FetchType.EAGER//,
            //cascade = CascadeType.MERGE
    )
    @JsonIgnoreProperties("users")
    @JoinTable(
            name = "USER_AUTHORITY",
            joinColumns = {@JoinColumn(name = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_ID")})
    private List<Authority> authorities;

    @ManyToMany(
            mappedBy = "witchers",
            fetch = FetchType.LAZY
    )
    @JsonIgnoreProperties({"witchers", "executors", "executorsBookmarked"})
    private List<Task> tasks;

    @ManyToMany(
            mappedBy = "witchersCompleted",
            fetch = FetchType.LAZY
    )
    @JsonIgnoreProperties({"witchersCompleted", "executors", "executorsBookmarked"})
    private List<Task> tasksCompleted;

    @ManyToMany(
            mappedBy = "executors",
            fetch = FetchType.LAZY
    )
    @JsonIgnoreProperties({"executors", "witchersCompleted", "witchers"})
    private List<Deal> deals;

    @ManyToMany(
            mappedBy = "executorsBookmarked",
            fetch = FetchType.LAZY
    )
    @JsonIgnoreProperties({"executorsBookmarked", "witchersCompleted", "witchers"})
    private List<Deal> dealsBookmarked;

    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @MapsId
    @NotNull
    private Bank bank;

    public User(){}

    public User(String username, String password, Role role, String firstName, String lastName, String email,
                @NotNull Boolean enabled, @NotNull Date lastPasswordResetDate, List<Authority> authorities,
                @NotNull Boolean kingRepository){
        this.username = username;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.enabled = enabled;
        this.lastPasswordResetDate = lastPasswordResetDate;
        this.authorities = authorities;
        this.bank = new Bank(kingRepository);
    }

    public User(String username, Role role, String firstName, String lastName, String email, Bank bank){
        this.username = username;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.bank = bank;
    }

    public User(Long id, String username, Role role, String firstName, String lastName, String email, Bank bank){
        this.id = id;
        this.username = username;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.bank = bank;
    }
}
