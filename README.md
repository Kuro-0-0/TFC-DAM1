# 🚀 Gestión de Incidencias - Proyecto Final DAM (2024-2025) 

![Java](https://img.shields.io/badge/Java-21%2B-orange?style=flat-square&logo=java)
![License](https://img.shields.io/badge/License-MIT-blue?style=flat-square)

🔧 **Sistema de gestión de incidencias** desarrollado como proyecto final del primer curso del ciclo **Desarrollo de Aplicaciones Multiplataforma (DAM)** en **Salesianos Triana**.

## 🌟 Características principales

- 📝 Gestión completa de incidencias
- 👨‍💻 Asignación de técnicos y reportantes
- 📊 Panel de administración unificado
- 🔄 Datos generados automáticamente al iniciar (no persistentes)
- 🎨 Interfaz intuitiva y fácil de usar

## 🛠 Requisitos del sistema

| Requisito | Versión |
|-----------|---------|
| Java      | 21+     |

## 🚀 Cómo ejecutar

1. Descarga el archivo `.jar` desde la sección [Releases](https://github.com/Kuro-0-0/TFC-DAM1/releases)
2. Abre una terminal en la carpeta de descarga
3. Ejecuta:

```bash
java -jar GarciaMariaPablo-VERSION.jar
```

## 🥚 Easter Eggs (Sorpresas ocultas)

La aplicación esconde algunas funcionalidades especiales que se activan en ciertas situaciones:

🔧 URLs especiales
- /usuarios/modificar/5 - Mensaje oculto al intentar editar el técnico "sin-tecnico"
- /usuarios/modificar/6 - Mensaje secreto al tratar de modificar el reportante "sin-reportante"

⚠️ Protecciones del sistema
- /usuarios - Intenta eliminar un Admin... ¡no podrás hacerlo! El sistema te lo impedirá

🤖 Funciones simuladas
- /contacto - Al enviar un mensaje recibirás una alerta (aunque en realidad no se envía nada)
