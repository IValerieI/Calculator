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

![Screenshot_1](https://github.com/IValerieI/Calculator/assets/53052683/aa353b7c-fa6b-43df-9a09-3cc872f4ec4c)

![Screenshot_2](https://github.com/IValerieI/Calculator/assets/53052683/cd61e092-9275-4f8f-8602-fab251262c31)

![Screenshot_3](https://github.com/IValerieI/Calculator/assets/53052683/7e3c3d30-7208-4d4e-afcb-95196ee1310e)

![Screenshot_4](https://github.com/IValerieI/Calculator/assets/53052683/1430aa33-7e37-4a39-8e67-2ea51c2dc6df)

![Screenshot_5](https://github.com/IValerieI/Calculator/assets/53052683/9f241b48-3689-49ab-85f2-b707dcc65126)
