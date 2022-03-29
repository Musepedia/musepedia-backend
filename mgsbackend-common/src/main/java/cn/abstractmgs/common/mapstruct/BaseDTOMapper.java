package cn.abstractmgs.common.mapstruct;

import java.util.List;

public interface BaseDTOMapper<D, E> {

    E toEntity(D dto);

    D toDto(E entity);

    List<E> toEntity(List<D> dtoList);

    List<D> toDto(List<E> entityList);
}
