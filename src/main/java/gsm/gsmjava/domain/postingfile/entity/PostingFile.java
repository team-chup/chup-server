package gsm.gsmjava.domain.postingfile.entity;

import gsm.gsmjava.domain.posting.entity.Posting;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_posting_file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Builder
@AllArgsConstructor
public class PostingFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posting_id", nullable = false)
    private Posting posting;

    private String name;

    private String url;
}
