package tdd.greeting;

import java.util.*;
import java.util.stream.*;

class Greeter {

    public static String greet(String name) {
        if (name == null) return "Hello, my friend.";
        if (name.equals(name.toUpperCase())) return String.format("HELLO %s!", name);
        return String.format("Hello, %s.", name);
    }

    public static String greet(String[] nameArray) {
        List<String> list = splitArray(nameArray);
        String res = "";
        List<String> namesUpper = list.stream().filter(n -> n.equals(n.toUpperCase())).collect(Collectors.toList());
        List<String> namesLower = list.stream().filter(n -> !n.equals(n.toUpperCase())).collect(Collectors.toList());
        if (!namesLower.isEmpty()) {
            if (namesLower.size() == 1) res += greet(namesLower.get(0));
            else if (namesLower.size() == 2) res += greet(String.join(" and ", namesLower));
            else {
                namesLower.add("and " + namesLower.remove(namesLower.size() - 1));
                res += greet(String.join(", ", namesLower));
            }
        }
        if (!namesUpper.isEmpty()) {
            res += res.isEmpty() ? "" : " AND ";
            if (namesUpper.size() == 1) res += greet(namesUpper.get(0));
            else if (namesUpper.size() == 2) res += greet(String.join(" AND ", namesUpper));
            else {
                namesUpper.add("AND " + namesUpper.remove(namesUpper.size() - 1));
                res += greet(String.join(", ", namesUpper));
            }
        }
        return res;
    }

    private static List<String> splitArray(String[] arr) {
        List<String> list = new ArrayList<>();
        Arrays.stream(arr).forEach(e -> {
            if (e != null) {
                if (!e.matches("^\".+\"$")) list.addAll(Arrays.asList(e.split(", ")));
                else list.add(e.replaceAll("\"", ""));
            }
        });
        return list;
    }
}
