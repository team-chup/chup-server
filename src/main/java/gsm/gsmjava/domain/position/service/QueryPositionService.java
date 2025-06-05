package gsm.gsmjava.domain.position.service;

import gsm.gsmjava.domain.position.entity.Position;
import gsm.gsmjava.domain.position.repository.PositionRepository;
import gsm.gsmjava.domain.position.service.dto.res.QueryPositionResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryPositionService {

    private final PositionRepository positionRepository;

    @Transactional(readOnly = true)
    public QueryPositionResDto queryAll() {
        List<Position> positions = positionRepository.findAll();
        return QueryPositionResDto.fromPositions(positions);
    }

}
