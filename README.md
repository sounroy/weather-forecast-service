# weather-forecast-service
#This is a weather forecast service, that fetches the weather data by zipcode, caches it for 30 minutes.
#Serves the data from cache if present,else makes call to external API.

# Code has docker integration
# Swagger integration
# Redis
# Micrometer Instrumentation
# Security is not added for brevity.

# Assumptions
# ZipCode to lat, lon transformation with Google Map API needed subscription hence , returning default data.
# OpenWeatherAPI key demands calls-calls subscription with payment card details to fetch data hence defaulting to # # default data.

#How to execute
# System should have docker installed.
# git clone the repo
# mvn clean install
# docker-compose down
# docker-compose up
# docker-compose build
# Or simly import the project & can run the app on IDE.
