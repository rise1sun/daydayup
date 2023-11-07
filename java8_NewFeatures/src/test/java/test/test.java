package test;

import org.example.lambda.Employee;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author jiangfeng
 * @date 2023/11/6
 */
public class test {

    @Test
    public void test1() {

        List<Employee> emps = Arrays.asList(
                new Employee(101, "Z3", 19, 9999.99),
                new Employee(102, "L4", 20, 7777.77),
                new Employee(103, "W5", 35, 6666.66),
                new Employee(104, "Tom", 44, 1111.11),
                new Employee(105, "Jerry", 60, 4444.44)
        );

        Collections.sort(emps, (e1, e2) -> {
            if (e1.getAge() == e2.getAge()) {
                return e1.getName().compareTo(e2.getName());
            } else {
                return Integer.compare(e1.getAge(), e2.getAge());

            }
        });

        for (Employee emp : emps) {
            System.out.println(emp);
        }
    }

    @Test
    public void test02() {
        List<Integer> list = new ArrayList<>();
        List<Integer> integers = Arrays.asList(1, 2, 3);
        list.addAll(integers);
        //Supplier<T>
        Supplier<Integer> supplier = () -> (int) Math.random() * 10;
        list.add(supplier.get());
        System.out.println(supplier);
        for (Integer integer : list) {
            System.out.println(integer);
        }
    }

    @Test
    public void test03() {
        //Function<T, R>
        String oldStr = "abc123456xyz";
        Function<String, String> function = (s) -> s.substring(1, s.length() - 1);
        //test
        System.out.println(function.apply(oldStr));
    }

    @Test
    public void test04() {
        List<Employee> emps = Arrays.asList(
                new Employee(101, "Z3", 19, 9999.99),
                new Employee(102, "L4", 20, 7777.77),
                new Employee(103, "W5", 35, 6666.66),
                new Employee(104, "Tom", 44, 1111.11),
                new Employee(105, "Jerry", 60, 4444.44)
        );
       /* List<Employee> collect = emps.stream()
                .filter(employee -> employee.getAge() > 35)
                .limit(3)
                .collect(Collectors.toList());
        for (Employee emp : collect) {
            System.out.println(emp);
        }*/
        emps.stream()
                .filter(employee -> employee.getAge() > 35)
                .limit(1)
                .forEach(System.out::println);


    }

    @Test
    public void test05(){
        List<String> list = Arrays.asList("a", "b", "c");
        list.stream()
                .map((str) -> str.toUpperCase())
                .forEach(System.out::println);
    }

    public enum Status {
        FREE, BUSY, VOCATION;
    }

    @Test
    public void test06(){
        List<Status> list = Arrays.asList(Status.FREE, Status.BUSY, Status.VOCATION);

        boolean flag1 = list.stream()
                .allMatch((s) -> s.equals(Status.BUSY));
        System.out.println(flag1);

        boolean flag2 = list.stream()
                .anyMatch((s) -> s.equals(Status.BUSY));
        System.out.println(flag2);

        boolean flag3 = list.stream()
                .noneMatch((s) -> s.equals(Status.BUSY));
        System.out.println(flag3);

        Optional<Status> stream = list.stream().findFirst();
        stream.orElse(Status.BUSY);

        // 避免空指针异常
        Optional<Status> op1 = list.stream()
                .findFirst();
        // 如果Optional为空 找一个替代的对象
        Status s1 = op1.orElse(Status.BUSY);
        System.out.println(s1);

        Optional<Status> op2 = list.stream()
                .findAny();
        System.out.println(op2);

        long count = list.stream()
                .count();
        System.out.println(count);
    }





}
