package com.devsuperior.dsmeta.services;

import java.time.LocalDate;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleReportDTO> findByDateRangeAndName(String dataInicial, String dataFinal, String nomeVendedor, Pageable pageable) {
		LocalDate maxDate = dataFinal.isEmpty() ? LocalDate.now() :  LocalDate.parse(dataFinal);
		LocalDate minDate = dataInicial.isEmpty() ? maxDate.minusYears(1l) : LocalDate.parse(dataInicial);

		return repository.searchByDateRangeAndName(minDate, maxDate, nomeVendedor, pageable);


	}
}
