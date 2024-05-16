package com.premiumminds.internship.teknonymy;

import java.time.LocalDateTime;

public record Person(
        String name,
        Character sex,
        Person[] children,
        LocalDateTime dateOfBirth,
        int level) {
    public Person(String name, Character sex, Person[] children, LocalDateTime dateOfBirth) {
        this(name, sex, children, dateOfBirth, 0);
    }

    public Person(Person newPerson) {
        this(
                newPerson.name(),
                newPerson.sex(),
                newPerson.children(),
                newPerson.dateOfBirth(),
                newPerson.level());
    }

    public Person withLevel(int newLevel) {
        return new Person(
                this.name,
                this.sex,
                this.children,
                this.dateOfBirth,
                newLevel);
    }

    public boolean isMoreRecentThan(Person test) {
        return test.level() < this.level;
    }

    public boolean isOlderThan(Person test) {
        return this.dateOfBirth.isBefore(test.dateOfBirth());
    }

    public boolean sameGenerationAs(Person test) {
        return test.level() == this.level;
    }

    public void incrementChildrenLevel() {
        for (int i = 0; i < children.length; i++) {
            children[i] = children[i].withLevel(level + 1);
        }
    }
}
