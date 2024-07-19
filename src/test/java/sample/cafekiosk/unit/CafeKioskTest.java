package sample.cafekiosk.unit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.unit.beverage.Americano;
import sample.cafekiosk.unit.beverage.Latte;
import sample.cafekiosk.unit.order.Order;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CafeKioskTest {
    @Test
    void add_manual_add(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());


        System.out.println(">>> 담긴 음료 수 : " + cafeKiosk.getBeverages().size());
        System.out.println(">>> 담긴 음료 : " + cafeKiosk.getBeverages().get(0).getName());
    }

    @Test
    void add(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        Assertions.assertThat(cafeKiosk.getBeverages().size()).isEqualTo(1);
        Assertions.assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }


    //경계값 분석 - 해피케이스
    @Test
    void addSeveralBeverages(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano, 2);

        Assertions.assertThat(cafeKiosk.getBeverages().get(0)).isEqualTo(americano);
        Assertions.assertThat(cafeKiosk.getBeverages().get(1)).isEqualTo(americano);
    }

    //경계값 분석 - 예외케이스
    @Test
    void addZeroBeverages(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        Assertions.assertThatThrownBy(()-> cafeKiosk.add(americano,0))
                        .isInstanceOf(IllegalStateException.class)
                        .hasMessage("음료는 1잔 이상 주문할 수 있습니다.");

    }


    @Test
    void remove(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);
        Assertions.assertThat(cafeKiosk.getBeverages().size()).isEqualTo(1);

        cafeKiosk.remove(americano);
        Assertions.assertThat(cafeKiosk.getBeverages()).isEmpty();
    }

    @Test
    void clear(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();

        cafeKiosk.add(americano);
        cafeKiosk.add(latte);

        Assertions.assertThat(cafeKiosk.getBeverages().size()).isEqualTo(2);

        cafeKiosk.clear();
        Assertions.assertThat(cafeKiosk.getBeverages().size()).isEqualTo(0);


    }

    @Test
    void createOrder(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);

        Order order = cafeKiosk.createOrder();
        Assertions.assertThat(order.getBeverages().size()).isEqualTo(1);
        Assertions.assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");

    }

    // 가게 운영시간 해피 케이스
    @Test
    void createOrderWithCurrentTime(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);


        Order order = cafeKiosk.createOrder(LocalDateTime.of(2023,1,17,10,0));
        Assertions.assertThat(order.getBeverages().size()).isEqualTo(1);
        Assertions.assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");

    }

    // 가게 운영시간 예외 케이스
    @Test
    void createOrderOutsideOpenTime(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);

        Assertions.assertThatThrownBy(() -> cafeKiosk.createOrder(LocalDateTime.of(2023,1,17,9,59)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("주문 시간이 아닙니다. 관리자에게 문의하세요.");

        Order order = cafeKiosk.createOrder(LocalDateTime.of(2023,1,17,10,0));
        Assertions.assertThat(order.getBeverages().size()).isEqualTo(1);
        Assertions.assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");

    }

}