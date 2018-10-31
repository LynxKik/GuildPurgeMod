package net.lynx.hypixelguildmod.api;

/**
 * This is the object class
 * to hold guild member info
 */
public class GuildMember {
    private String uuid;
    private String rank;
    private long joinDate;
    private int questParticipation;

    GuildMember(String uuid, String rank, long joinDate, int questParticipation) {
        this.uuid = uuid;
        this.rank = rank;
        this.joinDate = joinDate;
        this.questParticipation = questParticipation;
    }

    public int getQuestParticipation() {
        return questParticipation;
    }

    public void setQuestParticipation(int questParticipation) {
        this.questParticipation = questParticipation;
    }

    public long getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(long joinDate) {
        this.joinDate = joinDate;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
