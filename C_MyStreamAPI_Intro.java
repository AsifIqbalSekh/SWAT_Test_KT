import java.util.*;
import java.util.stream.*;

public class C_MyStreamAPI_Intro {

    @SuppressWarnings("unused")
    public static void main(String[] args) {

        // 1. Different way of Stream Creation

        // A. From collection

        List<Integer> numbers1 = List.of(1, 2, 3, 4, 5);

        Stream<Integer> numberStream = numbers1.stream();

        // B. From Arrays

        Integer arr[] = new Integer[] { 1, 2, 3, 4, 5, null, 11 };
        Stream<Integer> arrStream = Arrays.stream(arr);

        // C. Using static method

        Stream<Integer> staticStream = Stream.of(1, 2, 3, 4, 5);
        Stream<String> staticStreamString = Stream.of("1", "2", "3");

        // D. Using Stream Builder

        Stream.Builder<Integer> streamBuilder = Stream.builder();
        streamBuilder.add(1);
        streamBuilder.add(2);
        streamBuilder.add(3);

        Stream<Integer> builderStream = streamBuilder.build();

        // E. Using Stream iterator

        Stream<Integer> streamIterate = Stream.iterate(0, item -> item + 1).limit(5);

        // 2. Intermediate operation

        // A. Filter => Filters the element

        /*
         * 
         * Stream<String> names=Stream.of("asif","aman","saiyul","shafaqat");
         * List<String>myNames=names.filter(item->
         * item.length()>4).collect(Collectors.toList());
         * System.out.println(myNames);
         * arrStream.filter(item-> item!=null).forEach(item->System.out.println(item));
         * System.out.println(Objects.isNull(arr));
         * 
         */

        // B. Map => Used to transforms each element

        /*
         * 
         * List.of("asif","test").stream().map(item-> item.toUpperCase())
         * .forEach(item->System.out.println(item));
         * 
         */

        // C. flatMap => used to iterate each elements of complex ds and flatten them

        /*
         * 
         * List<List<Integer>> myFlatList=List.of(
         * 
         * List.of(1,2,3,4,5),
         * List.of(6,7,8,9,10)
         * 
         * );
         * 
         * myFlatList.stream().flatMap(item->
         * item.stream().map(ele->ele+=1)
         * ).forEach(item->System.out.println(item));
         * 
         */

        // D. distinct => removes duplicate from stream

        /*
         * 
         * Stream<Integer> intStream=Stream.of(8,1,2,1,3,4,3,5,2);
         * 
         * intStream.distinct().forEach(item->System.out.println(item));
         * 
         */

        // E. sorted => sorts the element

        /*
         * 
         * List<Integer> myNumber = Stream.iterate(100, item -> item -
         * 1).limit(5).toList();
         * System.out.println(myNumber);
         * 
         * ArrayList<Integer> myArr = new ArrayList(myNumber);
         * myArr.add(121);
         * myArr.add(3);
         * myArr.add(88);
         * myArr.add(95);
         * 
         * myArr.stream().sorted().forEach(item -> System.out.println(item));
         * System.out.println("Descending Order--");
         * myArr.stream().sorted((item1, item2) -> item1 > item2 ? -1 : 1).forEach(item
         * -> System.out.println(item));
         * 
         */

        // F. peek => help to show the intermediate result

        /*
         * 
         * List<Integer> myList = Stream.iterate(1, item -> item + 2).limit(5).toList();
         * Optional<List<Integer>> optList = Optional.of(myList);
         * optList.ifPresentOrElse(
         * value -> System.out.println("Value is present: " + value),
         * () -> System.out.println("Value is absent"));
         * 
         * // myList=null;
         * Optional<List<Integer>> optListnull = Optional.ofNullable(myList);
         * optListnull.orElseGet(() -> {
         * System.out.println("List iS Null");
         * return null;
         * });
         * optListnull.ifPresent(item -> System.out.println("List is present: " +
         * item));
         * 
         * myList.stream().peek(item -> System.out.println("from peek: " +
         * item)).filter(item -> item > 5)
         * .forEach(item -> System.out.println("from for each: " + item));
         * 
         */

        // G. limit => trucate the stream

        /*
         * 
         * List<Integer>myList=List.of(1,2,3,4,5);
         * 
         * myList.stream().limit(3).forEach(item->System.out.println(item));
         * 
         */

        // H. skip => to skip some elements from stream

        /*
         * 
         * long n= myList.stream().skip(3).count();
         * System.out.println("Total Element left: "+n);
         * 
         */

        // mapToInt =>Helps to work with premitive data type int

        /*
         * 
         * List<String> numsArr = List.of("1", "2", "3", "4", "5");
         * IntStream intStream = numsArr.stream().mapToInt(item ->
         * Integer.parseInt(item));
         * 
         * int intArr[] = intStream.toArray();
         * for (int item : intArr) {
         * System.out.println(item + 1);
         * }
         * 
         * int numbsArr[] = { 1, 2, 3, 6 };
         * System.out.println("Item::::");
         * IntStream myIntStr = Arrays.stream(numbsArr);
         * myIntStr.filter(item -> item >= 3).forEach(item -> System.out.println(item));
         * 
         */

        // Intermediate operations are lazy, Also it runs sequentially does'nt wait for
        // one ops
        // to be completed until required(Sorted)example

        /*
         * 
         * List<Integer> myList = List.of(1, 5, 8, 7);
         * Stream<Integer> myStream = myList.stream().filter(item -> item > 3).peek(item
         * -> System.out.println(item));
         * myStream.count(); //If I comment this line, nothing will print
         * 
         */

        // 3. Terminal Operation

        // A. collect & Collectors

        /*
         * List<String> numsArr = List.of("1", "2", "3", "4", "","5");
         * 
         * Map<String,Integer>mymap= numsArr.stream().filter(item->item!="")
         * .collect(Collectors.toMap(item->item, item-> Integer.parseInt(item)));
         * System.out.println(mymap);
         * 
         * List<String> MyListString = Arrays.asList("apple", "bat", "cat", "banana");
         * Map<String,Integer>mymap1=MyListString.stream().map(item->item.toUpperCase())
         * .collect(Collectors.toMap(item->item, item->item.length()));
         * System.out.println(mymap1);
         * 
         * 
         * 
         * String myString="sk asif iqbal";
         * Map<Character,Integer>myMap=new HashMap<>();
         * 
         * IntStream myStrStream=myString.chars();
         * 
         * myStrStream.mapToObj(item->(char)item).filter(item->item!=' ').forEach(key->
         * myMap.put(key, myMap.getOrDefault(key, 0)+1)
         * );
         * 
         * Map<Object,Long>myans=myString.chars().mapToObj(item->(char)item).filter(item
         * ->item!=' ').collect(
         * Collectors.groupingBy(key->key,Collectors.counting())
         * );
         * 
         * System.out.println(myans);
         * 
         */

        // B. toArray => Collects the element of stream in an Array

        /*
         * 
         * List<Integer> myList = List.of(1, -2, 3, 9, -3);
         * int myArr[] = myList.stream().mapToInt(item -> item).filter(item -> item >
         * 0).toArray();
         * for (int ele : myArr) {
         * System.out.println(ele);
         * }
         * Integer myList1[] = myList.stream().map(item -> item * 2).toArray(n -> new
         * Integer[n]);
         * for (int ele : myList1) {
         * System.out.println(ele);
         * }
         */

        // C. reduce => does reduction on a element, perform aggregative ops

        /*
         * 
         * List<Integer> myList=List.of(1,2,3,4,5);
         * Optional<Integer> ans=myList.stream().map(item->item*2).reduce((a,b)->a+b);
         * ans.ifPresent(item->System.out.println(item));
         * 
         */

        // min & max & count

        /*
         * 
         * List<Integer>myNumber=List.of(1,2,3,4,5,6,7,8);
         * Integer ans=myNumber.stream().max((a,b)->a-b).get();
         * Integer ans1=myNumber.stream().min((a,b)->a-b).get();
         * Long ans3=myNumber.stream().filter(ele->ele>=6).count();
         * System.out.println(ans+" "+ans1+" "+ans3);
         * 
         */

        // anyMatch, allMatch, noneMatch & findFirst, findAny
        /*
         * 
         * List<Integer>myNumber=List.of(1,2,3,4,5,6,7,8);
         * 
         * boolean ans=myNumber.stream().anyMatch(item->item>3);
         * boolean ans1=myNumber.stream().allMatch(item->item>3);
         * boolean ans2=myNumber.stream().noneMatch(item->item>10);
         * 
         * System.out.println("anyMatch: "+ans+" allMatch: "+ans1+" noneMatch: "+ans2);
         * 
         * 
         * 
         * Integer ans3=myNumber.stream().filter(ele->ele>3).findFirst().get();
         * Integer ans4=myNumber.stream().filter(ele->ele>3).findAny().get();
         * Integer ans5=myNumber.stream().filter(ele->ele>3).sorted((a,b)->b-a).skip(1).
         * findFirst().get();
         * 
         * System.out.println("findFirst: "+ans3+" findAny: "+ans4+" sort 2nd max: "
         * +ans5);
         * 
         */

        //Parallal Stream take less time

        // List<Integer> numbers2 = IntStream.rangeClosed(1, 10000).boxed().collect(Collectors.toList());


        List<Integer> numbers=Stream.iterate(1, item->item+1).limit(10000).toList();
        long sequentialStart = System.currentTimeMillis();
        List<Long> sequentialResult = numbers.stream()
                .map(item -> factorial(item))
                .collect(Collectors.toList());
        System.out.println("Sequential Time: " + (System.currentTimeMillis() - sequentialStart) + " ms");

        long parallelStart = System.currentTimeMillis();
        List<Long> parallelResult = numbers.parallelStream()
                .map(item -> factorial(item))
                .collect(Collectors.toList());
        System.out.println("Parallel Time: " + (System.currentTimeMillis() - parallelStart) + " ms");

    }

    private static long factorial(int n) {
        return (n == 0 || n == 1) ? 1 : n * factorial(n - 1);
    }
}
