package com.alternance.etna.agenda.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "RelationshipId",
        "UserId",
        "RelationUserId",
        "Status"
})
public class ContactRelationEntity implements Serializable
{
    @JsonProperty("RelationshipId")
    private int RelationshipId;
    @JsonProperty("UserId")
    private int UserId;
    @JsonProperty("RelationUserId")
    private int RelationshipUserId;
    @JsonProperty("Status")
    private int Status;

    public int getRelationshipId() {
        return RelationshipId;
    }

    public void setRelationshipId(int RelationshipId) {
        this.RelationshipId = RelationshipId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int mUserId) {
        this.UserId = mUserId;
    }

    public int getRelationshipUserId() {
        return RelationshipUserId;
    }

    public void setRelationshipUserId(int mRelationshipUserId) {
        this.RelationshipUserId = mRelationshipUserId;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int mStatus) {
        this.Status = mStatus;
    }
}