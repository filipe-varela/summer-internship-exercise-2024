package com.premiumminds.internship.teknonymy;

import com.premiumminds.internship.teknonymy.TeknonymyService;
import com.premiumminds.internship.teknonymy.Person;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TeknonymyServiceTest {

  /**
   * The corresponding implementations to test.
   *
   * If you want, you can make others :)
   *
   */
  public TeknonymyServiceTest() {
  };

  @Test
  public void PersonNoChildrenTest() {
    Person person = new Person("John", 'M', null, LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "";
    assertEquals(result, expected);
  }

  @Test
  public void PersonNoChildren2Test() {
    Person person = new Person("John", 'M', new Person[] {}, LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "";
    assertEquals(result, expected);
  }

  @Test
  public void PersonOneChildTest() {
    Person person = new Person(
        "John",
        'M',
        new Person[] { new Person("Holy", 'F', null, LocalDateTime.of(1046, 1, 1, 0, 0)) },
        LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "father of Holy";
    assertEquals(result, expected);
  }

  @Test
  public void FatherTest() {
    String result = new TeknonymyService().getRelativeTeknonymy('M', 1);
    String expected = "father";
    assertEquals(result, expected);
  }

  @Test
  public void MotherTest() {
    String result = new TeknonymyService().getRelativeTeknonymy('F', 1);
    String expected = "mother";
    assertEquals(result, expected);
  }

  @Test
  public void GrandFatherTest() {
    String result = new TeknonymyService().getRelativeTeknonymy('M', 2);
    String expected = "grandfather";
    assertEquals(result, expected);
  }

  @Test
  public void GrandMotherTest() {
    String result = new TeknonymyService().getRelativeTeknonymy('F', 2);
    String expected = "grandmother";
    assertEquals(result, expected);
  }

  @Test
  public void GreatGrandFatherTest() {
    String result = new TeknonymyService().getRelativeTeknonymy('M', 3);
    String expected = "great-grandfather";
    assertEquals(result, expected);
  }

  @Test
  public void GreatGrandMotherTest() {
    String result = new TeknonymyService().getRelativeTeknonymy('F', 3);
    String expected = "great-grandmother";
    assertEquals(result, expected);
  }

  @Test
  public void GreatGreatGrandFatherTest() {
    String result = new TeknonymyService().getRelativeTeknonymy('M', 4);
    String expected = "great-great-grandfather";
    assertEquals(result, expected);
  }

  @Test
  public void GreatGreatGrandMotherTest() {
    String result = new TeknonymyService().getRelativeTeknonymy('F', 4);
    String expected = "great-great-grandmother";
    assertEquals(result, expected);
  }

}