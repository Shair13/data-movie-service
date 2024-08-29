# Data Movie Service

## Service Description:

The Data Movie Service is a microservice designed for managing movie data. It connects to a database and provides
endpoints for adding, searching, updating, and deleting movies.

Features:

* Flexible Querying: Utilizes Criteria Queries to enable searches based on any field. 
* Database Integration: Uses H2 for local development and PostgreSQL for production, both managed with Liquibase for smooth database migrations. 
* Comprehensive Operations: Supports full CRUD operations (Create, Read, Update, Delete) for movie data.
