<h1>Foro-Hub API Rest</h1>

<p>
  Foro-Hub es una API REST diseñada para gestionar un foro con temática de escuela de programación. Permite realizar operaciones CRUD sobre tópicos, respuestas y usuarios, además de manejar autenticación y autorización.
</p>

<h2>Tecnologías utilizadas</h2>
<ul>
  <li>Java 17</li>
  <li>Spring Boot 3.3.1</li>
  <li>Spring Security</li>
  <li>Spring Data JPA</li>
  <li>MySQL</li>
  <li>Maven</li>
  <li>Flyway</li>
  <li>JWT para autenticación</li>
  <li>Lombok</li>
  <li>SpringDoc OpenAPI</li>
</ul>

<h2>Instalación y Configuración</h2>
<p>Clona el repositorio:</p>
<pre>git clone https://github.com/thimo23/alura-challanges-foro-hub.git</pre>
<p>Instala las dependencias Maven:</p>
<pre>mvn install</pre>
<p>Configura tu base de datos MySQL y actualiza el archivo <code>application.properties</code> con tus credenciales.</p>
<p>Ejecuta el proyecto:</p>
<pre>mvn spring-boot:run</pre>

<h2>Casos de Uso y Funcionalidades</h2>
<p>El proyecto <strong>foro-hub</strong> ofrece una plataforma de discusión para una comunidad enfocada en la programación. A continuación, se detallan sus principales funcionalidades:</p>

<ul>
  <li>
    <h4>Registro de Usuarios</h4>
    <p>
      Los usuarios pueden registrarse en la plataforma proporcionando sus datos a través de un formulario. La funcionalidad de registro se maneja en el UsuarioController con el método registrarUsuario. Este método acepta un objeto DTORegistroUsuario, que incluye la información del usuario, y luego utiliza UsuarioService para registrar al usuario en la base de datos.
    </p>
    <br>
    <pre>
<code>
@PostMapping
public ResponseEntity<DTOResponseUsuario> registrarUsuario(@RequestBody @Valid DTORegistroUsuario dtoRegistroUsuario,
                                                           UriComponentsBuilder uriComponentsBuilder){
  DTOResponseUsuario nuevoUsuario = usuarioService.registrarUsuario(dtoRegistroUsuario);
  URI url = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(nuevoUsuario.id()).toUri();

  return ResponseEntity.created(url).body(nuevoUsuario);
}
</code>
    </pre>
  </li>

  <li>
    <h4>Autenticación</h4> 
    <p>El sistema de autenticación JWT se implementa en el AutenticacionController. Cuando un usuario intenta autenticarse, se envían sus credenciales al método autenticarUsuario, que utiliza AuthLoginService para verificar las credenciales. Si son válidas, se genera un token JWT que el usuario debe usar para acceder a las rutas protegidas.</p>
    <br>
    <pre>
<code>
@PostMapping
public ResponseEntity autenticarUsuario(@RequestBody @Valid DTOAuthUsuario datosAutenticacionUsuario) {
    DTOJWTToken dtojwtToken = authLoginService.autenticarUsuario(datosAutenticacionUsuario);
    return ResponseEntity.ok(dtojwtToken);
}
</code>
    </pre>
  </li>

  <li>
    <h4>CRUD de Tópicos</h4> 
    <p>Los usuarios pueden crear nuevos tópicos utilizando el método registrarTopico en TopicoController. Este método acepta un DTORegistroTopico con la información del tópico y utiliza TopicoService para procesar y guardar el tópico en la base de datos.</p>
    <br>
    <pre>
<code>
@PostMapping
public ResponseEntity<DTOResponseTopic> registrarTopico(@RequestBody @Valid DTORegistroTopico dtoRegistroTopico,
                                                        UriComponentsBuilder uriComponentsBuilder){
    DTOResponseTopic topicoRegistrado = topicoService.registrarTopico(dtoRegistroTopico);
    URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topicoRegistrado.id()).toUri();
    return ResponseEntity.created(url).body(topicoRegistrado);
}
</code>
    </pre>
  </li>

  <li>
    <h4>Respuestas a Tópicos</h4> 
    <p>Para responder a un tópico, los usuarios utilizan el método registrarRespuesta en RespuestaController. Este método acepta un DTORegistroRespuesta y utiliza RespuestaService para crear y guardar la respuesta en la base de datos.</p>
    <br>
    <pre>
<code>
@PostMapping
ResponseEntity registrarRespuesta(@RequestBody @Valid DTORegistroRespuesta dtoRegistroRespuesta,
                                  UriComponentsBuilder uriComponentsBuilder){
    DTOResponseRespuesta nuevaRespuesta = respuestaService.registrarRespuesta(dtoRegistroRespuesta);
    URI url = uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(nuevaRespuesta.id()).toUri();
    return ResponseEntity.created(url).body(nuevaRespuesta);
}
</code>
    </pre>
  </li>

  <li>
    <h4>Validaciones</h4> 
    <p>Las validaciones se realizan en varios puntos del flujo de registro y creación de tópicos y respuestas para asegurar la integridad de los datos. Por ejemplo, en TopicoService y RespuestaService, se utilizan listas de validadores (validadores y validadoresDeRespuestas) para aplicar reglas de validación específicas antes de procesar los tópicos y respuestas.</p>
    <br>
    <pre>
<code>
validadores.forEach(v->v.validar(datos));
</code>
    </pre>
  </li>
  <li>
    <h4>Paginación de Tópicos y Respuestas</h4> 
    <p>Para listar todos los tópicos registrados en la base de datos, se utiliza el método listadoTopicos en la clase TopicoController. Este método acepta parámetros para la paginación (page y size) y para el ordenamiento (sort). Utiliza el método findAllByOrderByFechaCreacionDesc del TopicoRepository para obtener una página de tópicos ordenados por fecha de creación en orden descendente. Cada tópico obtenido se convierte a un DTOListadoTopico para su presentación.</p>
    <br>
    <pre>
<code>
@GetMapping
public ResponseEntity<Page<DTOListadoTopico>> listadoTopicos(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "2") int size,
        @RequestParam(value = "sort", defaultValue = "fechaCreacion") String sort) {
    Pageable paginacion = PageRequest.of(page, size, Sort.by(Sort.Order.by(sort)));
    return ResponseEntity.ok(topicoRepository.findAllByOrderByFechaCreacionDesc(paginacion).map(DTOListadoTopico::new));
}
</code>
    </pre>
  <br>
  <p>
    Para listar un tópico específico junto con todas sus respuestas, se utiliza el método retonarDatosTopico en la clase TopicoService. Este método recibe el ID del tópico y un objeto Pageable para la paginación de las respuestas. Primero, obtiene el tópico por su ID y luego utiliza el RespuestaRepository para obtener todas las respuestas asociadas a ese tópico, ordenadas por si son solución o no. Finalmente, se crea un objeto DTOTopicoYRespuestas que incluye la información del tópico y las respuestas paginadas.
  </p>
  <br>
      <pre>
<code>
public DTOTopicoYRespuestas retonarDatosTopico(Long id, Pageable pag){
    Topico topico = topicoRepository.getReferenceById(id);
    DTOResponseTopic dtoResponseTopic = new DTOResponseTopic(
            topico.getId(), topico.getTitulo(),topico.getMensaje(),
            topico.getFechaCreacion(),topico.getStatus(),
            new DTOInfoUsuario(topico.getId(),topico.getAutor().getPerfil().getNombre()),
            topico.getCurso(), topico.getNumRespuestas());
    Page<DTOResponseRespuesta> dtoResponseRespuestas = respuestaRepository.findAllByTopicoIdOrderBySolucionDesc(id,pag)
            .map(DTOResponseRespuesta::new);
    return new DTOTopicoYRespuestas(dtoResponseTopic,dtoResponseRespuestas);
}
</code>
    </pre>
  </li>

  <li>
    <h4>Documentación API</h4> 
    <p>La documentación de la API se genera automáticamente utilizando SpringDoc, configurado en SpringDocsConfigurations. Esto facilita a los desarrolladores y usuarios finales comprender cómo utilizar los diferentes endpoints de la API.</p>
    <br>
    <pre>
<code>
@OpenAPIDefinition(
    info = @Info(
        title = "Foro-hub Api Rest",
        description ="Provee un CRUD para un foro con temática de escuela de programación")
)
</code>
    </pre>
  </li>
  <li>
    <h4>Max Retry/Jail</h4>
    <p><strong>Guava</strong> es una biblioteca de utilidades de Google para Java que proporciona colecciones, cachés, primitivas, concurrencia, manipulación de cadenas, y más.</p>
    <p>A partir de sus métodos para manejar caché, creamos la clase <code>IntentosLoginService</code>, que nos permite controlar el número de intentos de logueo fallidos para un nombre de usuario, dando como resultado el bloqueo del mismo por un tiempo determinado.</p>
    <p>Para más información, visita el enlace a la clase:</p>
    <a href="./api/src/main/java/foro/hub/api/domain/usuarios/IntentosLoginService.java">IntentosLoginService.java</a>
    <p>También puedes ver su implementación en la Clase Service:</p>
    <a href="./api/src/main/java/foro/hub/api/infra/security/AuthLoginService.java">AuthLoginService.java</a>  
</li>

</ul>


