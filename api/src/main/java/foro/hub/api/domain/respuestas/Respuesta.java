package foro.hub.api.domain.respuestas;

import foro.hub.api.domain.topico.Topico;
import foro.hub.api.domain.usuarios.Usuario;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.Date;

@Table(name = "respuestas")
@Entity(name = "Respuesta")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")

public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String mensaje;

    @ManyToOne
    @JoinColumn(name = "topico_id",nullable = false)
    private Topico topico;

    @Column(name = "fecha_creacion",nullable = false)
    private Date fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "autor_id",nullable = false)
    private Usuario autor;

    @Column(nullable = false)
    private boolean solucion;
}
