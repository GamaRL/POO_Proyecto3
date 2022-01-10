# Proyecto 3 - Administración de un restaurante
## Equipo 4
- García Lemus Rocío
- Juárez Juárez, María José
- López Chong, Jorge Antonio
- Ríos Lira Gamaliel

## Instrucciones para la construcción del proyecto
El proyecto fue desarrollado en el entorno de desarrollo `Visual Studio Code`. Simplemente abriendo el directorio `src/` éste se podrá construir exitosamente.

En caso de que no se cuente con este IDE, se puede proceder de la siguiente manera:

1. Dirigirse al directorio del proyecto
2. Ejecutar el comando:
```bash
javac src/Main.java -sourcepath src/ -d out
```

3. Posteriormente, para ejecutar el proyecto, se debe ejecutar el siguiente comando:
```bash
java -classpath out/ Main
```

## Instrucciones para construir el proyecto LaTeX
1. Dirigirse al directorio `docs/escrito/`.
2. Ejecutar el comando:
```bash
pdflatex -output-directory=build/ main
```
3. Dirigirse al directorio `docs/escrito/build/` y abrir el archivo 
   `main.pdf`.
