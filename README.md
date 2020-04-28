# Testng Selenide Allure Soft Asserts
Demo project with soft asserts on testng, selenide and allure platforms

I want to get full allure report using soft asserts, but allure does't support them.
I hope this demo will help someone=)

Implimentation features:

1. For using soft asserts, i was create "SoftAssert" class with "assertEquals" method. Use it for compare expected and actual values.

2. If you put steps in other steps, use try\catch for all low level step method, wich marked @Step annotation. Else use try\catch for main step method. In catch blocks call "SoftAssert.unexpectedError" method for registered other autotest errors. And catch the top level exceptions as Throwable class.

3. Use "SoftAssert.assertAll" method at the end each test case method, wich marked @Test annotation.

4. Use "SoftAssert.updateTestResult" method in method wich marked @AfterMethod annotation.

Goodluck=)
