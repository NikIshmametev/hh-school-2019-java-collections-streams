package tasks;

import common.Person;
import common.Task;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
А теперь о горьком
Всем придется читать код
А некоторым придется читать код, написанный мною
Сочувствую им
Спасите будущих жертв, и исправьте здесь все, что вам не по душе!
P.S. функции тут разные и рабочие (наверное), но вот их понятность и эффективность страдает (аж пришлось писать комменты)
P.P.S Здесь ваши правки желательно прокомментировать (можно на гитхабе в пулл реквесте)
 */
public class Task8 implements Task {

    private long count;

    //Не хотим выдывать апи нашу фальшивую персону, поэтому конвертим начиная со второй
    public List<String> getNames(List<Person> persons) {
        //skip 1 элемент. Если пустой - вернет пустой лист
        return persons.stream().skip(1).map(Person::getFirstName).collect(Collectors.toList());
    }

    //ну и различные имена тоже хочется
    public Set<String> getDifferentNames(List<Person> persons) {
        //Distinct не нужен, Set уже включает в себя только различные элементы
        //Да и вообще stream избыточен
        return new HashSet<>(getNames(persons));
    }

    //Для фронтов выдадим полное имя, а то сами не могут
    public String convertPersonToString(Person person) {
        // добавил все значения в стрим
        return Stream.of(person.getSecondName(), person.getFirstName(), person.getMiddleName())
                .filter(s -> s != null && !s.isEmpty())
                .collect(Collectors.joining(" "));
    }

    // словарь id персоны -> ее имя
    public Map<Integer, String> getPersonNames(Collection<Person> persons) {
        // Заменил на stream, возвращающий map

        return persons.stream()
                .collect(Collectors.toMap(x -> x.getId(), x -> convertPersonToString(x), (a, b) -> a));
    }

    // есть ли совпадающие в двух коллекциях персоны?
    public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
        // Заменил двойной цикл на подсчет элементов в map, ключами которой являются объекты Person,
        // а значениями - кол-во таких объектов в двух листах
        return persons1.stream().anyMatch(person -> persons2.contains(person));
    }

    //Выглядит вроде неплохо...
    public long countEven(Stream<Integer> numbers) {
        // Убрал объявление переменной и использовал встроенный метод подсчета
        return numbers.filter(num -> num % 2 == 0).count();
    }

    @Override
    public boolean check() {
        System.out.println("Слабо дойти до сюда и исправить Fail этой таски?");
        boolean codeSmellsGood = false;
        boolean reviewerDrunk = false;
        return codeSmellsGood || reviewerDrunk;
    }
}
