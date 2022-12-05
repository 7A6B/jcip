package com.jcip.chapter4;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

class Person{
    String name;
    public Person(String name){this.name = name;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" + "name='" + name + '\'' + '}';
    }
}
public class PersonSet {
    private final Set<Person> mySet = new HashSet<>();
    public synchronized void addPerson(Person person){
        mySet.add(person);
    }
    public synchronized boolean contains(Person person){
        return mySet.contains(person);
    }
    //新增一个对mySet的访问
    public Set<Person> getMySet(){
        return mySet;
    }

    public static void main(String[] args) {
        final PersonSet personSet = new PersonSet();
        final Person person = new Person("tom");
        personSet.addPerson(person);
        new Thread(()->{
            final Set<Person> set = personSet.getMySet();
            //在线程中访问person对象
            for (Person item : set) {
                System.out.println(item);
            }
            //模拟 作其他的业务
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for (Person item : set) {
                System.out.println(item);
            }

        }).start();

        new Thread(()->{
            person.setName("kobe");
        }).start();
    }
}
