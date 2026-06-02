CREATE DATABASE company_db;

CREATE TABLE employee (
                          id serial PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          department VARCHAR(255) NOT NULL,
                          salary DECIMAL(10,2) NOT NULL
);

CREATE TABLE project (
                         id serial PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         budget DECIMAL(10,2) NOT NULL
);

CREATE TABLE assignment (
                            employee_id INT,
                            project_id INT,
                            role VARCHAR(255) NOT NULL,
                            PRIMARY KEY (employee_id, project_id),
                            FOREIGN KEY (employee_id) REFERENCES employee(id) ON DELETE CASCADE,
                            FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE
);
