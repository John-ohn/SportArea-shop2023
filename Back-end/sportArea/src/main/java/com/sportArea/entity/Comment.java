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

    @ManyToOne()
    @JoinColumn(name = "userId")
    private User userId;

    @ManyToOne()
    @JoinColumn(name = "productId")
    private ProductUA productId;

}
