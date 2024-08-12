package com.shair13.data_service.mapper;

import com.shair13.data_service.dto.PagedMovie;
import com.shair13.data_service.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieMapper INSTANCE = Mappers.getMapper( MovieMapper.class );

    @Mapping(source = "content", target = "movies")
    @Mapping(source = "number", target = "pageNumber")
    @Mapping(source = "size", target = "pageSize")
    PagedMovie pageToPagedMovie(Page<Movie> page);
}