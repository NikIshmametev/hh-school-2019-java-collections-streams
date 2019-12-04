package tasks;

import common.Person;
import common.PersonService;
import common.Task;

import java.util.*;
import java.util.stream.Collectors;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис (он выдает несортированный Set<Person>)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимпотику работы
 */
public class Task1 implements Task {

    // !!! Редактируйте этот метод !!!
    private List<Person> findOrderedPersons(List<Integer> personIds) {
        Set<Person> persons = PersonService.findPersons(personIds);

        Map<Person, Integer> map = new HashMap<>();
        for (Person person : persons) {
            map.put(person, Collections.binarySearch(personIds, person.getId()));
        }
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        // Ассимптотика O(N*log(N)). Как заиниализировать мапу в стриме не нашел. Но порядок работает
    }

    @Override
    public boolean check() {
        List<Integer> ids = List.of(2, 1, 3);

        return findOrderedPersons(ids).stream()
                .map(Person::getId)
                .collect(Collectors.toList())
                .equals(ids);
    }

}
