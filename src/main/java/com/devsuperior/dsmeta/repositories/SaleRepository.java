package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("select new com.devsuperior.dsmeta.dto.SaleReportDTO (sale.id, sale.date, sale.amount, sale.seller.name)" +
            " from Sale sale" +
            " where sale.date between :dataInicial and :dataFinal" +
            " and upper(sale.seller.name) like upper(concat('%',:nomeVendedor,'%'))" )
    Page<SaleReportDTO> searchByDateRangeAndName(LocalDate dataInicial, LocalDate dataFinal, String nomeVendedor, Pageable pageable);

    @Query("select new com.devsuperior.dsmeta.dto.SummaryDTO(sale.seller.name, sum (sale.amount))" +
            " from Sale sale" +
            " where sale.date between :minDate and :maxDate" +
            " group by sale.seller.name")
    List<SummaryDTO> summaryByDateRange(LocalDate minDate, LocalDate maxDate);
}
