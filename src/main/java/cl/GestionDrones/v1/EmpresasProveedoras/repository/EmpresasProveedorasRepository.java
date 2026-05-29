package cl.GestionDrones.v1.EmpresasProveedoras.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.GestionDrones.v1.EmpresasProveedoras.model.EmpresaProveedora;

@Repository
public interface EmpresasProveedorasRepository
        extends JpaRepository<EmpresaProveedora, Long> {

    List<EmpresaProveedora> findByEstado(String estado);

    List<EmpresaProveedora> findByRut(String rut);
}