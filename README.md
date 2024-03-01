## Приложение калькулятор дат

### Информация о лабораторной работе

**Тема:** REST API Калькулятора дат

**Текущие возможности:** 
- Расчет расстояния между date1 и date2: *GET /date-dif?date1={date1}&date2={date2}*

**Использованные технологии:** OpenJDK 21, Spring Boot 3.2.3, Maven

### Установка и Запуск
- Скопировать репозиторий:
```
git clone https://github.com/ansoox/dateCalculator.git
```
- Собрать проект:
```
mvn clean install
```
- Запустить приложение (на http://localhost:8080):
```
java -jar target/dateCalculator-0.0.1-SNAPSHOT.jar
```