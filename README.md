# GitHub API w Javie (Spring Boot)

REST API, które pobiera nieforkowane repozytoria użytkownika z GitHuba oraz informacje o gałęziach i commitach.

## Funkcjonalności

- Pobiera listę wszystkich repozytoriów, które nie są forkami  
- Zwraca nazwę repozytorium, login właściciela oraz wszystkie gałęzie wraz z najnowszym SHA commita  
- Zwraca kod 404, gdy użytkownik nie istnieje  

## Uruchomienie

```bash
mvn spring-boot:run
