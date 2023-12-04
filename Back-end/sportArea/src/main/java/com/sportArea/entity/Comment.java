package com.sportArea.entity;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(name = "userName")
    private String userName;

    @Column(name = "message")
    private String message;

    @Enumerated(value = EnumType.STRING)
    private Note note;

    @Column(name = "productRating")
    private Float productRating;

    @Column(name = "dateTime")
    private LocalDateTime dateTime;

    @ManyToOne()
    @JoinColumn(name = "userId", nullable = true)
    private User user;

    @ManyToOne()
    @JoinColumn(name = "productId", nullable = true)
    private ProductUA product;

}
