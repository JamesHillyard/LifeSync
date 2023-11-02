package dev.james.lifesync.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.sql.Date;
import java.time.Duration;
import java.util.Objects;

@Entity
public class ExerciseData {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "userid")
    private int userid;
    @Basic
    @Column(name = "activityName")
    private String activityName;
    @Basic
    @Column(name = "date")
    private Date date;
    @Basic
    @Column(name = "duration")
    private int duration;
    @Basic
    @Column(name = "caloriesBurnt")
    private int caloriesBurnt;

    public ExerciseData() {

    }

    public ExerciseData(Date date, int duration, int caloriesBurnt) {
        this.date = date;
        this.duration = duration;
        this.caloriesBurnt = caloriesBurnt;
    }

    public ExerciseData(int userid, String activityName, Date date, int duration, int caloriesBurnt) {
        this.userid = userid;
        this.activityName = activityName;
        this.date = date;
        this.duration = duration;
        this.caloriesBurnt = caloriesBurnt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    // This will capitalise the first letter
    public String getActivityName() {
        if (activityName == null) {
            return "";
        }
        return activityName.substring(0,1).toUpperCase() + activityName.substring(1).toLowerCase();
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCaloriesBurnt() {
        return caloriesBurnt;
    }

    public void setCaloriesBurnt(int caloriesBurnt) {
        this.caloriesBurnt = caloriesBurnt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExerciseData that = (ExerciseData) o;
        return id == that.id && userid == that.userid && duration == that.duration && caloriesBurnt == that.caloriesBurnt && Objects.equals(activityName, that.activityName) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userid, activityName, date, duration, caloriesBurnt);
    }

    public double getDurationInHoursAndMinutes() {
        if (duration > 0) {
            return (double) duration / 60; // Divide by 60 to convert minutes to hours and minutes
        } else {
            return 0.0;
        }
    }

    public String getDurationInHoursAndMinutesHumanReadable() {
        if (duration > 0) {
            return String.format("%d Hours %d Minutes",
                    Duration.ofMinutes(duration).toHoursPart(),
                    Duration.ofMinutes(duration).toMinutesPart());
        } else {
            return null;
        }
    }
}
