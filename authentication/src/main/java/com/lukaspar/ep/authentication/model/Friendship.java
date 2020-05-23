package com.lukaspar.ep.authentication.model;

import com.lukaspar.ep.authentication.util.FriendshipStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

import static com.lukaspar.ep.authentication.util.FriendshipStatus.REQUESTED;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "friendship")
public class Friendship implements Serializable {

    @EmbeddedId
    private FriendshipPrimaryKeys key = new FriendshipPrimaryKeys();

    @Enumerated(EnumType.STRING)
    private FriendshipStatus status = REQUESTED;

    @ManyToOne
    @MapsId("ownerId")
    private User owner;

    @ManyToOne
    @MapsId("friendId")
    private User friend;

    public Friendship(User owner, User friend, FriendshipStatus status){
        this.owner = owner;
        this.friend = friend;
        this.status = status;
    }

    @Embeddable
    public static class FriendshipPrimaryKeys implements Serializable {
        private Long ownerId;
        private Long friendId;
    }
}
