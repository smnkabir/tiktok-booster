package com.vectorit.tikboost.Model;

import com.google.gson.annotations.SerializedName;

public class Profile {
    @SerializedName("name")
    private String name;
    @SerializedName("bio")
    private String bio;
    @SerializedName("following")
    private String following;
    @SerializedName("follower")
    private String follower;
    @SerializedName("Likes")
    private String like;

    public Profile(String name, String bio, String following, String follower, String like) {
        this.name = name;
        this.bio = bio;
        this.following = following;
        this.follower = follower;
        this.like = like;
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    public String getFollowing() {
        return following;
    }

    public String getFollower() {
        return follower;
    }

    public String getLike() {
        return like;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "name='" + name + '\'' +
                ", bio='" + bio + '\'' +
                ", following='" + following + '\'' +
                ", follower='" + follower + '\'' +
                ", like='" + like + '\'' +
                '}';
    }
}
