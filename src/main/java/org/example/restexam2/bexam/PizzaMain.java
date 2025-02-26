package org.example.restexam2.bexam;

public class PizzaMain {

    public static void main(String[] args) {
        Pizza pizza = new Pizza("Small", true, true, true);
        System.out.println(pizza);

        Pizza pizza2 = Pizza.builder()
                .size("small")
                .cheese(true)
                .pepperoni(true)
                .build();  // 메서드 체이닝
        System.out.println(pizza2);



        Pizza pizza3 = Pizza.builder().size("small").build();

        System.out.println(pizza3);





    }


}
