# Reflect

## 获取方法和属性

class.getDeclaredFields()能获取所有属性（public、protected、default、private），但不包括父类属性，
class.getFields() 获取类的属性（public），包括父类； 
apache commons包下的FieldUtils.getAllFields()可以获取类和父类的所有(public、protected、default、private)属性。
