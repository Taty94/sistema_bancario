# Sistema Bancario

Este proyecto es una aplicación de ejemplo para un **sistema bancario**.
El código fuente se encuentra en [GitHub](https://github.com/Taty94/sistema_bancario) y también está disponible como imagen en [Docker Hub](https://hub.docker.com/r/tmontene/sistemabancario-app).

## 📦 Repositorios

* **Código fuente:** [GitHub - Taty94/sistema_bancario](https://github.com/Taty94/sistema_bancario)
* **Imagen Docker:** [Docker Hub - tmontene/sistemabancario-app](https://hub.docker.com/r/tmontene/sistemabancario-app)

## 🚀 Cómo ejecutar

### Opción 1: Clonar el repositorio y ejecutar con Docker

```bash
# Clonar el repositorio
git clone https://github.com/Taty94/sistema_bancario.git
cd sistema_bancario

# Construir la imagen
docker build -t sistemabancario-app .

# Ejecutar el contenedor
docker run -d -p 8080:8080 --name sistema-bancario sistemabancario-app
```

### Opción 2: Usar la imagen directamente desde Docker Hub

```bash
docker pull tmontene/sistemabancario-app
docker run -d -p 8080:8080 --name sistema-bancario tmontene/sistemabancario-app
```

Con esto, la aplicación quedará disponible en:
👉 [http://localhost:8080](http://localhost:8080)

## ⚙️ Variables de entorno (si aplica)

Si la aplicación requiere configuración (por ejemplo conexión a base de datos), puedes pasar las variables necesarias al momento de ejecutar el contenedor:

```bash
docker run -d -p 8080:8080 \
  -e DB_HOST=mi-servidor-db \
  -e DB_USER=usuario \
  -e DB_PASS=clave \
  --name sistema-bancario tmontene/sistemabancario-app
```

## 🛠️ Tecnologías utilizadas

* Java / Spring Boot (según el proyecto)
* Maven / Gradle
* Docker

## 📖 Notas

* Puedes usar este proyecto como base para prácticas con microservicios, contenedores y despliegue en la nube.
* El tamaño de la imagen en Docker Hub es de **179 MB** aprox.

---

✍️ Autor: [Tatiana Montenegro](https://github.com/Taty94)
