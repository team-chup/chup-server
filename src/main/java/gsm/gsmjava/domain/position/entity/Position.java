package gsm.gsmjava.domain.position.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_position")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Builder
@AllArgsConstructor
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
}
