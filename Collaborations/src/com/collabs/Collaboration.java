package com.collabs;

public class Collaboration implements Comparable<Collaboration> {

    private final int emp1ID;
    private final int emp2ID;
    private final int projID;
    private int days;
    //days is not final because it has to change

    public Collaboration(int emp1ID, int emp2ID, int projID, int days) {
        this.emp1ID = emp1ID;
        this.emp2ID = emp2ID;
        this.projID = projID;
        this.days = days;
    }

    public void addDays(int days)
    {
        this.days += days;
    }

    public int getEmp1ID() {
        return emp1ID;
    }

    public int getEmp2ID() {
        return emp2ID;
    }

    public int getProjID() {
        return projID;
    }

    public int getDays() {
        return days;
    }

    @Override
    public String toString() {
        return String.format("Employee1ID = %d, Employee2ID = %d, ProjectID = %d, Days worked = %d",
                getEmp1ID(), getEmp2ID(), getProjID(), getDays());
    }

    //Overriding those methods because I don't want them to check the field "days"
    @Override
    public int compareTo(Collaboration collab) {
        if(this.emp1ID == collab.emp1ID && this.emp2ID == collab.emp2ID && this.projID == collab.projID) {
            return 0;
        }
        return 1;
    }

    @Override
    public boolean equals(Object object) {
        boolean isEqual = false;

        if (object instanceof Collaboration) {
            Collaboration ptr = (Collaboration) object;
            isEqual = ptr.emp1ID == this.emp1ID && ptr.emp2ID == this.emp2ID && ptr.projID == this.projID;
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + emp1ID;
        result = 31 * result + emp2ID;
        result = 31 * result + projID;
        return result;
    }
}
