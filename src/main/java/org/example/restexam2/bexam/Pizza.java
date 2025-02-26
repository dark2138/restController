package org.example.restexam2.bexam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Builder
public class Pizza {
    private String size;  // (Small, Medium, Large)

    @Builder.Default
    private boolean cheese = true;

    @Builder.Default
    private boolean pepperoni = true;

    @Builder.Default
    private boolean mushrooms= true;
}


/*
// 위 과정 롬복을 풀어서 구현해놓은 코드
@AllArgsConstructor
@ToString
public class Pizza {
    private String size;  // (Small, Medium, Large)
    private boolean cheese;
    private boolean pepperoni;
    private boolean mushrooms;


    public static class Builder {
        private String size;
        private boolean cheese = true;
        private boolean pepperoni = true;
        private boolean mushrooms = true;

        public Builder(){}

        public Builder size(String size){
            this.size = size;
            return this;
        }
        public Builder cheese(boolean cheese){
            this.cheese = cheese;
            return this;
        }
        public Builder pepperoni(boolean pepperoni){
            this.pepperoni = pepperoni;
            return this;
        }
        public Builder mushrooms(boolean mushrooms){
            this.mushrooms = mushrooms;
            return this;
        }
        public Pizza build(){
            return new Pizza(this);
        }
    }

    private Pizza(Builder builder){
        this.size = builder.size;
        this.cheese = builder.cheese;
        this.pepperoni = builder.pepperoni;
        this.mushrooms = builder.mushrooms;
    }

    public static Builder builder(){
        return new Builder();
    }


}
*/