package gsm.gsmjava.domain.position.service.dto.res;

import gsm.gsmjava.domain.position.entity.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class QueryPositionResDto {

    private List<PositionDto> positions;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class PositionDto {
        private Long id;
        private String name;
    }

    public static QueryPositionResDto fromPositions(List<Position> positions) {
        List<PositionDto> positionDto = positions.stream().map(position ->
                PositionDto.builder()
                        .id(position.getId())
                        .name(position.getName())
                        .build()).toList();

        return new QueryPositionResDto(positionDto);
    }
}
