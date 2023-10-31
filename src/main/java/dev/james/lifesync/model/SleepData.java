package dev.james.lifesync.model;

import jakarta.persistence.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Entity
public class SleepData {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "sleepid")
    private int sleepid;
    @Basic
    @Column(name = "userid")
    private int userid;
    @Basic
    @Column(name = "starttime")
    private Timestamp starttime;
    @Basic
    @Column(name = "endtime")
    private Timestamp endtime;

    @Transient
    private long duration;

    public SleepData(int userid, Timestamp starttime, Timestamp endtime) {
        this.userid = userid;
        this.starttime = starttime;
        this.endtime = endtime;
    }

    public SleepData() {

    }

    public int getSleepid() {
        return sleepid;
    }

    public void setSleepid(int sleepid) {
        this.sleepid = sleepid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public Timestamp getStarttime() {
        return starttime;
    }

    public void setStarttime(Timestamp starttime) {
        this.starttime = starttime;
    }

    public Timestamp getEndtime() {
        return endtime;
    }

    public void setEndtime(Timestamp endtime) {
        this.endtime = endtime;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SleepData sleepData = (SleepData) o;
        return sleepid == sleepData.sleepid && userid == sleepData.userid && Objects.equals(starttime, sleepData.starttime) && Objects.equals(endtime, sleepData.endtime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sleepid, userid, starttime, endtime);
    }

    // These methods are used in the view state to display data in human readable format. All data returned is transient
    public double getDurationInHoursAndMinutes() {
        if (starttime != null && endtime != null) {
            long milliseconds = endtime.getTime() - starttime.getTime();
            return (double) milliseconds / 3600000;
        } else {
            return 0.0;
        }
    }

    public String getDurationInHoursAndMinutesHumanReadable() {
        if (starttime != null && endtime != null) {
            long milliseconds = endtime.getTime() - starttime.getTime();
            return String.format("%d Hours %d Minutes",
                    TimeUnit.MILLISECONDS.toHours(milliseconds),
                    TimeUnit.MILLISECONDS.toMinutes(milliseconds) % 60);
        } else {
            return null;
        }
    }
}
