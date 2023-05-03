import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class SetCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            String line = sc.nextLine();
            String[] parts = line.split("\\s*(\\+|\\*|-)\\s*");
            if (parts.length != 2) {
                System.out.println("Syntax error: " + line);
                continue;
            }
            
            Set<Integer> setA = parseSet(parts[0]);
            Set<Integer> setB = parseSet(parts[1]);
            
            Set<Integer> result = null;
            switch (line.charAt(parts[0].length() + 1)) {
                case '+':
                    result = union(setA, setB);
                    break;
                case '*':
                    result = intersection(setA, setB);
                    break;
                case '-':
                    result = difference(setA, setB);
                    break;
                default:
                    System.out.println("Syntax error: " + line);
                    continue;
            }
            
            System.out.println(result);
        }
    }
    
    private static Set<Integer> parseSet(String str) {
        Set<Integer> set = new TreeSet<>();
        String[] parts = str.replaceAll("\\[|\\]", "").split("\\s*,\\s*");
        for (String part : parts) {
            try {
                int value = Integer.parseInt(part);
                if (value >= 0) {
                    set.add(value);
                } else {
                    System.out.println("Negative integer: " + value);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer: " + part);
            }
        }
        return set;
    }
    
    private static Set<Integer> union(Set<Integer> setA, Set<Integer> setB) {
        Set<Integer> result = new TreeSet<>(setA);
        result.addAll(setB);
        return result;
    }
    
    private static Set<Integer> intersection(Set<Integer> setA, Set<Integer> setB) {
        Set<Integer> result = new TreeSet<>(setA);
        result.retainAll(setB);
        return result;
    }
    
    private static Set<Integer> difference(Set<Integer> setA, Set<Integer> setB) {
        Set<Integer> result = new TreeSet<>(setA);
        result.removeAll(setB);
        return result;
    }
}
