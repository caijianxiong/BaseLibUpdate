package com.example.testdemo.been;

public class Data {

    public Data() {
    }

    public Data(String year, int num, boolean eat, People people) {
        this.year = year;
        this.num = num;
        this.eat = eat;
        this.people = people;
    }

    String year;
    int num;
    boolean eat;
    People people;


    public static class People {
        public People(String name, int age) {
            this.name = name;
            this.age = age;
        }

        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + name.hashCode();
            result = 31 * result + age;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this)
                return true;
            People p = (People) obj;
            return p.age == age && p.name.equals(name);
        }
    }

    public String getYear() {
        return year;
    }

    public int getNum() {
        return num;
    }

    public boolean isEat() {
        return eat;
    }

    public People getPeople() {
        return people;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + year.hashCode();
        result = 31 * result + (eat ? 1 : 0);
        result = 31 * result + people.hashCode();
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        Data a = (Data) obj;
        return a.year.equals(year)
                && a.num == num
                && a.eat == eat
                && a.people.equals(people);
    }
}
