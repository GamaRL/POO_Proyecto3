package configuraciones;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import modelos.Platillo;
import modelos.Restaurante;
import repositorios.Repositorio;

/**
 * Clase que contiene la configuración inicial para crear un archivo que
 * contenga la
 * información del restaurante (nombre, número de mesas y platillos).
 */
public class ManejadorRestaurante {
  /**
   * Métodos que establece los platillos con sus características (nombre, precio y
   * descripción)
   */
  public static void reescribirArchivo() {
    // Configuración del restaurante
    Restaurante res = new Restaurante("COCINA MEXICANA", 8);

    // Configuración de los platillos
    res.agregarPlatillo(new Platillo("Sopa del día", 70, "Puede elegir entre pierna, muslo o rabadilla"));
    res.agregarPlatillo(new Platillo("Arroz", 30, "Arroz rojo o blanco"));
    res.agregarPlatillo(new Platillo("Spaghetti", 32, "Rojo o blanco según el día"));
    res.agregarPlatillo(new Platillo("Enchiladas", 120, "Tortillas con salsa"));
    res.agregarPlatillo(new Platillo("Mole", 120, "Un platillo típico de México"));
    res.agregarPlatillo(new Platillo("Milanesa", 70, "De pollo o de res"));
    res.agregarPlatillo(new Platillo("Arrachera", 70, "Asada o término medio"));
    res.agregarPlatillo(new Platillo("Tacos Dorados", 65, "Tortilla con carne, lechuga, crema y queso"));
    res.agregarPlatillo(new Platillo("Agua de frutas", 15, "Agua fresca de temporada"));
    res.agregarPlatillo(new Platillo("Refresco", 15, "Pespi, Coca Cola, Boing"));

    /**
     * Manejo de excepciones por el archivo que guardará el estado de cada uno de
     * los elementos que componen a los platillos
     */
    try {
      File ruta = new File(new File(Repositorio.getRuta()), Repositorio.ARCHIVO_RESTAURANTE);
      ObjectOutputStream s = new ObjectOutputStream(new FileOutputStream(ruta));
      s.writeObject(res);
      s.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
