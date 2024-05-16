package com.premiumminds.internship.teknonymy;

import java.util.ArrayList;
import java.util.List;

import com.premiumminds.internship.teknonymy.Person;

class TeknonymyService implements ITeknonymyService {

  /**
   * <p>
   * Get corresponding relative teknonymy given the sex and level in the family
   * tree. That is, if:
   * </p>
   * <p>
   * - level == 1: Parent
   * </p>
   * <p>
   * - level == 2: Grandparent
   * </p>
   * <p>
   * - level >= 3: ((level-2) * Great-)Grandparent
   * </p>
   * 
   * @param sex   either 'M' for male or 'F' for female
   * @param level integer value for the current family tree level
   * @return String with the corresponding relative teknonymy
   */
  String getRelativeTeknonymy(final Character sex, final int level) {
    if (level < 1)
      throw new IllegalArgumentException("The argument level = " + level + " must be greater than zero.");
    String teknonymy = "";
    if (level > 1) {
      teknonymy = "grand";
      for (int i = level; i > 2; i--)
        teknonymy = "great-" + teknonymy;
    }
    return (sex == 'M') ? teknonymy + "father" : teknonymy + "mother";
  }

  Person getRecentOldestChildren(final Person root) {
    final List<Person> personQueue = new ArrayList<Person>();
    personQueue.add(root.withLevel(0));

    personQueue.get(0).incrementChildrenLevel();

    Person teknonymyPerson = new Person(personQueue.get(0));
    Person currentPerson = new Person(personQueue.get(0));

    while (!personQueue.isEmpty()) {
      currentPerson = personQueue.remove(0);

      // validate oldest and recent children
      if (!currentPerson.equals(teknonymyPerson) &&
          doesUpdateTeknonymyPerson(teknonymyPerson, currentPerson)) {
        teknonymyPerson = new Person(currentPerson);
      }

      if (!currentPerson.hasChildren())
        continue;

      for (Person person : currentPerson.children()) {
        if (person.hasChildren()) {
          // Update children level, if any
          person.incrementChildrenLevel();
        }

        // Add to queue since it has children
        personQueue.add(0, person);
      }
    }

    return teknonymyPerson;

  }

  private boolean doesUpdateTeknonymyPerson(Person teknonymyPerson, Person currentPerson) {
    boolean isOlderSameGeneration = currentPerson.isSameGenerationAs(teknonymyPerson)
        && currentPerson.isOlderThan(teknonymyPerson);
    return currentPerson.isMoreRecentThan(teknonymyPerson) || isOlderSameGeneration;
  }

  /**
   * Method to get a Person Teknonymy Name
   * 
   * @param Person person
   * @return String which is the Teknonymy Name
   */
  public String getTeknonymy(Person person) {
    // In case the person doesn't havee children
    if (!person.hasChildren())
      return "";

    Character sex = person.sex();
    Person finalChildren = getRecentOldestChildren(person);
    String name = finalChildren.name();
    int level = finalChildren.level();

    return getRelativeTeknonymy(sex, level) + " of " + name;
  };
}
