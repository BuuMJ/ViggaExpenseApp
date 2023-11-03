package com.example.viggaexpense;

import java.io.Serializable;

public class Observation implements Serializable {
    private int observationId;
    private int observationTripId;
    private String observationTitle;
    private String observationTime;
    private String observationNotes;

    public Observation(int observationId, int observationTripId, String observationTitle, String observationTime, String observationNotes) {
        this.observationId = observationId;
        this.observationTripId = observationTripId;
        this.observationTitle = observationTitle;
        this.observationTime = observationTime;
        this.observationNotes = observationNotes;
    }

    public String getObservationTitle() {
        return observationTitle;
    }

    public void setObservationTitle(String observationTitle) {
        this.observationTitle = observationTitle;
    }

    public String getObservationTime() {
        return observationTime;
    }

    public void setObservationTime(String observationTime) {
        this.observationTime = observationTime;
    }

    public String getObservationNotes() {
        return observationNotes;
    }

    public void setObservationNotes(String observationNotes) {
        this.observationNotes = observationNotes;
    }

    public int getObservationTripId() {
        return observationTripId;
    }

    public void setObservationTripId(int observationTripId) {
        this.observationTripId = observationTripId;
    }

    public int getObservationId() {
        return observationId;
    }

    public void setObservationId(int observationId) {
        this.observationId = observationId;
    }
}
