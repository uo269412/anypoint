# Diseño e Integración de Aplicaciones Internet Curso: 2023-2024 Práctica Final: Registro de Pokémon y entrenadores

## Temática y lógica de negocio

El proyecto trata sobre un formulario de registro para que, jugadores de la saga Pokémon (llamados trainers o entrenadores (clase Trainer)) compitan utilizando sus Pokémon. Desde la página web, podremos introducir los datos del entrenador y el Pokémon característico que utilizará, mientras que desde el csv podremos introducir al entrenador y a todos los demás Pokémon que vaya a utilizar.
Del entrenador recogemos el ID (que nos sirve para determinar si el Pokémon ha sido capturado por el entrenador o se ha obtenido de otros modos), nombre, edad y si ha competido previamente.
De los pokémon cogeremos el mote (nickname), la especie de Pokémon al que pertenece (pokemon_name), la pokéball en la que ha sido capturado, su naturaleza, los dos tipos que tiene (si solo tiene uno, el otro será none que se traducirá a null), el id del entrenador original, y luego características como vida (ps), ataque, defensa… Los atributos que se corresponden a las características +_evs, pertenecen a los puntos de esfuerzo que se sumarán a las estadísticas del Pokémon una vez se ha corroborado su legalidad. Por ejemplo un pokémon con 50 ps y 120 de ps_evs, tendrá 170 ps una vez se hayan realizado todos los cálculos.
Adicionalmente, los pokémon tendrán flags especiales. La primera verificación buscará si el pokémon existe en pokemon.properties, si no existe, se activará la flag de que el pokémon no es válido. Adicionalmente, existe legendaries.properties, en la que si el pokémon está, se activará el flag de que el pokémon es legendario. Si este lo es, se tendrá que verificar que sus tipos se corresponden a los del pokémon legendario (también en legendaries.properties), que su pokeball es de un tipo especial y debido a que es un pokémon poco común, también se verificará que el pokémon haya sido capturado por el mismo entrenador que lo está registrando. Si una de estas condiciones falla, se activará el flag de que este pokémon es ilegal.
Luego estos pokémon legendarios y los pokémon que sean validos, se someterán a otra verificación que comprobará que las características puestas en evs no superan los 504, si no, se tacharán como pokémon ilegales. Si los pokémon son ilegales, se convertirán a un tipo especial de pokémon llamado MissingNo y todas sus características pasarán a ser de 0. Si el pokémon es válido y legal, se computarán sus características finales y, en conjunto todas esas características harán una media que se añadirá a la media de fuerza del entrenador. 



