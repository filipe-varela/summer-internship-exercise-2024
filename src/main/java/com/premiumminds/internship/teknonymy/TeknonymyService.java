package com.premiumminds.internship.teknonymy;

import com.premiumminds.internship.teknonymy.Person;

class TeknonymyService implements ITeknonymyService {

  /**
   * Method to get a Person Teknonymy Name
   * 
   * @param Person person
   * @return String which is the Teknonymy Name
   */
  public String getTeknonymy(Person person) {
    Person[] children = person.children();
    if (children == null || person.children().length == 0)
      return "";
    else
      return "Algo";
  };
}
