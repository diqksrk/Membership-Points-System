package com.example.membershipsystem.application.barcode;

import com.example.membershipsystem.application.barcode.response.BarcodeIssueReturnDto;
import com.example.membershipsystem.domain.model.barcodes.entity.Barcode;
import com.example.membershipsystem.domain.model.barcodes.entity.BarcodeIssue;
import com.example.membershipsystem.domain.model.barcodes.repository.BarcodeIssueRepository;
import com.example.membershipsystem.domain.model.barcodes.repository.BarcodeRepository;
import com.example.membershipsystem.infrastructure.exception.message.BarcodeIssueExceptionMessage;
import com.example.membershipsystem.infrastructure.exception.status.NotFoundDataException;
import com.example.membershipsystem.presentation.barcodes.request.BarcodeIssueRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BarcodeIssueServiceImpl implements BarcodeIssueService {
    private final BarcodeRepository barcodeRepository;
    private final BarcodeIssueRepository barcodeIssueRepository;

    @Override
    @Transactional
    public BarcodeIssueReturnDto issueBarcode(BarcodeIssueRequest barcodeIssueRequest) {
        Optional<BarcodeIssue> barcodeIssue = barcodeIssueRepository.findByUserId(barcodeIssueRequest.getUserId());

        if (barcodeIssue.isPresent()) {
            BarcodeIssue existBarcodeIssue = barcodeIssue.get();
            return new BarcodeIssueReturnDto(existBarcodeIssue.getId(), barcodeIssueRequest.getUserId(), existBarcodeIssue.getBarcode().getBarcodeNo()
                    , existBarcodeIssue.getIssueDate(), existBarcodeIssue.getTerminationDate(), existBarcodeIssue.getStatusCode().getName());
        }

        // 이미 사용하려고 조회 한 바코드는 락을 걸어 동시 사용하지 못하도록 함.
        Optional<Barcode> barcodeNo = barcodeRepository.getBarcodeNoWithLock();
        if (!barcodeNo.isPresent())
            throw new NotFoundDataException(BarcodeIssueExceptionMessage.NotBarcodeFoundException.getMessage());

        Barcode newIssueBarcode = barcodeNo.get();
        newIssueBarcode.changeToisAlign();
        barcodeRepository.save(newIssueBarcode);

        BarcodeIssue barcodeIssueParam = new BarcodeIssue(barcodeIssueRequest.getUserId());
        barcodeIssueParam.setInitParam(newIssueBarcode);
        BarcodeIssue newBarcodeIssue = barcodeIssueRepository.save(barcodeIssueParam);

        return new BarcodeIssueReturnDto(newBarcodeIssue.getId(), barcodeIssueRequest.getUserId(), newBarcodeIssue.getBarcode().getBarcodeNo()
                , newBarcodeIssue.getIssueDate(), newBarcodeIssue.getTerminationDate(), newBarcodeIssue.getStatusCode().getName());
    }
}
