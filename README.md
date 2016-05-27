Set up:

1. Install MySQL
2. Go to db/ directory
3. Run command:
   
   mysql -uroot -p < fresh.sql

   (password is "admin" - SHHHHHHH!!!!)

Build:

1. gradle build

Run App:

1. gradle run

Run Tests with Code Coverage:

1. gradle test jacocoTestReport

Open test results:

1. open build/reports/jacoco/test/html/index.html
