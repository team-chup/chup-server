package gsm.gsmjava.domain.position.service;

import gsm.gsmjava.domain.position.entity.Position;
import gsm.gsmjava.domain.position.repository.PositionRepository;
import gsm.gsmjava.domain.position.service.dto.req.CreatePositionReqDto;
import gsm.gsmjava.global.error.ExpectedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreatePositionService {

    private final PositionRepository positionRepository;

    @Transactional
    public void create(CreatePositionReqDto reqDto) {
        String name = reqDto.getName();

        boolean isDuplicate = positionRepository.existsByName(name);
        if (isDuplicate) {
            throw new ExpectedException("이미 동일한 이름의 포지션이 존재합니다.", HttpStatus.BAD_REQUEST);
        }

        Position newPosition = Position.of(name);
        positionRepository.save(newPosition);
    }

}
