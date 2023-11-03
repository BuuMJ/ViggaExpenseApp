package com.example.viggaexpense;

import java.io.Serializable;

public class Observation implements Serializable {
    private String observation;
    private String timeOfObservation;
    private String notes;

    public Observation(String observation, String timeOfObservation, String notes) {
        this.observation = observation;
        this.timeOfObservation = timeOfObservation;
        this.notes = notes;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getTimeOfObservation() {
        return timeOfObservation;
    }

    public void setTimeOfObservation(String timeOfObservation) {
        this.timeOfObservation = timeOfObservation;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
