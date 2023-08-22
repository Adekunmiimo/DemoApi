package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

// Bean instance
@Repository("fakeDao")
public class FakePersonDateAccessService implements PersonDao {

    private  static List<Person> DB = new ArrayList<>();
    @Override
    public int insertPerson(UUID id, Person person){
        DB.add(new Person(id, person.getName()));
        return 1;
    }

    @Override
    public List<Person> selectAllPeople(){
        return DB;
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        return DB.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();// find specific person by id
    }

    @Override
    public int deletePersonById(UUID id) {
        Optional<Person> personMaybe = selectPersonById(id);
        if (personMaybe.isEmpty()){
            return 0;
        }
        DB.remove(personMaybe.get());
        return 1;
    }

    @Override
    public int updatePersonById(UUID id, Person update) {
        return selectPersonById(id)//select person
                .map(person -> { //map person
                    int indexOfPersonToUpdate = DB.indexOf(person);
                    if (indexOfPersonToUpdate >= 0) {// found the person
                        DB.set(indexOfPersonToUpdate, new Person(id, update.getName()));// set content of person to new person
                        return 1;
                    }
                    return  0;// if not found, return 0
                })
                .orElse(0);// if selectPerson by id is not present, just return 0
    }

}
