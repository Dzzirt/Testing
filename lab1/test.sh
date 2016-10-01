# Пожалуйста, положите исполнительный файл программы рядом с этим скриптом
# Не треугольник
java -jar lab1.jar 1 2 3
echo "----------------------------"
# Обычный
java -jar lab1.jar 2 4 3
echo "----------------------------"

# Равнобедренный
java -jar lab1.jar 1 1 0.5
echo "----------------------------"

# Равносторонний
java -jar lab1.jar 1 1 1
echo "----------------------------"

# Incorrect input
java -jar lab1.jar 2 4 3 4
echo "-------"
java -jar lab1.jar 2 4 s
echo "-------"
java -jar lab1.jar asdasd 4 asda
echo "-------"
java -jar lab1.jar a b c
echo "-------"
java -jar lab1.jar 0 0 0
echo "-------"
java -jar lab1.jar -100 -2 -1



