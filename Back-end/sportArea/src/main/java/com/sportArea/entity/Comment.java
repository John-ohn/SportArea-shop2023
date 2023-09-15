package com.sportArea.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentId")
    private Long commentId;

    @Column(name = "message")
    private String message;

    @Enumerated(value = EnumType.STRING)
    private Note note;

    @Column(name="productRating")
    private Float productRating;

    @ManyToOne()
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne()
    @JoinColumn(name = "productId")
    private ProductUA product;

}
