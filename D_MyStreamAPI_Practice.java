import java.security.KeyStore.Entry;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.*;

@SuppressWarnings("unused")
public class D_MyStreamAPI_Practice {

        // Q1. Return a map of list or list of list by separating odd and even number
        // from a list

        public static void separateOddEven(List<Integer> myList) {

                Map<Boolean, List<Integer>> myMap = myList.stream()
                                .collect(Collectors.partitioningBy(item -> item % 2 == 0));

                var myAnsList = myList.stream().collect(
                                Collectors.collectingAndThen(Collectors.partitioningBy(item -> item % 2 == 0),
                                                item -> item.values()));

                System.out.println(myMap);
                System.out.println(myAnsList);

        }

        // Q2. Count frequency of a character in a String, return map of character and
        // count

        public static void countCharacterOfString(String str) {

                Map<Character, Long> ans = str.chars().mapToObj(item -> (char) item).collect(Collectors.groupingBy(
                                item -> item,
                                Collectors.counting()));
                System.out.println(ans);

        }

        // Q3. Find All Non Repeated character from a String

        public static void findAllNonRepeatedCharacterOfString(String str) {

                List<Character> ans = str.chars().mapToObj(item -> (char) item)
                                .filter(ch -> str.indexOf(ch) == str.lastIndexOf(ch))
                                .distinct().collect(Collectors.toList());

                System.out.println(ans);

        }
        // Q4. Find First Repeated character In A String

        public static void findFirstRepeatedCharacterOfString(String str) {

                Optional<Character> ans = str.chars().mapToObj(item -> (char) item)
                                .filter(ch -> str.indexOf(ch) != str.lastIndexOf(ch)).findFirst();
                System.out.println("First Repeated Character: " + ans.orElse(' '));

        }
        // Q5. Find First Non Repeated character In A String

        public static void findFirstNonRepeatedCharacterOfString(String str) {

                Optional<Character> ans = str.chars().mapToObj(item -> (char) item)
                                .filter(ch -> str.indexOf(ch) == str.lastIndexOf(ch)).findFirst();
                System.out.println("First Non Repeated Character: " + ans.orElse(' '));

        }

        // Q6. Reverse an Array

        public static void reverseAnArray(int arr[]) {

                int ans[] = IntStream.range(0, arr.length).map(i -> arr[arr.length - 1 - i]).toArray();
                System.out.println(Arrays.toString(ans));
        }
        // Q7. Remove duplicate from array

        public static void removeDuplicateFromList(List<Integer> myList) {

                var myAnsList = myList.stream().distinct().toList();
                System.out.println(myAnsList);

                var myAnsList1 = myList.stream().collect(Collectors.toSet());
                System.out.println(myAnsList);

                var dup_ans = myList.stream().filter(ele -> myList.indexOf(ele) != myList.lastIndexOf(ele)).distinct()
                                .toList();
                System.out.println(dup_ans);
        }

        // Q8. Count frequency of an element in a List
        public static void findFrequencyOfElement(List<Integer> myList) {

                var ans = myList.stream().collect(Collectors.groupingBy(item -> item, Collectors.counting()));
                System.out.println(ans);
        }

        // Q.9 Sort a List
        public static void sortList(List<Integer> myList) {

                var sortAns = myList.stream().sorted().toList();
                var sortAns1 = myList.stream().sorted((x, y) -> Integer.compare(x, y)).distinct().toList();
                var reverseSortAns = myList.stream().sorted((a, b) -> b - a).toList();
                System.out.println(sortAns);
                System.out.println(sortAns1);
                System.out.println(reverseSortAns);
        }

        // Q.10 Join a List of String Prefix [ suffix ] delimiter ,
        public static void joinListOfString(List<String> myList) {

                String ans = myList.stream().collect(Collectors.joining(",", "[", "]"));
                System.out.println(ans);
        }

        // Q.11 print the number which are multiple of 5
        public static void printMultipleOf5(List<Integer> myList) {

                myList.stream().filter(ele -> ele % 5 == 0).forEach(ele -> System.out.print(ele + " "));
                System.out.println();
        }

        // Q.12 Find Max & Min from a Given List

        public static void findMaxMin(List<Integer> myList) {

                var ans1 = myList.stream().reduce((a, b) -> Math.max(a, b));
                System.out.println(ans1.get());

                var ans2 = myList.stream().reduce((a, b) -> Math.min(a, b));
                System.out.println(ans2.get());

                var ans3 = myList.stream().max((a, b) -> a - b);
                System.out.println(ans3.get());

                var ans4 = myList.stream().min((a, b) -> a - b);
                System.out.println(ans4.get());
        }

        // Q.13 IntSummaryStatistics
        public static void myIntSummaryStatistics(List<Integer> myList) {

                IntSummaryStatistics ans = myList.stream().collect(Collectors.summarizingInt(ele -> (int) ele));
                System.out.println(ans.getCount());
                System.out.println(ans.getMax());
                System.out.println(ans.getMin());
                System.out.println(ans.getAverage());
                System.out.println(ans.getSum());

        }

        // Q.14 Merge two unsorted array and sort them, also remove duplicate
        public static void mergeTwoUnsortedArrayAndSort(int arr1[], int arr2[]) {

                int ans[] = IntStream.concat(IntStream.of(arr1), IntStream.of(arr2)).distinct().sorted().toArray();
                System.out.println(Arrays.toString(ans));

        }

        // Q.15 Merge two unsorted List and sort them, also remove duplicate
        public static void mergeTwoUnsortedListAndSort(List<Integer> myList1, List<Integer> myList2) {

                var ans = Stream.concat(myList1.stream(), myList2.stream()).sorted().distinct().toList();
                System.out.println(ans);

        }

        // Q.16 Get 3 max and 3 min number from a list
        public static void get3MinMax(List<Integer> myList) {

                System.out.println(myList);

                var ans = myList.stream().sorted().limit(3).toList();
                System.out.println("3 min Number: " + ans);

                var ans1 = myList.stream().sorted().skip(myList.size() - 3).toList();
                System.out.println("3 max Number: " + ans1);

        }
        // Q.17 check anagram

        public static void isAnagramEqualLength(String str1, String str2) {

                // assuming length is same
                char[] str1arr = str1.toCharArray();
                char[] str2arr = str2.toCharArray();

                Arrays.sort(str1arr);
                Arrays.sort(str2arr);

                var ans = IntStream.range(0, str1.length()).allMatch(i -> str1arr[i] == str2arr[i]);
                System.out.println("Is Anagram: " + ans);

        }

        public static void isAnagramNotEqualLength(String str1, String str2) {

                var ans1 = Stream.of(str1.split("")).sorted().collect(Collectors.joining(""));
                var ans2 = Stream.of(str2.split("")).sorted().collect(Collectors.joining(""));
                System.out.println("Is Anagram: " + ans1.equals(ans2));

        }

        // Q.18 Sum of all digit in a number

        public static void sumDigit(int num) {

                int ans = Stream.of(Integer.toString(num).split("")).map(ele -> Integer.parseInt(ele))
                                .reduce((a, b) -> a + b).get();
                System.out.println(ans);
                int ans1 = Stream.of(Integer.toString(num).split("")).mapToInt(ele -> Integer.parseInt(ele)).sum();
                System.out.println(ans1);

        }

        // Q.19 Second Largest element of a list
        public static void secondLargestElement(List<Integer> myList) {

                System.out.println(myList);

                var ans1 = myList.stream().sorted((a, b) -> b - a).skip(1).findFirst().get();
                System.out.println("2nd max Number: " + ans1);

        }

        // Q.20 Sort List of a String based on their length, decreasing order

        public static void sortlistOfStringBasedLength(List<String> myList) {

                System.out.println(myList);

                var ans1 = myList.stream().sorted((a, b) -> b.length() - a.length()).toList();
                System.out.println("2nd max Number: " + ans1);

        }

        // Q.21 print map of (str, int value of str) from a list of String
        public static void mapOfStrInt(List<String> myList) {

                Map<String, Integer> ans1 = myList.stream()
                                .collect(Collectors.toMap(ele -> ele, ele -> Integer.parseInt(ele)));
                System.out.println(ans1);

        }

        // Q22. Find All Repeated character from a String

        public static void findAllRepeatedCharacterOfString(String str) {

                List<Character> ans = str.chars().mapToObj(item -> (char) item)
                                .filter(ch -> str.indexOf(ch) != str.lastIndexOf(ch))
                                .distinct().collect(Collectors.toList());

                System.out.println(ans);

        }

        // Q.23 Find sum and average of an Integer array **

        public static void sumAndAverage(List<Integer> myList) {

                IntSummaryStatistics ans = myList.stream().collect(Collectors.summarizingInt(item -> (int) item));
                System.out.println("Sum: " + ans.getSum() + " Average: " + ans.getAverage());

                int ans1 = myList.stream().mapToInt(ele -> ele).sum();
                double ans2 = myList.stream().mapToInt(ele -> ele).average().getAsDouble();
                System.out.println("Sum: " + ans1 + " Average: " + ans2);

        }

        // Q.24 Find common element between two array

        public static void findCommonElement(List<Integer> myList1, List<Integer> myList2) {

                System.out.println(myList1);
                System.out.println(myList2);

                var ans = myList1.stream().filter(ele -> myList2.contains(ele)).distinct().toList();
                System.out.println(ans);

        }

        // Q.25 Reverse each word of stream

        public static void reverseEachWordOfString(String str) {

                String ans = Stream.of(str.split(" ")).map(ele -> new StringBuffer(ele).reverse().toString()).collect(
                                Collectors.joining(" "));
                System.out.println(ans);

        }

        // Q.26 Sum of n natural number

        public static void sumOfNNaturalNumber(int n) {

                int ans = IntStream.range(1, n + 1).sum();
                System.out.println(ans);

        }

        // Q.27 reverse an Integer array

        public static void reverseAnIntegerArray(List<Integer> myList1) {

                var ans = Stream.iterate(myList1.size() - 1, ele -> ele - 1).limit(myList1.size())
                                .map(ele -> myList1.get(ele)).toList();
                System.out.println(ans);

        }

        // Q.28 max repeated element in an array **

        public static void maxRepetedElement(List<Integer> myList1) {

                Map<Integer, Long> ans = myList1.stream()
                                .collect(Collectors.groupingBy(ele -> ele, Collectors.counting()));

                Map.Entry<Integer, Long> ans1 = ans.entrySet().stream().max(
                                (entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                                .get();

                System.out.println(ans1);

        }
        // Q.29 is string palindrome

        public static void isStringPalindrome(String str) {

                boolean ans = IntStream.range(0, str.length())
                                .allMatch(i -> str.charAt(i) == str.charAt(str.length() - i - 1));
                System.out.println(ans);

        }
        // Q.30 find all the string which start with a number from a list of string

        public static void findStartWithNumber(List<String> mystr) {

                var ans = mystr.stream().filter(ele->Character.isDigit(ele.charAt(0))).toList();
                System.out.println(ans);

        }
        // Q.31 generate fibonacci series **
        public static void generateFibonacciSeries(int n) {

                var ans=Stream.iterate(
                                new int[]{0,1}, 
                                ele->new int[]{ele[1],ele[0]+ele[1]}
                        )// first it will start the iteration with arr[0,1], later each time it will increment acc to fib logic
                        .limit(n)
                        .map(ele->ele[0])//we will take the ele of first idx
                        .toList();

                System.out.println(ans);

        }

        //Q.32 Find first n odd number

        public static void generateFirstNOdd(int n) {

                var ans=Stream.iterate(1, i->i+2).limit(n).toList();
                System.out.println(ans);

        }

        //Q.33 Find last ele of array

        public static void findLastEleOfList(List<Integer>myList) {

                int ans=myList.stream().skip(myList.size()-1).findFirst().get();
                System.out.println(ans);

        }
        //Q.34 Calculate Age

        public static void findAge(LocalDate dob) {

                Period age=Period.between(dob, LocalDate.now());
                System.out.println(age.getYears()+"y "+age.getMonths()+ "m "+age.getDays()+"d");


        }






        public static void main(String[] args) {

                // ****** Data ******

                List<Integer> myListNumber = List.of(1, 20, 3, 1, 79, 79, 6, 8, 10, 78, 20, 73);
                List<Integer> myListNumber1 = List.of(1, 2, 3, 5, 112, 74);
                List<String> myListNumberStr = List.of("1", "2", "33", "34", "5", "9");
                List<String> myListString = Arrays.asList("apple","5turn", "bats","12table", "cat", "banana");
                List<List<String>> listOfLists = Arrays.asList(Arrays.asList("apple", "banana"),
                                Arrays.asList("orange", "kiwi"));

                String str = "mzamnstzrBBBbbtsaq";
                String str1 = "asiff";
                int x[] = { 6, 1, 2, 3, 4, 5 };
                int y[] = { 4, 9, 8, 1 };

                // ***** Operation *****

                separateOddEven(myListNumber);
                countCharacterOfString(str);
                findAllRepeatedCharacterOfString(str);
                findAllNonRepeatedCharacterOfString(str);
                findFirstRepeatedCharacterOfString(str);
                findFirstNonRepeatedCharacterOfString(str);
                reverseAnArray(x);
                removeDuplicateFromList(myListNumber);
                findFrequencyOfElement(myListNumber);
                sortList(myListNumber);
                joinListOfString(myListString);
                printMultipleOf5(myListNumber);
                findMaxMin(myListNumber);
                myIntSummaryStatistics(myListNumber);
                mergeTwoUnsortedArrayAndSort(x, y);
                mergeTwoUnsortedListAndSort(myListNumber, myListNumber1);
                get3MinMax(myListNumber1);
                isAnagramEqualLength("str1", "s1rt");
                isAnagramNotEqualLength("str1", "s1rtz");
                sumDigit(12312);
                secondLargestElement(myListNumber1);
                sortlistOfStringBasedLength(myListString);
                mapOfStrInt(myListNumberStr);
                sumAndAverage(myListNumber1);
                findCommonElement(myListNumber, myListNumber1);
                reverseEachWordOfString("Sk Asif Iqbal");
                sumOfNNaturalNumber(10);
                reverseAnIntegerArray(myListNumber1);
                maxRepetedElement(myListNumber);
                isStringPalindrome("asifisa");
                findStartWithNumber(myListString);
                generateFibonacciSeries(5);
                generateFirstNOdd(10);
                findLastEleOfList(myListNumber1);
                findAge(LocalDate.of(1996, 8, 17));




        }
}
