package me.maxgrzy.economybotdiscord.helperclasses;

import java.util.Objects;

public class Entry {
    private String guildID;
    private String userID;
    public Entry(String guildID, String userID) {
        this.guildID = guildID;
        this.userID = userID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry entry = (Entry) o;
        return Objects.equals(guildID, entry.guildID) &&
                Objects.equals(userID, entry.userID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guildID, userID);
    }

    public String getGuildID() {
        return guildID;
    }

    public void setGuildID(String guildID) {
        this.guildID = guildID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
