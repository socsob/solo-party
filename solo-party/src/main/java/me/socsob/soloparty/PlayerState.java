package me.socsob.soloparty;

public class PlayerState {
    private boolean isPaused;
    private boolean isEditing;

    private String recordingName = null;

    public String getRecordingName() {
        return recordingName;
    }

    public void setRecordingName(String recordingName) {
        this.recordingName = recordingName;
    }

    public boolean isEditing() {
        return isEditing;
    }
    public void setEditing(boolean editing) {
        isEditing = editing;
    }
    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }
}