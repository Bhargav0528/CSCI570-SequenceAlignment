package model;

public class SequenceAlignmentOutput {
    Integer costOfAlignment;
    String firstAlignmentString;
    String secondAlignmentString;

    Float timeInMilliSeconds;
    Float memoryInKB;
    public Integer getCostOfAlignment() {
        return costOfAlignment;
    }

    public void setCostOfAlignment(Integer costOfAlignment) {
        this.costOfAlignment = costOfAlignment;
    }

    public String getFirstAlignmentString() {
        return firstAlignmentString;
    }

    public void setFirstAlignmentString(String firstAlignmentString) {
        this.firstAlignmentString = firstAlignmentString;
    }

    public String getSecondAlignmentString() {
        return secondAlignmentString;
    }

    public void setSecondAlignmentString(String secondAlignmentString) {
        this.secondAlignmentString = secondAlignmentString;
    }

    public Float getTimeInMilliSeconds() {
        return timeInMilliSeconds;
    }

    public void setTimeInMilliSeconds(Float timeInMilliSeconds) {
        this.timeInMilliSeconds = timeInMilliSeconds;
    }

    public Float getMemoryInKB() {
        return memoryInKB;
    }

    public void setMemoryInKB(Float memoryInKB) {
        this.memoryInKB = memoryInKB;
    }

    @Override
    public String toString() {
        return "SequenceAlignmentOutput{" +
                "costOfAlignment=" + costOfAlignment +
                ", firstAlignmentString='" + firstAlignmentString + '\'' +
                ", secondAlignmentString='" + secondAlignmentString + '\'' +
                ", timeInMilliSeconds=" + timeInMilliSeconds +
                ", memoryInKB=" + memoryInKB +
                '}';
    }
}
