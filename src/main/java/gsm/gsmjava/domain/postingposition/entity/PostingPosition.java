package gsm.gsmjava.domain.postingposition.entity;

import gsm.gsmjava.domain.position.entity.Position;
import gsm.gsmjava.domain.posting.entity.Posting;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tbl_posting_position")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Builder
@AllArgsConstructor
public class PostingPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "posting_id", nullable = false)
    private List<Posting> postings = List.of();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", nullable = false)
    private List<Position> positions = List.of();
}
