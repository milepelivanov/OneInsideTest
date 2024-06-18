public class Main {


    public static void main(String[] args) {
        String test1 = "QWRTYPSDFGHJKLZXCVBNM"; //output 21
        String test2 = "AEIOUAA"; //output 0
        String test3 = "ASDQWEDGSXZASDASD"; //output 13
        String test4 = ""; //output 0
        System.out.println(countConsonants(test1.toLowerCase(), 0));
        System.out.println(countConsonants(test2.toLowerCase(), 0));
        System.out.println(countConsonants(test3.toLowerCase(), 0));
        System.out.println(countConsonants(test4.toLowerCase(), 0));
    }

    public static Integer countConsonants(String s, Integer count) {
        if (s.length() == 0) {
            return count;
        } else {
            char c = s.charAt(0);
            if (c != 'a' && c != 'e' && c != 'i' && c != 'o' && c != 'u') {
                count++;
            }
            return countConsonants(s.substring(1), count);
        }
    }
}



