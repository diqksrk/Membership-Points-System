package com.example.membershipsystem.presentation.points;

import com.example.membershipsystem.application.point.PointService;
import com.example.membershipsystem.domain.model.points.validator.PointValidator;
import com.example.membershipsystem.infrastructure.exception.GlobalRequestException;
import com.example.membershipsystem.infrastructure.exception.status.DuplicaitonException;
import com.example.membershipsystem.presentation.points.request.PointEarningRequest;
import com.example.membershipsystem.presentation.points.request.PointUsingRequest;
import com.example.membershipsystem.application.point.response.PointReturnDto;
import com.example.membershipsystem.infrastructure.exception.status.UnauthorizedException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/point", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;
    private final PointValidator pointValidator;

    @PostMapping("/earn")
    @ApiOperation(value = "포인트 적립", notes = "포인트를 적립합니다.")
    public ResponseEntity earnPoint(@RequestBody @Valid PointEarningRequest pointEarningRequest, Errors errors) {
        if (errors.hasErrors()) {
            return GlobalRequestException.badRequest(errors);
        }
        pointValidator.earnPointvalidate(pointEarningRequest, errors);
        if (errors.hasErrors()) {
            return GlobalRequestException.badRequest(errors);
        }

        PointReturnDto newPoint = pointService.earnPoint(pointEarningRequest);

        EntityModel entityModel = EntityModel.of(newPoint);
        entityModel.add(linkTo(methodOn(PointController.class).earnPoint(pointEarningRequest, errors)).slash(newPoint.getId()).withSelfRel());
        entityModel.add(Link.of("/swagger-ui.html#/point-controller/earnPointUsingPOST").withRel("profile"));
        return ResponseEntity.ok(entityModel);
    }

    @PostMapping("/use")
    @ApiOperation(value = "포인트 사용", notes = "포인트를 사용합니다.")
    public ResponseEntity usePoint(@RequestBody @Valid PointUsingRequest pointUsingRequest, Errors errors) {
        if (errors.hasErrors()) {
            return GlobalRequestException.badRequest(errors);
        }
        pointValidator.usePointvalidate(pointUsingRequest, errors);
        if (errors.hasErrors()) {
            return GlobalRequestException.badRequest(errors);
        }

        PointReturnDto updatedPoint = pointService.usePoint(pointUsingRequest);

        EntityModel entityModel = EntityModel.of(updatedPoint);
        entityModel.add(linkTo(methodOn(PointController.class).usePoint(pointUsingRequest, errors)).slash(updatedPoint.getId()).withSelfRel());
        entityModel.add(Link.of("/swagger-ui.html#/point-controller/usePointUsingPOST").withRel("profile"));
        return ResponseEntity.ok(entityModel);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Errors> unAuthorizedException(UnauthorizedException e) {
        Errors errors = new BeanPropertyBindingResult(null, "");
        errors.reject(e.getStatus().toString(), e.getReason());
        return GlobalRequestException.badRequest(errors);
    }

    @ExceptionHandler(DuplicaitonException.class)
    public ResponseEntity<Errors> duplicationException(DuplicaitonException e) {
        Errors errors = new BeanPropertyBindingResult(null, "");
        errors.reject(e.getStatus().toString(), e.getReason());
        return GlobalRequestException.badRequest(errors);
    }
}
