package ru.puchinets.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.puchinets.model.dto.NewDto;
import ru.puchinets.model.entity.New;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NewMapper {
    New dtoToEntity(NewDto dto);
    NewDto entityToDto(New entity);
    default New update(New entity, NewDto dto) {
        if(dto==null) return entity;
        if(dto.getHeader()!=null && !dto.getHeader().isBlank()) entity.setHeader(dto.getHeader());
        if(dto.getText()!=null && !dto.getText().isBlank()) entity.setText(dto.getText());
        return entity;
    }
}
