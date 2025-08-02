package com.example.reactive.rxjava;

import rx.Observable;
import rx.observables.BlockingObservable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObservableImpl {
    private static Integer[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    private static String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h", "i"};
    private static String[] titles = {"title"};
    private static List<String> titleList = Arrays.asList(titles);

    static Observable<String> getTitle() {
        return Observable.from(titleList);
    }

    public static void main(String[] args) {
        Map<Integer, Integer> m = new HashMap<>();
        m.put(4,5);
        m.put(5,6);
        System.out.println(m.computeIfAbsent(6, n -> n +1));;
        System.out.println(m.computeIfPresent(5, (i,j) -> i * j));
        m.forEach((i,j)-> System.out.println(i+j));

        System.out.println("-------Just-----------");
        Observable<String> observable = Observable.just("Hello");
        observable.subscribe(
                System.out::println, //onNext
                Throwable::printStackTrace, //onError
                () -> System.out.println("onCompleted")  //onCompleted
        );

        BlockingObservable<String> blockingObservable = observable.toBlocking();

        System.out.println();
        System.out.println("-------Map-----------");
        Observable.from(letters)
                .map(String::toUpperCase)
                .subscribe(System.out::print);

        System.out.println();
        System.out.println("-------FlatMap-----------");
        Observable.just("book1", "book2")
                .flatMap(s -> getTitle())
                .subscribe(System.out::print);

        System.out.println();
        System.out.println("--------Scan----------");
        Observable.from(letters)
                .scan(new StringBuilder(), StringBuilder::append)
                .subscribe(System.out::println);

        System.out.println();
        System.out.println("------GroubBy------------");
        Observable.from(numbers)
                .groupBy(integer -> {
                    if(integer % 2 == 0){
                        return "TWO";
                    }
                    if(integer % 3 == 0){
                        return "THREE";
                    }
                    if(integer % 5 == 0){
                        return "FIVE";
                    }

                    if(integer % 7 == 0){
                        return "SEVEN";
                    }
                    return "OTHER";
                })
                .subscribe((group) -> group.subscribe((number) -> {
                    System.out.println(group.getKey() + " : " + number);
                }));

        System.out.println();
        System.out.println("-------Filter-----------");
        Observable.from(numbers)
                .filter(i -> (i % 2 == 1))
                .subscribe(System.out::println);

        System.out.println("------DefaultIfEmpty------------");
        Observable.empty()
                .defaultIfEmpty("Observable is empty")
                .subscribe(System.out::println);

        System.out.println("------DefaultIfEmpty-2-----------");
        Observable.from(letters)
                .defaultIfEmpty("Observable is empty")
                .first()
                .subscribe(System.out::println);

        System.out.println("-------TakeWhile-----------");
        Observable.from(numbers)
                .takeWhile(i -> i < 5)
                .subscribe(System.out::println);
    }
}
