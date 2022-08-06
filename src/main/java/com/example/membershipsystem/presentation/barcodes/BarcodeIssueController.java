package com.example.membershipsystem.presentation.barcodes;

import com.example.membershipsystem.application.barcode.BarcodeIssueService;
import com.example.membershipsystem.application.barcode.response.BarcodeIssueReturnDto;
import com.example.membershipsystem.domain.model.barcodes.resource.BarcodeIssueResource;
import com.example.membershipsystem.infrastructure.exception.GlobalRequestException;
import com.example.membershipsystem.infrastructure.exception.status.NotFoundDataException;
import com.example.membershipsystem.presentation.barcodes.request.BarcodeIssueRequest;
import com.example.membershipsystem.domain.model.barcodes.validator.BarcodeIssueValidator;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/api/barcode/issue", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class BarcodeIssueController {
    private final BarcodeIssueService barcodeIssueService;
    private final BarcodeIssueValidator barcodeIssueValidator;

    @PostMapping
    @ApiOperation(value = "바코드 발급", notes = "바코드를 발급 합니다.")
    public ResponseEntity issueBarcode(@RequestBody @Valid BarcodeIssueRequest barcodeIssueRequest, Errors errors) {
        if (errors.hasErrors()) {
            return GlobalRequestException.badRequest(errors);
        }
        barcodeIssueValidator.barcodeIssueValidate(barcodeIssueRequest, errors);
        if (errors.hasErrors()) {
            return GlobalRequestException.badRequest(errors);
        }

        BarcodeIssueReturnDto issuedBarcode = barcodeIssueService.issueBarcode(barcodeIssueRequest);
        var selfLinkBuilder = linkTo(BarcodeIssueController.class).slash(issuedBarcode.getId());
        URI createdUri = selfLinkBuilder.toUri();
        BarcodeIssueResource barcodeIssueResource = new BarcodeIssueResource(issuedBarcode);
        barcodeIssueResource.add(Link.of("/swagger-ui.html#/barcode-controller/issueBarcodeUsingPOST").withRel("profile"));
        return ResponseEntity.created(createdUri).body(barcodeIssueResource);
    }

    @ExceptionHandler(NotFoundDataException.class)
    public ResponseEntity<Errors> notFoundException(NotFoundDataException e) {
        Errors errors = new BeanPropertyBindingResult(null, "");
        errors.reject(e.getStatus().toString(), e.getReason());
        return GlobalRequestException.badRequest(errors);
    }
}
