from selenium import webdriver
import time
chrome_options = webdriver.ChromeOptions()

driver = webdriver.Remote(command_executor='http://192.168.196.242:4444', options=chrome_options)
driver.get("http://www.baidu.com")
time.sleep(5)
driver.quit()