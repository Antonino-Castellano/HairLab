package com.generation.hairlab.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.hairlab.dto.CustomerAnalysisDto;
import com.generation.hairlab.service.CustomerAnalysisService;
import com.generation.hairlab.service.ServiceException;

import lombok.RequiredArgsConstructor;

/** Controller REST della scheda tecnica aggregata del cliente. */
@RestController
@RequestMapping("/hairlab/api/customer-analysis")
@RequiredArgsConstructor
public class CustomerAnalysisController {

    private final CustomerAnalysisService customerAnalysisService;

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<CustomerAnalysisDto> findByCustomerId(
            @PathVariable Integer customerId) throws ServiceException {
        return ResponseEntity.ok(
                customerAnalysisService.findByCustomerId(customerId));
    }
}
