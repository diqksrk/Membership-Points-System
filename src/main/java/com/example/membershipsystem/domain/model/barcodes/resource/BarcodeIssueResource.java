package com.example.membershipsystem.domain.model.barcodes.resource;

import com.example.membershipsystem.application.barcode.response.BarcodeIssueReturnDto;
import com.example.membershipsystem.presentation.barcodes.BarcodeIssueController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import java.util.Arrays;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class BarcodeIssueResource extends EntityModel<BarcodeIssueReturnDto> {
    public BarcodeIssueResource(BarcodeIssueReturnDto barcodeIssueDto, Link... links) {
        super(barcodeIssueDto, Arrays.asList(links));
        add(linkTo(BarcodeIssueController.class).slash(barcodeIssueDto.getId()).withSelfRel());
    }
}
