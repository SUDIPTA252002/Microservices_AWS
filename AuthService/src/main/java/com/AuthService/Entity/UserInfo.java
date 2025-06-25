package com.AuthService.Entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfo
{
    @Id
    @Column(name="user_id")
    private String userId;
    private String username;
    private String firstName;
    private String lastName;
    private String password;

    private Long phoneNumber;
    private String email;



    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
        name="user_roles",
        joinColumns = @JoinColumn(name="user_id"),
        inverseJoinColumns = @JoinColumn(name="role_id")
    )
    private Set<UserRole> roles=new HashSet<>();
}