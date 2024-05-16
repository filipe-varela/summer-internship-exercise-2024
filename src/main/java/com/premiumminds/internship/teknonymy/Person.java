package com.premiumminds.internship.teknonymy;

import java.time.LocalDateTime;

public record Person(
        String name,
        Character sex,
        Person[] children,
        LocalDateTime dateOfBirth,
        int generation) {
    public Person(String name, Character sex, Person[] children, LocalDateTime dateOfBirth) {
        this(name, sex, children, dateOfBirth, 0);
    }

    public Person(Person newPerson) {
        this(
                newPerson.name(),
                newPerson.sex(),
                newPerson.children(),
                newPerson.dateOfBirth(),
                newPerson.generation());
    }

    public Person withLevel(int newLevel) {
        return new Person(
                this.name,
                this.sex,
                this.children,
                this.dateOfBirth,
                newLevel);
    }

    public boolean isNewerGenerationThan(Person test) {
        return test.generation() < this.generation;
    }

    public boolean isOlderThan(Person test) {
        return this.dateOfBirth.isBefore(test.dateOfBirth());
    }

    public boolean isSameGenerationAs(Person test) {
        return test.generation() == this.generation;
    }

    public void incrementChildrenGeneration() {
        for (int i = 0; i < children.length; i++) {
            children[i] = children[i].withLevel(generation + 1);
        }
    }

    public boolean hasChildren() {
        return children != null && children.length > 0;
    }
}
