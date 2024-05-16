package com.premiumminds.internship.teknonymy;

import java.util.ArrayList;
import java.util.List;

import com.premiumminds.internship.teknonymy.Person;

class TeknonymyService implements ITeknonymyService {

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

    // otherwise, retrieve teh teknonymy of the the current person
    Character sex = person.sex();
    Person finalChildren = getMostDistantChildren(person);
    String name = finalChildren.name();
    int level = finalChildren.generation();

    return getRelativeTeknonymy(sex, level) + " of " + name;
  };

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
    if (sex != 'M' && sex != 'F')
      throw new IllegalArgumentException("The sex argument = " + sex + " must be either 'M' or 'F'.");

    String teknonymy = "";
    if (level > 1) {
      teknonymy = "grand";
      for (int i = level; i > 2; i--)
        teknonymy = "great-" + teknonymy;
    }
    return (sex == 'M') ? teknonymy + "father" : teknonymy + "mother";
  }

  /**
   * Retrieve the most distant descendent from the root person. It evaluates
   * each level (horizontally) of the family tree as to find the oldest person
   * of the most recent generation.
   * 
   * @param root Where the search begins. It's assumed the root has children.
   * @return The most distant descendent from root.
   */
  Person getMostDistantChildren(final Person root) {
    final List<Person> personQueue = new ArrayList<Person>();

    // Adds root to the queue and set its family tree level to 0 as it's the
    // base, and increments its children afterwards.
    personQueue.add(root.withLevel(0));
    personQueue.get(0).incrementChildrenGeneration();

    Person distantDescendent = new Person(personQueue.get(0));
    Person currentPerson = new Person(personQueue.get(0));

    while (!personQueue.isEmpty()) {
      currentPerson = personQueue.remove(0);

      if (doesUpdateTeknonymyPerson(distantDescendent, currentPerson)) {
        distantDescendent = new Person(currentPerson);
      }

      if (!currentPerson.hasChildren())
        continue;

      for (Person person : currentPerson.children()) {
        // Update children level, if any
        if (person.hasChildren())
          person.incrementChildrenGeneration();

        personQueue.add(0, person);
      }
    }

    return distantDescendent;

  }

  /**
   * Evaluates if the target's person should be updated with the current person.
   * This evaluation is done by checking if target is more distant or, in the
   * same generation, older than the current one.
   * 
   * @param target  Person to evaluate
   * @param current Current person to compare from
   * @return true in case target is more distant/older than the current
   */
  private boolean doesUpdateTeknonymyPerson(Person target, Person current) {
    boolean isOlderSameGeneration = current.isSameGenerationAs(target) &&
        current.isOlderThan(target);

    return !current.equals(target) &&
        current.isNewerGenerationThan(target) ||
        isOlderSameGeneration;
  }
}
