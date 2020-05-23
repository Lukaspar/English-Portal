package com.lukaspar.ep.authentication.model;

import com.lukaspar.ep.authentication.exception.FriendRequestAlreadyExistsException;
import com.lukaspar.ep.authentication.exception.FriendRequestNotFoundException;
import com.lukaspar.ep.authentication.exception.FriendRequestRejectedExceptionException;
import com.lukaspar.ep.authentication.exception.FriendRequestWrongStatusException;
import com.lukaspar.ep.authentication.util.FriendshipStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.lukaspar.ep.authentication.util.FriendshipStatus.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private boolean active;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy="owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Friendship> friends = new HashSet<>();

    @OneToMany(mappedBy="friend", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Friendship> friendsOf = new HashSet<>();

    public void addFriendRequest(User friend){
        Optional<Friendship> existingFriendship = friends.stream()
                .filter(friendship -> friendship.getOwner().equals(this) && friendship.getFriend().equals(friend))
                .findAny();

        if(existingFriendship.isEmpty()){
            friends.add(new Friendship(this, friend, REQUESTED));
        } else if (existingFriendship.get().getStatus() == REQUESTED){
            throw new FriendRequestAlreadyExistsException(id, friend.getId());
        } else if(existingFriendship.get().getStatus() == REJECTED){
            throw new FriendRequestRejectedExceptionException(id, friend.getId());
        }
    }

    public Set<String> getFriendsRequests() {
        return friendsOf.stream()
                .filter(friendship -> friendship.getStatus() == REQUESTED)
                .map(friendship -> friendship.getOwner().getUsername())
                .collect(Collectors.toSet());
    }

    public void acceptOrRejectFriendRequest(User friend, FriendshipStatus status){
        Friendship friendship = friendsOf.stream()
                .filter(friendRequests -> friendRequests.getOwner().equals(friend))
                .findAny()
                .orElseThrow(() -> new FriendRequestNotFoundException(id, friend.getId()));

        if(friendship.getStatus() != REQUESTED){
            throw new FriendRequestWrongStatusException(friendship.getStatus());
        }

        friendship.setStatus(status);
    }

    public Set<String> getAllFriends(){
        Set<String> requestedFriends = friends.stream()
                .filter(friendship -> friendship.getStatus() == ACCEPTED)
                .map(friendship -> friendship.getFriend().getUsername()).collect(Collectors.toSet());

        Set<String> acceptedFriends = friendsOf.stream()
                .filter(friendship -> friendship.getStatus() == ACCEPTED)
                .map(friendship -> friendship.getOwner().getUsername()).collect(Collectors.toSet());

        Set<String> result = new HashSet<>(requestedFriends);
        result.addAll(acceptedFriends);
        return result;
    }
}