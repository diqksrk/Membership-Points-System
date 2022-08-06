package com.example.membershipsystem.application.barcode;

import com.example.membershipsystem.application.barcode.response.BarcodeIssueReturnDto;
import com.example.membershipsystem.presentation.barcodes.request.BarcodeIssueRequest;

public interface BarcodeIssueService {
    BarcodeIssueReturnDto issueBarcode(BarcodeIssueRequest barcodeIssueRequest);
}
