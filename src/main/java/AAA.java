import se.alten.schoolproject.entity.Student;

import java.util.stream.Stream;

public class AAA {

  public static void main(String[] args) {
    Student s = new Student();
    s.setForename("");
    s.setLastname("kk");
    s.setEmail("");


    boolean checkForEmptyVariables =
            Stream.of(s.getForename(),
                    s.getLastname(),
                    s.getEmail())
                    .allMatch(String::isBlank);

    System.out.println(checkForEmptyVariables);
  }
}
