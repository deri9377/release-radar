# release_radar
Steps to run this project
./gradlew clean build
./gradlew bootRun

# This project pulls from the Spotify API
there is an in memory database in this project call Hibernate
I read and write from this database using H2-hibernate
you can see the database configurations in JDBCTemplate also I use the CRUDRepositories to do read/writes