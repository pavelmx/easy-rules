package com.example.innowise.model;

public class Person extends BaseModel{
    private String name;
    private int age;
    private boolean adult;
    private int cnt;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", adult=" + adult +
                ", cnt=" + cnt +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public Person(String id, String name, int age) {
        super(id);
        this.name = name;
        this.age = age;
    }
}