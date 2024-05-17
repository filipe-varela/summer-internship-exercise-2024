package com.premiumminds.internship.teknonymy;

import java.time.LocalDateTime;

public record Person(
        String name,
        Character sex,
        Person[] children,
        LocalDateTime dateOfBirth,
        int generation) {

    public Person(
            String name,
            Character sex,
            Person[] children,
            LocalDateTime dateOfBirth) {
        this(name, sex, children, dateOfBirth, 0);
    }

    public Person(final Person newPerson) {
        this(
                newPerson.name(),
                newPerson.sex(),
                newPerson.children(),
                newPerson.dateOfBirth(),
                newPerson.generation());
    }

    public Person withGeneration(int newGeneration) {
        return new Person(
                this.name,
                this.sex,
                this.children,
                this.dateOfBirth,
                newGeneration);
    }

    public Person withChildren(final Person[] newChildren) {
        return new Person(
                this.name,
                this.sex,
                newChildren,
                this.dateOfBirth,
                this.generation);
    }

    public boolean isNewerGenerationThan(final Person test) {
        return test.generation() < this.generation;
    }

    public boolean isOlderThan(final Person test) {
        return this.dateOfBirth.isBefore(test.dateOfBirth());
    }

    public boolean isSameGenerationAs(final Person test) {
        return test.generation() == this.generation;
    }

    public Person incrementChildrenGeneration() {
        Person[] incrementedChildren = new Person[children.length];
        for (int i = 0; i < children.length; i++)
            incrementedChildren[i] = children[i].withGeneration(generation + 1);
        return this.withChildren(incrementedChildren);
    }

    public Person incrementChildrenGenerationIfAny() {
        if (this.hasChildren())
            return this.incrementChildrenGeneration();
        else
            return new Person(this);
    }

    public boolean hasChildren() {
        return children != null && children.length > 0;
    }
}
