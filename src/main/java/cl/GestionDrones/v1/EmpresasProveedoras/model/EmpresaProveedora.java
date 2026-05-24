package cl.GestionDrones.v1.EmpresasProveedoras.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "empresas_proveedoras")

public class EmpresaProveedora {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "rut", nullable = false, unique = true, length = 15)
    private String rut; 

    @Column(name = "razon_social", nullable = false, length = 150)
    private String razonSocial;

    @Column(name = "cantidad_aeronaves", nullable = false)
    private Integer cantidadAeronaves;

    @Column(name = "cantidad_pilotos", nullable = false)
    private Integer cantidadPilotos;

    @Column(name = "contacto_email", nullable = false, length = 100)
    private String contactoEmail;

    @Column(name = "estado", nullable = false, length = 30)
    private String estado;

    public Proveedor() {
    }

    // Constructor completo
    public Proveedor(Long id, String rut, String razonSocial, Integer cantidadAeronaves, Integer cantidadPilotos, String contactoEmail, String estado) {
        this.id = id;
        this.rut = rut;
        this.razonSocial = razonSocial;
        this.cantidadAeronaves = cantidadAeronaves;
        this.cantidadPilotos = cantidadPilotos;
        this.contactoEmail = contactoEmail;
        this.estado = estado;
    }

    // Getters y Setters Manuales
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public Integer getCantidadAeronaves() {
        return cantidadAeronaves;
    }

    public void setCantidadAeronaves(Integer cantidadAeronaves) {
        this.cantidadAeronaves = cantidadAeronaves;
    }

    public Integer getCantidadPilotos() {
        return cantidadPilotos;
    }

    public void setCantidadPilotos(Integer cantidadPilotos) {
        this.cantidadPilotos = cantidadPilotos;
    }

    public String getContactoEmail() {
        return contactoEmail;
    }

    public void setContactoEmail(String contactoEmail) {
        this.contactoEmail = contactoEmail;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
