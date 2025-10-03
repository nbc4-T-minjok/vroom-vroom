package com.sparta.vroomvroom.domain.cart.model.entity;

import com.sparta.vroomvroom.domain.menu.model.entity.Menu;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "cart_menus")
@Getter
@Setter
@NoArgsConstructor
public class CartMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "cart_menu_id")
    private UUID cartMenuId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(name = "menu_amount", nullable = false)
    private int menuAmount;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    //    @CreatedBy
    @Column(name = "created_by",updatable = false, nullable = false, length = 20)
    private String createdBy;

    public void create(String userName){
        this.createdBy = userName;
    }
}
