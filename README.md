<h1>Foro-Hub API Rest</h1>

<p>
  Foro-Hub es una API REST diseñada para gestionar un foro con temática de escuela de programación. Permite realizar operaciones CRUD sobre tópicos, respuestas y usuarios, además de manejar autenticación y autorización.
</p>

<h2>Índice</h2>
<ul>
  <li><a href="#tecnologias-utilizadas">Tecnologías Utilizadas</a></li>
  <li><a href="#instalacion-y-configuracion">Instalación y Configuración</a></li>
  <li><a href="#usos">Usos</a></li>
  <li><a href="#funcionalidades">Funcionalidades</a></li>
  <li><a href="#documentacion-api">Documentación API</a></li>
  <li><a href="#agradecimientos-y-contribuciones">Agradecimientos y Contribuciones</a></li>
</ul>
<h2 id="tecnologias-utilizadas">Tecnologías utilizadas</h2>
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

<h2 id="instalacion-y-configuracion">Instalación y Configuración</h2>
<p>Clona el repositorio:</p>
<pre>git clone https://github.com/thimo23/alura-challanges-foro-hub.git</pre>
<p>Instala las dependencias Maven:</p>
<pre>mvn install</pre>
<p>Configura tu base de datos MySQL y actualiza el archivo <code>application.properties</code> con tus credenciales.</p>
<p>Ejecuta el proyecto:</p>
<pre>mvn spring-boot:run</pre>

<h2 id="usos">Usos</h2>
<ol>
    <li>
        <strong>Registrar un nuevo tópico</strong><br>
        <strong>Endpoint:</strong> POST /topicos<br>
        <strong>Descripción:</strong> Este endpoint permite registrar un nuevo tópico en la base de datos.<br>
        <strong>JSON de solicitud:</strong>
        <pre>
{
  "titulo": "Título del tópico",
  "mensaje": "Mensaje del tópico",
  "curso": "Nombre del curso"
}
        </pre>
    </li>
    <li>
        <strong>Listar todos los tópicos</strong><br>
        <strong>Endpoint:</strong> GET /topicos<br>
        <strong>Descripción:</strong> Devuelve una lista paginada de todos los tópicos registrados en la base de datos.<br>
        <strong>Parámetros:</strong> page, size, sort<br>
        <strong>Ejemplo de solicitud:</strong> /topicos?page=0&size=10&sort=fechaCreacion
    </li>
    <li>
        <strong>Registrar una nueva respuesta a un tópico</strong><br>
        <strong>Endpoint:</strong> POST /respuestas<br>
        <strong>Descripción:</strong> Permite registrar una nueva respuesta asociada a un tópico específico.<br>
        <strong>JSON de solicitud:</strong>
        <pre>
{
  "mensaje": "Mensaje de la respuesta",
  "topicoID": "ID del tópico al que se responde"
}
        </pre>
    </li>
    <li>
        <strong>Actualizar una respuesta</strong><br>
        <strong>Endpoint:</strong> PUT /respuestas<br>
        <strong>Descripción:</strong> Permite editar el contenido de una respuesta existente.<br>
        <strong>JSON de solicitud:</strong>
        <pre>
{
  "iDRespuesta": "ID de la respuesta a actualizar",
  "mensaje": "Nuevo mensaje de la respuesta"
}
        </pre>
    </li>
    <li>
        <strong>Registrar un nuevo usuario</strong><br>
        <strong>Endpoint:</strong> POST /usuarios<br>
        <strong>Descripción:</strong> Registra un nuevo usuario en el sistema.<br>
        <strong>JSON de solicitud:</strong>
        <pre>
{
  "nombre": "Nombre del usuario",
  "email": "Email del usuario",
  "password": "Contraseña"
}
        </pre>
    </li>
    <li>
        <strong>Autenticación de usuario</strong><br>
        <strong>Endpoint:</strong> POST /login<br>
        <strong>Descripción:</strong> Obtiene un token JWT para un usuario registrado, permitiendo el acceso a endpoints protegidos.<br>
        <strong>JSON de solicitud:</strong>
        <pre>
{
  "email": "Email del usuario",
  "password": "Contraseña"
}
        </pre>
    </li>
</ol>

<h2 id="funcionalidades">Funcionalidades</h2>
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
<li>
  <h4>Tests Unitarios y de Integración</h4>
  <p>En proceso... ⏳</p>
</li>
</ul>
<h2 id="documentacion-api">Como acceder a la documentación</h2>
<p>Para acceder a la documentación de la API de Foro-hub, que ha sido generada utilizando SpringDoc, sigue los siguientes pasos:</p>
<ol>
    <li>
        Asegúrate de que la aplicación esté en ejecución. Si no está en ejecución, puedes iniciarla utilizando Maven con el siguiente comando en la terminal, ubicándote en el directorio raíz del proyecto (donde se encuentra el archivo <code>pom.xml</code>):
        <pre>mvn spring-boot:run</pre>
    </li>
    <li>
        Una vez que la aplicación esté en ejecución, abre un navegador web y visita la URL de la documentación de la API generada por SpringDoc. La URL por defecto para acceder a la documentación de la API es:
        <pre>http://localhost:8080/swagger-ui/index.html</pre>
        Reemplaza <code>localhost</code> y <code>8080</code> si tu aplicación se está ejecutando en una dirección o puerto diferente.
    </li>
    <li>
        En la página de documentación de la API, verás una interfaz de usuario interactiva proporcionada por Swagger UI, donde se listan todos los endpoints disponibles en la API de Foro-hub. La documentación incluye detalles como los métodos HTTP (GET, POST, PUT, DELETE), los parámetros de entrada, los modelos de datos, y las respuestas esperadas para cada endpoint.
    </li>
    <li>
        Puedes interactuar con la API directamente desde esta interfaz de usuario. Para hacerlo, haz clic en cualquier endpoint listado para expandir sus detalles. Luego, puedes probar el endpoint haciendo clic en el botón <strong>Try it out</strong>, llenando los parámetros necesarios si los hay, y finalmente haciendo clic en <strong>Execute</strong>. Verás la respuesta de la API directamente en la interfaz.
    </li>
</ol>

<h2 id="agradecimientos-y-contribuciones">Agradecimientos y Contribuciones</h2>
<p>Quiero expresar mi más sincero agradecimiento al programa <strong>Oracle Next Education</strong> y a la plataforma <strong>Alura</strong> por ofrecerme este desafío. Gracias a esta experiencia, he podido diversificar mis conocimientos en el desarrollo backend y mejorar mis habilidades técnicas.</p>

<h3>Contribuciones</h3>

<p>Si deseas contribuir a este proyecto, aquí hay algunas maneras en las que puedes hacerlo:</p>

<ul>
    <li><strong>Reportar Issues:</strong> Si encuentras errores o tienes sugerencias de mejora, no dudes en abrir un issue en este repositorio.</li>
    <li><strong>Pull Requests:</strong> Si tienes mejoras o nuevas funcionalidades que deseas implementar, ¡estaré encantado de revisar tus pull requests!</li>
    <li><strong>Documentación:</strong> Ayuda a mejorar la documentación del proyecto. Cualquier aportación en este sentido es muy valiosa.</li>
    <li><strong>Feedback:</strong> Cualquier comentario sobre el proyecto es bienvenido. Tu opinión puede ayudar a hacer de este un mejor recurso para todos.</li>
</ul>
