package local.projects.betting;

public class Test {
  public static void main(String[] args) {
    String a = "12:30 Empoli - Napoli";
    System.out.println(a.substring(6, a.indexOf("-")-1).trim());
    System.out.println(a.substring(a.indexOf("-")+1,a.length()).trim());

  }
  
  
}
