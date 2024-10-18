# Delivery de bebidas con alcohol - Funcionalidades principales

## 1. Gestión de productos
- **Catálogo de bebidas**:
  - Visualización de productos organizados por categorías (cervezas, vinos, licores, etc.).
  - Filtros por precio, tipo de bebida y marca.
  - Paginación o scroll infinito para mejorar la carga.
  - Información detallada del producto (imagen, descripción, precio, reseñas, etc.).

## 2. Carrito de compras
- **Añadir al carrito**:
  - Agregar productos al carrito desde el catálogo.
  - Persistencia del carrito entre sesiones usando almacenamiento local o base de datos.
- **Edición del carrito**:
  - Modificación de cantidades o eliminación de productos.
  - Verificación en tiempo real del stock.
- **Resumen del pedido**:
  - Detalle de productos, cantidades, precios y costos de envío.
- **Proceso de compra**:  
  - Formulario para ingresar dirección de entrega y métodos de pago.

## 3. Seguimiento del pedido
- **Estado del pedido**:
  - Actualización en tiempo real del estado del pedido (confirmado, en preparación, en camino, entregado).
  - Notificaciones push en cada actualización del pedido.
- **Mapa en tiempo real**:
  - Seguimiento del repartidor mediante Google Maps.
- **Detalles del pedido**:
  - Resumen detallado de productos, dirección y tiempo estimado de llegada.
- **Retroalimentación post-entrega**:
  - Solicitud de calificación y comentario una vez entregado el pedido.

## 4. Comentarios y reseñas
- **Calificación del servicio**:
  - Sistema de puntuación y reseñas escritas por el servicio dado.
- **Moderación**:
  - Moderación de reseñas y posibilidad de reportar contenido inapropiado.

## 5. Historial de pedidos
- **Listado de pedidos anteriores**:
  - Visualización de todos los pedidos con detalles de productos, fechas y costos.
- **Repetir pedido**:
  - Opción para repetir pedidos anteriores con un solo clic.
- **Estado del pedido**:
  - Mostrar estado final del pedido (entregado, cancelado, en disputa).
- **Recibos**:
  - Descarga de comprobantes en formato PDF o PNG.

## 6. Soporte
- **Centro de ayuda**:
  - Sección de preguntas frecuentes (FAQ) para resolver dudas comunes.

## 7. Notificaciones push
- **Actualizaciones del pedido**:
  - Notificaciones push para cada cambio en el estado del pedido.

## 8. Autenticación
- **Registro de usuarios**:
  - Formulario de registro para nuevos usuarios con validación de correo electrónico.
  - Opción de autenticación con redes sociales (Google, Facebook) para agilizar el proceso de login.
- **Inicio de sesión**:
  - Autenticación de usuarios mediante correo electrónico y contraseña.
  - Opción de autenticación con redes sociales (Google, Facebook) para agilizar el proceso de login.
- **Restablecimiento de contraseña**:
  - Funcionalidad para que los usuarios puedan restablecer su contraseña en caso de olvido.
  - Envío de enlaces de recuperación de contraseña por correo electrónico.
- **Sesión persistente**:
  - Permitir que los usuarios mantengan la sesión iniciada para agilizar futuros accesos.
  - Expiración de sesiones después de un período de inactividad para mayor seguridad.
- **Panel de usuario**:
  - Pantalla para que los usuarios puedan editar su perfil, incluyendo datos personales y dirección de entrega.
