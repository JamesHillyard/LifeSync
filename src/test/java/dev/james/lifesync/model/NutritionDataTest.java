package dev.james.lifesync.model;

import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class NutritionDataTest {

    @Test
    public void testCreateNutritionData() {
        NutritionData nutritionData = new NutritionData(-1, Date.valueOf(LocalDate.now()), "Apple", 90.5F, 1.98F, 20.43F);

        assertEquals(-1, nutritionData.getUserid());
        assertEquals(Date.valueOf(LocalDate.now()), nutritionData.getDate());
        assertEquals("Apple", nutritionData.getFoodName());
        assertEquals(90.5F, nutritionData.getCalories());
        assertEquals(1.98F, nutritionData.getFat());
        assertEquals(20.43F, nutritionData.getSugar());
    }

    @Test
    public void testCreateNutritionDataMinimalConstructor() {
        NutritionData nutritionData = new NutritionData(Date.valueOf(LocalDate.now()), 30, 45, 10);

        assertEquals(Date.valueOf(LocalDate.now()), nutritionData.getDate());
        assertEquals(30, nutritionData.getCalories());
        assertEquals(45, nutritionData.getFat());
        assertEquals(10, nutritionData.getSugar());

        assertEquals("", nutritionData.getFoodName());
        assertEquals(0, nutritionData.getUserid());
        assertEquals(0, nutritionData.getId());
    }

    @Test
    public void testCreateNutritionDataNoArgsConstructor() {
        NutritionData nutritionData = new NutritionData();

        assertNull(nutritionData.getDate());
        assertEquals(0, nutritionData.getCalories());
        assertEquals(0, nutritionData.getFat());
        assertEquals(0, nutritionData.getSugar());
        assertEquals("", nutritionData.getFoodName());
        assertEquals(0, nutritionData.getUserid());
        assertEquals(0, nutritionData.getId());
    }

    @Test
    public void testGettersAndSetters() {
        NutritionData nutritionData = new NutritionData();
        nutritionData.setId(10);
        nutritionData.setUserid(32);
        nutritionData.setDate(Date.valueOf(LocalDate.now()));
        nutritionData.setCalories(30);
        nutritionData.setFat(5.5F);
        nutritionData.setSugar(2);
        nutritionData.setFoodName("Chocolate");

        assertEquals(10, nutritionData.getId());
        assertEquals(32, nutritionData.getUserid());
        assertEquals(Date.valueOf(LocalDate.now()), nutritionData.getDate());
        assertEquals(30, nutritionData.getCalories());
        assertEquals(5.5, nutritionData.getFat());
        assertEquals(2, nutritionData.getSugar());
        assertEquals("Chocolate", nutritionData.getFoodName());
    }

    @Test
    public void testEqualsAndHashCode() {
        NutritionData nutritionData1 = new NutritionData(Date.valueOf(LocalDate.now()), 30, 45, 10);
        NutritionData nutritionData2 = new NutritionData(Date.valueOf(LocalDate.now()), 30, 45, 10);

        assertEquals(nutritionData1, nutritionData2);
        assertTrue(nutritionData1.equals(nutritionData2));
        assertTrue(nutritionData2.equals(nutritionData1));
        assertEquals(nutritionData1.hashCode(), nutritionData2.hashCode());
    }

    @Test
    public void testNotEquals() {
        NutritionData nutritionData1 = new NutritionData(Date.valueOf(LocalDate.now()), 30, 46, 10);
        NutritionData nutritionData2 = new NutritionData(Date.valueOf(LocalDate.now()), 30, 45, 10);

        assertNotEquals(nutritionData1, nutritionData2);
        assertFalse(nutritionData1.equals(nutritionData2));
        assertFalse(nutritionData2.equals(nutritionData1));
        assertNotEquals(nutritionData1.hashCode(), nutritionData2.hashCode());
    }

    @Test
    public void testNotEqualsWithDifferentClass() {
        String string = "Test";
        NutritionData nutritionData = new NutritionData(Date.valueOf(LocalDate.now()), 30, 46, 10);

        assertNotEquals(nutritionData, string);
        assertFalse(nutritionData.equals(string));
    }

    @Test
    public void testEqualsOnSameObject() {
        NutritionData nutritionData = new NutritionData(Date.valueOf(LocalDate.now()), 30, 46, 10);

        assertTrue(nutritionData.equals(nutritionData));
    }

    @Test
    public void testGetActivityNameCapitalisesFirstLetter() {
        NutritionData nutritionData = new NutritionData();

        // Test lowercase
        nutritionData.setFoodName("apple");
        assertEquals("Apple", nutritionData.getFoodName());

        // Test already capitalised
        nutritionData.setFoodName("Orange");
        assertEquals("Orange", nutritionData.getFoodName());

        // Test all caps
        nutritionData.setFoodName("BANANA");
        assertEquals("Banana", nutritionData.getFoodName());

        // Test all caps first letter lowercase
        nutritionData.setFoodName("lEMON");
        assertEquals("Lemon", nutritionData.getFoodName());
    }
}