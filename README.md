# 🚀 Gestión de Incidencias - Proyecto Final DAM (2024-2025)

![Java](https://img.shields.io/badge/Java-21%2B-orange?style=flat-square&logo=java)
![License](https://img.shields.io/badge/License-MIT-blue?style=flat-square)

🔧 **Sistema de gestión de incidencias** desarrollado como proyecto final del primer curso del ciclo **Desarrollo de Aplicaciones Multiplataforma (DAM)** en **Salesianos Triana**.

## 🌟 Características principales

- 🔒 **Sistema de seguridad por roles** con interfaces específicas para:
  - 👤 **USER** (Usuario estándar)
  - 🛠️ **TECH** (Técnico)
  - 👑 **ADMIN** (Administrador)
- 📝 Gestión completa de incidencias
- 👨‍💻 Asignación de técnicos y reportantes
- 📊 Panel de administración unificado
- 🔄 Datos generados automáticamente al iniciar (no persistentes)
- 🎨 Interfaz intuitiva y fácil de usar

## 👥 Usuarios de prueba

El sistema incluye **23 usuarios predefinidos** para testing:

### 🔹 Usuarios estándar (10)
Username: user01, user02, ..., user10
Password: 1234


### 🔧 Técnicos (10)
Username: tech01, tech02, ..., tech10
Password: 1234


### 👑 Cuentas especiales (3)
Admin: admin / admin
Usuario: user / user
Técnico: tech / tech


## 🛠 Requisitos técnicos

| Componente | Versión requerida |
|------------|------------------|
| Java       | JDK 21+          |
| RAM        | Mínimo 2GB       |

## 🚀 Instalación y ejecución

1. Descargar el archivo `.jar` desde [Releases](https://github.com/Kuro-0-0/TFC-DAM1/releases)
2. Ejecutar desde terminal:
```bash
java -jar GarciaMariaPablo-VERSION.jar
```
# 🎮 Funcionalidades por rol
### 👤 USER
- Reportar incidencias
- Ver historial
- Ver y Editar su perfil

### 🛠️ TECH
- Ver incidencias
- Ver sus estadisticas
- Asignarse incidencias
- Gestionar incidencias asignadas
- Ver y Editar su perfil

### 👑 ADMIN
- Gestión completa de usuarios
- Gestión completa de incidencias
- Gestión completa de estados
- Estadísticas avanzadas
- Ver y Editar su perfil


# Detalles del sistema

## 🥚 Easter Eggs
### 🔧 URLs especiales:

- /admin/usuarios/modificar/5  → Mensaje oculto
- /admin/usuarios/modificar/6  → Mensaje secreto
- /admin/nosotros             → Agradecimientos
### ⚠️ Protecciones del sistema:

- Intento de eliminar admin → Mensaje de error especial

### 🤖 Funciones simuladas:
- Envío de mensajes en /contacto → Alerta simulada
