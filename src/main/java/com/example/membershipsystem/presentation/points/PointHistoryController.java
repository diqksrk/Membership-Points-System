package com.example.membershipsystem.presentation.points;

import com.example.membershipsystem.application.point.response.PointHistoryReturnDto;
import com.example.membershipsystem.domain.model.points.resource.PointHistoryResource;
import com.example.membershipsystem.domain.model.points.validator.PointHistoryValidator;
import com.example.membershipsystem.infrastructure.exception.GlobalRequestException;
import com.example.membershipsystem.presentation.points.request.PointSearchingReqeust;
import com.example.membershipsystem.application.point.PointHistoryService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/point/history", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PointHistoryController {
    private final PointHistoryService pointHistoryService;
    private final PointHistoryValidator pointHistoryValidator;

    @GetMapping
    @ApiOperation(value = "포인트 이력 조회", notes = "포인트 이력을 조회합니다.")
    public ResponseEntity searchPointHistory(PointSearchingReqeust pointSearchingReqeust, Pageable pageable, Errors errors, PagedResourcesAssembler assembler) {
        pointHistoryValidator.searchPointHistoryValidate(pointSearchingReqeust, errors);
        if (errors.hasErrors()) {
            return GlobalRequestException.badRequest(errors);
        }

        Page<PointHistoryReturnDto> pointHistoryDtos = pointHistoryService.searchPointHistory(pointSearchingReqeust, pageable);
        PagedModel<PointHistoryResource> pointHistoryResources = assembler.toModel(pointHistoryDtos, e -> new PointHistoryResource(e));
        pointHistoryResources.add(Link.of("/swagger-ui.html#/point-controller/searchPointHistoryUsingGET").withRel("profile"));
        return ResponseEntity.ok(pointHistoryResources);
    }
}
