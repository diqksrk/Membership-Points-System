package com.example.membershipsystem.domain.model.points.resource;

import com.example.membershipsystem.application.point.response.PointHistoryReturnDto;
import com.example.membershipsystem.presentation.points.PointHistoryController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import java.util.Arrays;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class PointHistoryResource<T> extends EntityModel {
    public PointHistoryResource(T t, Link... links) {
        super(t, Arrays.asList(links));
        add(linkTo(PointHistoryController.class).slash(((PointHistoryReturnDto) t).getPointHistoryId()).withSelfRel());
    }
}
