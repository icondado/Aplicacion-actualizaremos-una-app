# Tarea 04: Multimedia y UX - Spyro The Dragon

**BK Programación** | Proyecto desarrollado por: **Irene Condado Alcantarilla**

## Introducción
Este proyecto consiste en la evolución multimedia de una aplicación base inspirada en el universo de **Spyro the Dragon**. El propósito principal es mejorar la retención del usuario y la competitividad de la app mediante el uso estratégico de elementos visuales, sonoros y sorpresas interactivas (Easter Eggs), asegurando una experiencia de usuario (UX) inmersiva y funcional.

## Características principales
*   **Guía Interactiva (Onboarding)**: Sistema de 6 pantallas con bloqueo de interacción que explica las secciones de Personajes, Mundos, Coleccionables e Información.
*   **Bocadillos Dinámicos (Tooltips)**: Implementación de globos informativos púrpuras mediante la librería **Balloon**. Aparecen de forma animada (`ELASTIC`) al pulsar sobre la barra de navegación inferior y el icono de información.
*   **Navegación Automatizada**: Durante la guía, la aplicación cambia automáticamente de fragmento para posicionar los bocadillos sobre los iconos correspondientes.
*   **Easter Egg de Vídeo**: Activación de un vídeo temático mediante una interacción de triple clic en la lista de mundos.
*   **Easter Egg Canvas**: Animación avanzada de brillo e intensidad progresiva en el cetro de Ripto mediante una pulsación prolongada.

### Vista Previa de la Aplicación
![Inicio](img/inicio.png)

*Interfaz representativa del inicio.*

## Tecnologías utilizadas
*   **Kotlin**: Lenguaje de programación para la lógica de negocio.
*   **Navigation Component**: Para la gestión de fragmentos y flujo entre pantallas.
*   **Balloon Library**: Librería externa (`com.github.skydoves:balloon`) para la creación de bocadillos animados.
*   **API Canvas**: Para el renderizado técnico de la energía mágica del cetro de Ripto.
*   **SharedPreferences**: Gestión de la persistencia simple para el control de la guía.
*   **Recursos Multimedia**: Gestión de vídeo (`.mp4`) y audio (`.mp3`) mediante `VideoView` y `MediaPlayer`.

## Instrucciones de uso
1.  **Clonación**:
    ```bash
    git clone https://github.com/black-cat-17/Tarea4_CondadoAlcantarilla_Irene.git
    ```
2.  **Sincronización**: Al abrir en Android Studio, es necesario realizar un *Sync Project with Gradle Files* para cargar la dependencia de Balloon definida en el Version Catalog (`libs.versions.toml`).
3.  **Ejecución**: La guía interactiva se iniciará automáticamente la primera vez que se instale la aplicación.

## Conclusiones del desarrollador
El proceso de desarrollo ha permitido integrar de manera fluida elementos multimedia y animaciones que enriquecen la experiencia del usuario.
*   **Desafíos**: El mayor reto fue el posicionamiento dinámico de los bocadillos de la librería Balloon sobre los elementos del `BottomNavigationView`, asegurando que la flecha del globo apuntara correctamente a cada icono.
*   **Aprendizaje**: Se ha profundizado en la creación de interfaces dinámicas y el uso de librerías de terceros para mejorar la usabilidad (UX) sin sacrificar el rendimiento de la aplicación.
