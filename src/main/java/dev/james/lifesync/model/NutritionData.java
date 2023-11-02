package dev.james.lifesync.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.sql.Date;
import java.util.Objects;

@Entity
public class NutritionData {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "userid")
    private int userid;
    @Basic
    @Column(name = "date")
    private Date date;
    @Basic
    @Column(name = "foodName")
    private String foodName;
    @Basic
    @Column(name = "calories")
    private float calories;
    @Basic
    @Column(name = "fat")
    private float fat;
    @Basic
    @Column(name = "sugar")
    private float sugar;

    public NutritionData() {

    }

    public NutritionData(Date date, float calories, float fat, float sugar) {
        this.date = date;
        this.calories = calories;
        this.fat = fat;
        this.sugar = sugar;
    }

    public NutritionData(int userid, Date date, String foodName, float calories, float fat, float sugar) {
        this.userid = userid;
        this.date = date;
        this.foodName = foodName;
        this.calories = calories;
        this.fat = fat;
        this.sugar = sugar;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFoodName() {
        if (foodName == null) {
            return "";
        }
        return foodName.substring(0,1).toUpperCase() + foodName.substring(1).toLowerCase();
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public float getSugar() {
        return sugar;
    }

    public void setSugar(float sugar) {
        this.sugar = sugar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NutritionData that = (NutritionData) o;
        return id == that.id && userid == that.userid && Objects.equals(date, that.date) && Objects.equals(foodName, that.foodName) && Objects.equals(calories, that.calories) && Objects.equals(fat, that.fat) && Objects.equals(sugar, that.sugar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userid, date, foodName, calories, fat, sugar);
    }
}
