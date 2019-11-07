package com.company.sessionplanner;

public class Person {

    private int age;

    private String name;

    @BuilderProperty
    public void setAge(int age) {
        this.age = age;
    }

    @BuilderProperty
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    //Getters here
}

class Test {
    public static void main(String[] args) {

        Person person = new PersonBuilder()
                .setAge(25)
                .setName("John")
                .build();

    }
}
