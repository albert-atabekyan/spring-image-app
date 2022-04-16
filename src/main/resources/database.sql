-- Table: users
# CREATE TABLE t_images (
#                        id       INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
#                        url VARCHAR(255) NOT NULL
# )
#     ENGINE = InnoDB;
# #
# -- Table: roles
# CREATE TABLE t_roles (
#                        id   INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
#                        name VARCHAR(100) NOT NULL
# )
#     ENGINE = InnoDB;
#
# -- Table for mapping user and roles: user_roles
# CREATE TABLE user_roles (
#                             user_id INT NOT NULL,
#                             role_id INT NOT NULL,
#
#                             FOREIGN KEY (user_id) REFERENCES t_users (id),
#                             FOREIGN KEY (role_id) REFERENCES t_roles (id),
#
#                             UNIQUE (user_id, role_id)
# )
#     ENGINE = InnoDB;
#
# -- Insert data
#
# INSERT INTO t_users VALUES (1, 'proselyte', '$2a$11$uSXS6rLJ91WjgOHhEGDx..VGs7MkKZV68Lv5r1uwFu7HgtRn3dcXG');

INSERT INTO t_roles VALUES (1, 'ROLE_USER');
INSERT INTO t_roles VALUES (2, 'ROLE_ADMIN');

# INSERT INTO user_roles VALUES (1, 2);