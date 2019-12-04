package tasks;

import common.Area;
import common.Person;
import common.Task;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
Имеются
- коллекция персон Collection<Person>
- словарь Map<Integer, Set<Integer>>, сопоставляющий каждой персоне множество id регионов
- коллекция всех регионов Collection<Area>
На выходе хочется получить множество строк вида "Имя - регион". Если у персон регионов несколько, таких строк так же будет несколько
 */
public class Task6 implements Task {

    private Set<String> getPersonDescriptions(Collection<Person> persons,
                                              Map<Integer, Set<Integer>> personAreaIds,
                                              Collection<Area> areas) {

        // Если у id нет регионов, то хотелось бы вывести имя и ничего, например, "Олег - "
        // Но сейчас этот id вообще не выведется
        return persons.stream()
                .flatMap(person -> personAreaIds.get(person.getId()).stream()
                        .flatMap(region -> Stream.of(String.format("%s - %s",
                                person.getFirstName(),
                                areas.stream()
                                        .filter(area -> area.getId().equals(region))
                                        .map(Area::getName)
                                        .reduce("", (s1, s2) -> s1 + s2)
                        )))
                        .collect(Collectors.toSet())
                        .stream()
                )
                .collect(Collectors.toSet());
    }

    @Override
    public boolean check() {
        List<Person> persons = List.of(
                new Person(1, "Oleg", Instant.now()),
                new Person(2, "Vasya", Instant.now())
        );
        Map<Integer, Set<Integer>> personAreaIds = Map.of(1, Set.of(1, 2), 2, Set.of(2, 3));
        List<Area> areas = List.of(new Area(1, "Moscow"), new Area(2, "Spb"), new Area(3, "Ivanovo"));
        return getPersonDescriptions(persons, personAreaIds, areas)
                .equals(Set.of("Oleg - Moscow", "Oleg - Spb", "Vasya - Spb", "Vasya - Ivanovo"));
    }
}
