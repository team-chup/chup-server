package gsm.gsmjava.domain.posting.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmploymentType {
    FULL_TIME("정규직"),
    CONTRACT("계약직"),
    INTERN("인턴"),
    MILITARY_EXCEPTION("병역특례");

    private final String name;
}
