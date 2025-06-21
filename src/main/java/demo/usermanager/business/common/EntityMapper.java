package demo.usermanager.business.common;

public interface EntityMapper<DTO, E> {
    DTO toDto(E entity);
    E toEntity(DTO dto);
}
