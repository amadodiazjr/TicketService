Set up:

1. Install MySQL
2. Go to db/ directory
3. Run command:
   
   mysql -uroot -p < fresh.sql

   (password is "admin" - SHHHHHHH!!!!)

Build:

  gradle build

Run App:

  gradle run

Run Tests with Code Coverage:

  gradle test jacocoTestReport

Open test results:

  open build/reports/jacoco/test/html/index.html
