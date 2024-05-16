package com.premiumminds.internship.teknonymy;

import com.premiumminds.internship.teknonymy.Person;

class TeknonymyService implements ITeknonymyService {

  String getRelativeTeknonymy(Character sex, int level) {
    String teknonymy = "";
    if (level > 1) {
      teknonymy = "grand";
      for (int i = level; i > 2; i--)
        teknonymy = "great-" + teknonymy;
    }
    return (sex == 'M') ? teknonymy + "father" : teknonymy + "mother";
  }

  /**
   * Method to get a Person Teknonymy Name
   * 
   * @param Person person
   * @return String which is the Teknonymy Name
   */
  public String getTeknonymy(Person person) {
    Person[] children = person.children();

    // In case the person doesn't havee children
    if (children == null || person.children().length == 0)
      return "";

    Character sex = person.sex();
    String name = "";

    return getRelativeTeknonymy(sex, 0) + " of " + name;
  };
}
