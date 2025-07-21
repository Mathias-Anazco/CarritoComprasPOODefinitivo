package ec.edu.ups.modelo;

/**
 * Enumeración que define los roles de los usuarios dentro del sistema.
 * Proporciona un conjunto de constantes de tipo seguro para representar los
 * diferentes niveles de acceso y permisos.
 *
 * @author Mathias Añazco
 * @version 1.0
 * @since 18/07/2025
 */
public enum Rol {
    /**
     * Rol con los máximos privilegios, destinado a la administración del sistema,
     * como la gestión de usuarios y productos.
     */
    ADMINISTRADOR,

    /**
     * Rol estándar para los clientes o usuarios finales del sistema, con permisos
     * limitados a operaciones de compra y gestión de su propia información.
     */
    USUARIO
}