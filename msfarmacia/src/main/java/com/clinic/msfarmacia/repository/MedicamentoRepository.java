package com.clinic.msfarmacia.repository;
import com.clinic.msfarmacia.model.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {
}
