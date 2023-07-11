package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/report")
	public ResponseEntity<Page<SaleReportDTO>> getReport(@RequestParam(value = "minDate", defaultValue = "")String dataInicial,
														 @RequestParam(value = "maxDate", defaultValue = "") String dataFinal,
														 @RequestParam(value="name", defaultValue = "") String nomeVendedor,
														 Pageable pageable) {

		Page<SaleReportDTO> dto = service.findByDateRangeAndName(dataInicial, dataFinal, nomeVendedor, pageable);
		return ResponseEntity.ok(dto);

	}

	@GetMapping(value = "/summary")
	public ResponseEntity<List<SummaryDTO>> getSummary(@RequestParam(value = "minDate", defaultValue = "")String dataInicial,
												 @RequestParam(value = "maxDate", defaultValue = "") String dataFinal) {

		List<SummaryDTO> dto = service.summaryByDateRange(dataInicial, dataFinal);
		return ResponseEntity.ok(dto);

	}
}
