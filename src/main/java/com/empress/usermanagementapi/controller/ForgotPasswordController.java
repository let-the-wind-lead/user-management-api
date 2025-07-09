########################################
# 1) Datasource (Postgres on Railway) #
########################################

# Railway gives you a DATABASE_URL like:
#   postgres://<user>:<pass>@<host>:<port>/<db>
# We expose it as SPRING_DATASOURCE_URL so Spring Boot picks it up:
spring.datasource.url=${DATABASE_URL}
spring.datasource.username=${PGUSER}
spring.datasource.password=${PGPASSWORD}

# Let Hibernate auto‐create/update tables (DEV only—consider "validate" in prod)
spring.jpa.hibernate.ddl-auto=update
# Tell Hibernate exactly which dialect to use
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

########################################
# 2) JPA / Show SQL (optional)        #
########################################

# spring.jpa.show-sql=true
# spring.jpa.properties.hibernate.format_sql=true

########################################
# 3) Mail (needed by your EmailService)
########################################

# Your SMTP host (e.g. smtp.gmail.com, sendgrid, Mailgun, etc)
spring.mail.host=${SMTP_HOST}
spring.mail.port=${SMTP_PORT:587}
spring.mail.username=${SMTP_USERNAME}
spring.mail.password=${SMTP_PASSWORD}

# Gmail/etc generally wants TLS + auth
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

# The “from:” address your app will use
spring.mail.from=${SMTP_FROM}
