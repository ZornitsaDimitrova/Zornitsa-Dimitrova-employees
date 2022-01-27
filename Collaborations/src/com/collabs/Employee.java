package com.collabs;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

public class Employee implements Comparable<Employee> {
    private final int empID;
    private final int projID;
    private final Date dateFrom;
    private final Date dateTo;

    private static DateTimeFormatter formatter = null;
    public Employee(int empID, int projID, String dateFrom, String dateTo) {
        this.empID = empID;
        this.projID = projID;
        this.dateFrom = stringAsDate(dateFrom);
        this.dateTo = dateTo.equals("NULL") ? new Date() : stringAsDate(dateTo);
    }

    //Using Joda time in order to support more than one date format
    private Date stringAsDate(String strDate) {
        if (strDate.isEmpty()) {
            return null;
        }
        else {
            try {
                if (formatter == null) {
                    formatter = new DateTimeFormatterBuilder().appendOptional(DateTimeFormat.forPattern("yyyyMMdd")
                            .getParser())
                            .appendOptional(DateTimeFormat.forPattern("yyyy-MM-dd")
                                    .getParser())
                            .appendOptional(DateTimeFormat.forPattern("MM/dd/yyyy")
                                    .getParser())
                            .toFormatter();
                }

                LocalDateTime dateTime = LocalDateTime.parse(strDate, formatter);
                return dateTime.toDate();
            }
            catch (Exception e) {
                return null;
            }
        }
    }

    public int getEmpID() {
        return empID;
    }

    public int getProjID() {
        return projID;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDateFrom = formatter.format(getDateFrom());
        String strDateTo = formatter.format(getDateTo());
        return String.format("EmployeeID = %d, ProjectID = %d, DateFrom = %s, DateTo = %s",
                getEmpID(), getProjID(), strDateFrom, strDateTo);
    }

    //Overriding compare to, because I want to sort the list only with checking their IDs
    @Override
    public int compareTo(Employee emp) {
        if(this.getEmpID() > emp.getEmpID()) {
            return 1;
        } else if (this.getEmpID() < emp.getEmpID()) {
            return -1;
        } else {
            return 0;
        }
    }
}
