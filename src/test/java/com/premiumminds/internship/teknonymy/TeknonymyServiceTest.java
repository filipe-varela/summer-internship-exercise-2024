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
  public void PersonNoChildrenNotNullTest() {
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
  public void PersonTwoChildYoungestTest() {
    Person person = new Person(
        "John",
        'M',
        new Person[] {
            new Person("Holy", 'F', null, LocalDateTime.of(1046, 1, 1, 0, 0)),
            new Person("Baxter", 'M', null, LocalDateTime.of(1050, 1, 1, 0, 0))
        },
        LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "father of Holy";
    assertEquals(result, expected);
  }

  @Test
  public void PersonTwoChildOldestTest() {
    Person person = new Person(
        "John",
        'M',
        new Person[] {
            new Person("Holy", 'F', null, LocalDateTime.of(1046, 1, 1, 0, 0)),
            new Person("Baxter", 'M', null, LocalDateTime.of(1044, 1, 1, 0, 0))
        },
        LocalDateTime.of(1044, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "father of Baxter";
    assertEquals(result, expected);
  }

  @Test
  public void PersonTwoChildAndOneGrandChildTest() {
    Person person = new Person(
        "John",
        'M',
        new Person[] {
            new Person("Holy", 'F', new Person[] {
                new Person("William", 'M', null, LocalDateTime.of(1066, 1, 1, 0, 0))
            }, LocalDateTime.of(1046, 1, 1, 0, 0)),
            new Person("Baxter", 'M', null, LocalDateTime.of(1044, 1, 1, 0, 0))
        },
        LocalDateTime.of(1044, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "grandfather of William";
    assertEquals(result, expected);
  }

  @Test
  public void PersonTwoChildAndTwoGrandChildTest() {
    Person person = new Person(
        "John",
        'M',
        new Person[] {
            new Person("Holy", 'F', new Person[] {
                new Person("William", 'M', null, LocalDateTime.of(1066, 1, 1, 0, 0)),
                new Person("Vanessa", 'F', null, LocalDateTime.of(1068, 1, 1, 0, 0))
            }, LocalDateTime.of(1046, 1, 1, 0, 0)),
            new Person("Baxter", 'M', null, LocalDateTime.of(1044, 1, 1, 0, 0))
        },
        LocalDateTime.of(1044, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "grandfather of William";
    assertEquals(result, expected);
  }

  @Test
  public void PersonThreeChildAndTwoGrandChildTest() {
    Person person = new Person(
        "John",
        'M',
        new Person[] {
            new Person("Holy", 'F', new Person[] {
                new Person("William", 'M', null, LocalDateTime.of(1066, 1, 1, 0, 0)),
                new Person("Vanessa", 'F', null, LocalDateTime.of(1068, 1, 1, 0, 0))
            }, LocalDateTime.of(1046, 1, 1, 0, 0)),
            new Person("Baxter", 'M', null, LocalDateTime.of(1044, 1, 1, 0, 0)),
            new Person("Rebeca", 'F', null, LocalDateTime.of(1070, 1, 1, 0, 0))
        },
        LocalDateTime.of(1044, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "grandfather of William";
    assertEquals(result, expected);
  }

  @Test
  public void PersonThreeChildAndTwoGrandChildAndOneGGChildTest() {
    Person person = new Person(
        "John",
        'F',
        new Person[] {
            new Person("Holy", 'F', new Person[] {
                new Person("William", 'M', null, LocalDateTime.of(1066, 1, 1, 0, 0)),
                new Person("Vanessa", 'F', new Person[] {
                    new Person("Mark", 'M', null, LocalDateTime.of(1071, 1, 1, 0, 0))
                }, LocalDateTime.of(1068, 1, 1, 0, 0))
            }, LocalDateTime.of(1046, 1, 1, 0, 0)),
            new Person("Baxter", 'M', null, LocalDateTime.of(1044, 1, 1, 0, 0)),
            new Person("Rebeca", 'F', null, LocalDateTime.of(1070, 1, 1, 0, 0))
        },
        LocalDateTime.of(1044, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "great-grandmother of Mark";
    assertEquals(result, expected);
  }

  @Test
  public void BigTreeTest() {
    Person person = new Person(
        "John",
        'F',
        new Person[] {
            new Person("Holy", 'F', new Person[] {
                new Person("William", 'M', null, LocalDateTime.of(1066, 1, 1, 0, 0)),
                new Person("Vanessa", 'F', new Person[] {
                    new Person("Mark", 'M', null, LocalDateTime.of(1071, 1, 1, 0, 0)),
                    new Person("Susan", 'F', null, LocalDateTime.of(1082, 1, 1, 0, 0)),
                    new Person("Kristin", 'F', new Person[] {
                        new Person("Keith", 'M', null, LocalDateTime.of(1085, 1, 1, 0, 0))
                    }, LocalDateTime.of(1075, 1, 1, 0, 0)),
                }, LocalDateTime.of(1068, 1, 1, 0, 0))
            }, LocalDateTime.of(1046, 1, 1, 0, 0)),
            new Person("Baxter", 'M', null, LocalDateTime.of(1044, 1, 1, 0, 0)),
            new Person("Rebeca", 'F', new Person[] {
                new Person("Valister", 'F', null, LocalDateTime.of(1080, 1, 1, 0, 0))
            }, LocalDateTime.of(1070, 1, 1, 0, 0))
        },
        LocalDateTime.of(1044, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "great-great-grandmother of Keith";
    assertEquals(result, expected);
  }

  @Test
  public void DummyATest() {
    Person person = new Person(
        "A",
        'M',
        new Person[] {
            new Person("B", 'M', new Person[] {
                new Person("E", 'M', null, LocalDateTime.of(2019, 1, 1, 0, 0)),
            }, LocalDateTime.of(1046, 1, 1, 0, 0)),
            new Person("C", 'M', null, LocalDateTime.of(2002, 1, 1, 0, 0)),
            new Person("D", 'F', new Person[] {
                new Person("F", 'M', null, LocalDateTime.of(2018, 1, 1, 0, 0)),
                new Person("G", 'M', null, LocalDateTime.of(2021, 1, 1, 0, 0)),
                new Person("H", 'M', null, LocalDateTime.of(2022, 1, 1, 0, 0))
            }, LocalDateTime.of(1070, 1, 1, 0, 0))
        },
        LocalDateTime.of(1980, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "grandfather of F";
    assertEquals(result, expected);
  }

  @Test
  public void DummyBTest() {
    Person person = new Person(
        "A",
        'M',
        new Person[] {
            new Person("B", 'M', new Person[] {
                new Person("E", 'M', null, LocalDateTime.of(2019, 1, 1, 0, 0)),
            }, LocalDateTime.of(1046, 1, 1, 0, 0)),
            new Person("C", 'M', null, LocalDateTime.of(2002, 1, 1, 0, 0)),
            new Person("D", 'F', new Person[] {
                new Person("F", 'M', null, LocalDateTime.of(2018, 1, 1, 0, 0)),
                new Person("G", 'M', null, LocalDateTime.of(2021, 1, 1, 0, 0)),
                new Person("H", 'M', null, LocalDateTime.of(2022, 1, 1, 0, 0))
            }, LocalDateTime.of(1070, 1, 1, 0, 0))
        },
        LocalDateTime.of(1980, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person.children()[0]);
    String expected = "father of E";
    assertEquals(result, expected);
  }

  @Test
  public void DummyDTest() {
    Person person = new Person(
        "A",
        'M',
        new Person[] {
            new Person("B", 'M', new Person[] {
                new Person("E", 'M', null, LocalDateTime.of(2019, 1, 1, 0, 0)),
            }, LocalDateTime.of(1046, 1, 1, 0, 0)),
            new Person("C", 'M', null, LocalDateTime.of(2002, 1, 1, 0, 0)),
            new Person("D", 'F', new Person[] {
                new Person("F", 'M', null, LocalDateTime.of(2018, 1, 1, 0, 0)),
                new Person("G", 'M', null, LocalDateTime.of(2021, 1, 1, 0, 0)),
                new Person("H", 'M', null, LocalDateTime.of(2022, 1, 1, 0, 0))
            }, LocalDateTime.of(1070, 1, 1, 0, 0))
        },
        LocalDateTime.of(1980, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person.children()[2]);
    String expected = "mother of F";
    assertEquals(result, expected);
  }

  @Test
  public void DummyCTest() {
    Person person = new Person(
        "A",
        'M',
        new Person[] {
            new Person("B", 'M', new Person[] {
                new Person("E", 'M', null, LocalDateTime.of(2019, 1, 1, 0, 0)),
            }, LocalDateTime.of(1046, 1, 1, 0, 0)),
            new Person("C", 'M', null, LocalDateTime.of(2002, 1, 1, 0, 0)),
            new Person("D", 'F', new Person[] {
                new Person("F", 'M', null, LocalDateTime.of(2018, 1, 1, 0, 0)),
                new Person("G", 'M', null, LocalDateTime.of(2021, 1, 1, 0, 0)),
                new Person("H", 'M', null, LocalDateTime.of(2022, 1, 1, 0, 0))
            }, LocalDateTime.of(1070, 1, 1, 0, 0))
        },
        LocalDateTime.of(1980, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person.children()[1]);
    String expected = "";
    assertEquals(result, expected);
  }

  @Test
  public void DummyETest() {
    Person person = new Person(
        "A",
        'M',
        new Person[] {
            new Person("B", 'M', new Person[] {
                new Person("E", 'M', null, LocalDateTime.of(2019, 1, 1, 0, 0)),
            }, LocalDateTime.of(1046, 1, 1, 0, 0)),
            new Person("C", 'M', null, LocalDateTime.of(2002, 1, 1, 0, 0)),
            new Person("D", 'F', new Person[] {
                new Person("F", 'M', null, LocalDateTime.of(2018, 1, 1, 0, 0)),
                new Person("G", 'M', null, LocalDateTime.of(2021, 1, 1, 0, 0)),
                new Person("H", 'M', null, LocalDateTime.of(2022, 1, 1, 0, 0))
            }, LocalDateTime.of(1070, 1, 1, 0, 0))
        },
        LocalDateTime.of(1980, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person.children()[0].children()[0]);
    String expected = "";
    assertEquals(result, expected);
  }

  @Test
  public void DummyGTest() {
    Person person = new Person(
        "A",
        'M',
        new Person[] {
            new Person("B", 'M', new Person[] {
                new Person("E", 'M', null, LocalDateTime.of(2019, 1, 1, 0, 0)),
            }, LocalDateTime.of(1046, 1, 1, 0, 0)),
            new Person("C", 'M', null, LocalDateTime.of(2002, 1, 1, 0, 0)),
            new Person("D", 'F', new Person[] {
                new Person("F", 'M', null, LocalDateTime.of(2018, 1, 1, 0, 0)),
                new Person("G", 'M', null, LocalDateTime.of(2021, 1, 1, 0, 0)),
                new Person("H", 'M', null, LocalDateTime.of(2022, 1, 1, 0, 0))
            }, LocalDateTime.of(1070, 1, 1, 0, 0))
        },
        LocalDateTime.of(1980, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person.children()[2].children()[1]);
    String expected = "";
    assertEquals(result, expected);
  }

  @Test
  public void DummyHTest() {
    Person person = new Person(
        "A",
        'M',
        new Person[] {
            new Person("B", 'M', new Person[] {
                new Person("E", 'M', null, LocalDateTime.of(2019, 1, 1, 0, 0)),
            }, LocalDateTime.of(1046, 1, 1, 0, 0)),
            new Person("C", 'M', null, LocalDateTime.of(2002, 1, 1, 0, 0)),
            new Person("D", 'F', new Person[] {
                new Person("F", 'M', null, LocalDateTime.of(2018, 1, 1, 0, 0)),
                new Person("G", 'M', null, LocalDateTime.of(2021, 1, 1, 0, 0)),
                new Person("H", 'M', null, LocalDateTime.of(2022, 1, 1, 0, 0))
            }, LocalDateTime.of(1070, 1, 1, 0, 0))
        },
        LocalDateTime.of(1980, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person.children()[2].children()[2]);
    String expected = "";
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

  @Test(expected = IllegalArgumentException.class)
  public void ZeroGenerationTest() {
    new TeknonymyService().getRelativeTeknonymy('M', 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void NegativeGenerationTest() {
    new TeknonymyService().getRelativeTeknonymy('M', -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void UnvalidSexTest() {
    new TeknonymyService().getRelativeTeknonymy('A', 0);
  }

}