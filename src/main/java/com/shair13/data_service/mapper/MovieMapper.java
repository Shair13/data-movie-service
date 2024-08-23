package com.shair13.data_service.mapper;

import com.shair13.data_service.dao.PageDetails;
import com.shair13.data_service.dto.PagedMovie;
import com.shair13.data_service.dto.ReadMovieDto;
import com.shair13.data_service.dto.WriteMovieDto;
import com.shair13.data_service.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    PagedMovie toPagedMovie(List<Movie> movies, PageDetails pageDetails);

    @Mapping(target = "id", ignore = true)
    Movie toDomain(WriteMovieDto writeMovieDto);

    ReadMovieDto toReadDto(Movie movie);
}