package foro.hub.api.domain.topico;


import foro.hub.api.domain.usuarios.Usuario;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String mensaje;

    @Column(name = "fecha_creacion",nullable = false)
    private Date fechaCreacion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TopicStatus status;

    @ManyToOne
    @JoinColumn(name = "autor_id",nullable = false)
    private Usuario autor;

    @Column(nullable = false)
    private String curso;


    public Topico(DTORegistroTopico dtoRegistroTopico) {
        this.titulo = dtoRegistroTopico.titulo();
        this.mensaje = dtoRegistroTopico.mensaje();
        this.fechaCreacion = new Date();
        this.status = dtoRegistroTopico.status();
        this.autor = dtoRegistroTopico.autor();
        this.curso = dtoRegistroTopico.curso();
    }

    public void actualizarDatos(DTOActualizarTopico dtoActualizarTopico){

        if(dtoActualizarTopico.mensaje()!=null){
            this.mensaje = dtoActualizarTopico.mensaje();
        }

        if(dtoActualizarTopico.titulo()!=null){
            this.titulo = dtoActualizarTopico.titulo();
        }

        if(dtoActualizarTopico.status()!=null){
            this.status = dtoActualizarTopico.status();
        }
    }

}
