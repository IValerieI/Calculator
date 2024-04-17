Задание: 
Приложение "Калькулятор отпускных".
Микросервис на SpringBoot + Java 11 c одним API:
GET "/calculacte"

Минимальные требования: Приложение принимает твою среднюю зарплату за 12 месяцев и количество дней отпуска - отвечает суммой отпускных, которые придут сотруднику.
Доп. задание: При запросе также можно указать точные дни ухода в отпуск, тогда должен проводиться рассчет отпускных с учётом праздников и выходных.

Выполнение:
В проекте выполнены оба варианта. 
У обычного калькулятора точка входа /calculate, у калькулятора с датами точка входа /calculate/dates. 
Для получения списка месяцев с указанием выходных и праздников используется производственный календарь в формате json (https://xmlcalendar.ru/data/ru/2024/calendar.json).

Примеры запросов (Postman):
![Screenshot_1](https://github.com/IValerieI/Calculator/assets/53052683/155cc32d-cef8-465e-a659-74c12955c3df)
![Screenshot_2](https://github.com/IValerieI/Calculator/assets/53052683/bd55da3a-9d29-43ae-9035-405aa93e6d68)
![Screenshot_3](https://github.com/IValerieI/Calculator/assets/53052683/8aa6cf42-d583-4878-a5bf-9bf5a29d4e8a)
![Screenshot_4](https://github.com/IValerieI/Calculator/assets/53052683/3f77908c-4ff2-43bf-9d40-83add91f2353)
![Screenshot_5](https://github.com/IValerieI/Calculator/assets/53052683/5d034f23-93ec-4442-b296-efe09d5f1fc9)
