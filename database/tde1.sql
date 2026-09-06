-- ============================================================
-- Assessment Management System (TDE1)
-- Database: PostgreSQL
-- ============================================================

-- ============================================================
-- TABLE: users
-- ============================================================

CREATE TABLE users (
                        id BIGSERIAL PRIMARY KEY,
                        email VARCHAR(255) NOT NULL UNIQUE,
                        name VARCHAR(150) NOT NULL,
                        type VARCHAR(20) NOT NULL,
                        password VARCHAR(255) NOT NULL,

                        CONSTRAINT ck_users_type
                            CHECK (type IN ('ADMIN', 'AUTHOR'))
);


-- ============================================================
-- TABLE: courses
-- ============================================================

CREATE TABLE courses (
                         id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(150) NOT NULL,
                         description TEXT,
                         class_format VARCHAR(20) NOT NULL,

                         CONSTRAINT ck_courses_class_format
                             CHECK (class_format IN ('PRESENTIAL', 'EAD', 'HYBRID'))
);


-- ============================================================
-- TABLE: disciplines
-- ============================================================

CREATE TABLE disciplines (
                             id BIGSERIAL PRIMARY KEY,
                             name VARCHAR(150) NOT NULL,
                             description TEXT,
                             workload_hours INTEGER NOT NULL,
                             shift VARCHAR(20) NOT NULL,
                             course_id BIGINT NOT NULL,

                             CONSTRAINT ck_disciplines_workload_hours
                                 CHECK (workload_hours > 0),

                             CONSTRAINT ck_disciplines_shift
                                 CHECK (shift IN ('AFTERNOON', 'NIGHT')),

                             CONSTRAINT fk_disciplines_course
                                 FOREIGN KEY (course_id)
                                     REFERENCES courses(id)
);


-- ============================================================
-- TABLE: user_courses
-- Association between users and courses
-- ============================================================

CREATE TABLE user_courses (
                              user_id BIGINT NOT NULL,
                              course_id BIGINT NOT NULL,

                              PRIMARY KEY (user_id, course_id),

                              CONSTRAINT fk_user_courses_user
                                  FOREIGN KEY (user_id)
                                      REFERENCES users(id),

                              CONSTRAINT fk_user_courses_course
                                  FOREIGN KEY (course_id)
                                      REFERENCES courses(id)
);


-- ============================================================
-- TABLE: user_subjects
-- Association between users and disciplines
-- ============================================================

CREATE TABLE user_subjects (
                               user_id BIGINT NOT NULL,
                               discipline_id BIGINT NOT NULL,

                               PRIMARY KEY (user_id, discipline_id),

                               CONSTRAINT fk_user_subjects_user
                                   FOREIGN KEY (user_id)
                                       REFERENCES users(id),

                               CONSTRAINT fk_user_subjects_discipline
                                   FOREIGN KEY (discipline_id)
                                       REFERENCES disciplines(id)
);


-- ============================================================
-- TABLE: questions
-- ============================================================

CREATE TABLE questions (
                           id BIGSERIAL PRIMARY KEY,
                           type VARCHAR(20) NOT NULL,
                           difficulty VARCHAR(20) NOT NULL,
                           description TEXT NOT NULL,
                           option_count INTEGER,
                           discipline_id BIGINT NOT NULL,
                           author_id BIGINT NOT NULL,

                           CONSTRAINT ck_questions_type
                               CHECK (type IN ('OPEN', 'MULTIPLE_CHOICE')),

                           CONSTRAINT ck_questions_difficulty
                               CHECK (difficulty IN ('EASY', 'MEDIUM', 'HARD')),

                           CONSTRAINT fk_questions_discipline
                               FOREIGN KEY (discipline_id)
                                   REFERENCES disciplines(id),

                           CONSTRAINT fk_questions_author
                               FOREIGN KEY (author_id)
                                   REFERENCES users(id)
);


-- ============================================================
-- TABLE: question_options
-- ============================================================

CREATE TABLE question_options (
                                  id BIGSERIAL PRIMARY KEY,
                                  text TEXT NOT NULL,
                                  is_correct BOOLEAN NOT NULL,
                                  question_id BIGINT NOT NULL,

                                  CONSTRAINT fk_question_options_question
                                      FOREIGN KEY (question_id)
                                          REFERENCES questions(id)
);


-- ============================================================
-- TABLE: assessments
-- ============================================================

CREATE TABLE assessments (
                             id BIGSERIAL PRIMARY KEY,
                             semester VARCHAR(20) NOT NULL,
                             teacher VARCHAR(150) NOT NULL,
                             assessment_date DATE NOT NULL,
                             elaboration_date DATE NOT NULL,
                             type VARCHAR(40) NOT NULL,
                             value NUMERIC(6,2) NOT NULL,
                             multiple_choice_question_count INTEGER NOT NULL,
                             open_question_count INTEGER NOT NULL,
                             course_id BIGINT NOT NULL,
                             discipline_id BIGINT NOT NULL,
                             author_id BIGINT NOT NULL,

                             CONSTRAINT ck_assessments_type
                                 CHECK (type IN (
                                     'AV1',
                                     'AV1_SECOND_CALL',
                                     'AV1_ADAPTED',
                                     'AV1_ADAPTED_SECOND_CALL',
                                     'AV2',
                                     'AV2_SECOND_CALL',
                                     'AV2_ADAPTED',
                                     'AV2_ADAPTED_SECOND_CALL',
                                     'FINAL',
                                     'FINAL_SECOND_CALL',
                                     'FINAL_ADAPTED',
                                     'FINAL_ADAPTED_SECOND_CALL'
                                 )),

                             CONSTRAINT fk_assessments_course
                                 FOREIGN KEY (course_id)
                                     REFERENCES courses(id),

                             CONSTRAINT fk_assessments_discipline
                                 FOREIGN KEY (discipline_id)
                                     REFERENCES disciplines(id),

                             CONSTRAINT fk_assessments_author
                                 FOREIGN KEY (author_id)
                                     REFERENCES users(id)
);


-- ============================================================
-- TABLE: assessment_multiple_choice_questions
-- Association between assessments and multiple choice questions
-- ============================================================

CREATE TABLE assessment_multiple_choice_questions (
                                                      assessment_id BIGINT NOT NULL,
                                                      question_id BIGINT NOT NULL,
                                                      value NUMERIC(6,2) NOT NULL,

                                                      PRIMARY KEY (assessment_id, question_id),

                                                      CONSTRAINT fk_amcq_assessment
                                                          FOREIGN KEY (assessment_id)
                                                              REFERENCES assessments(id),

                                                      CONSTRAINT fk_amcq_question
                                                          FOREIGN KEY (question_id)
                                                              REFERENCES questions(id)
);


-- ============================================================
-- TABLE: assessment_open_questions
-- Association between assessments and open questions
-- ============================================================

CREATE TABLE assessment_open_questions (
                                           assessment_id BIGINT NOT NULL,
                                           question_id BIGINT NOT NULL,
                                           value NUMERIC(6,2) NOT NULL,

                                           PRIMARY KEY (assessment_id, question_id),

                                           CONSTRAINT fk_aoq_assessment
                                               FOREIGN KEY (assessment_id)
                                                   REFERENCES assessments(id),

                                           CONSTRAINT fk_aoq_question
                                               FOREIGN KEY (question_id)
                                                   REFERENCES questions(id)
);


-- ============================================================
-- TABLE: templates
-- ============================================================

CREATE TABLE templates (
                           id BIGSERIAL PRIMARY KEY,
                           name VARCHAR(150) NOT NULL,
                           file_name VARCHAR(255) NOT NULL
);


-- ============================================================
-- SEED DATA
-- Passwords are BCrypt hashes of "password123"
-- ============================================================

INSERT INTO users (email, name, type, password)
VALUES ('admin@example.com', 'System Admin', 'ADMIN', '$2a$10$lbRlUgsfy4KCzXFpDAZ5HOS5j68//DVVDGtcwDdi/un4EracqMYMW'),
       ('author@example.com', 'Content Author', 'AUTHOR', '$2a$10$lbRlUgsfy4KCzXFpDAZ5HOS5j68//DVVDGtcwDdi/un4EracqMYMW');


INSERT INTO courses (name, description, class_format)
VALUES ('Software Engineering',
        'Course focused on developing and managing software systems.',
        'PRESENTIAL');


INSERT INTO disciplines (name, description, workload_hours, shift, course_id)
VALUES ('Object-Oriented Programming',
        'Discipline about object-oriented programming concepts and practices.',
        80,
        'NIGHT',
        1);


-- Associate the author with the course and the discipline

INSERT INTO user_courses (user_id, course_id)
VALUES (2, 1);

INSERT INTO user_subjects (user_id, discipline_id)
VALUES (2, 1);


-- Sample questions

INSERT INTO questions (type, difficulty, description, option_count, discipline_id, author_id)
VALUES ('OPEN', 'MEDIUM',
        'Explain the concept of polymorphism in object-oriented programming and give an example.',
        NULL,
        1,
        2);

INSERT INTO questions (type, difficulty, description, option_count, discipline_id, author_id)
VALUES ('MULTIPLE_CHOICE', 'EASY',
        'Which of the following principles is NOT part of the four pillars of object-oriented programming?',
        4,
        1,
        2);

INSERT INTO question_options (text, is_correct, question_id)
VALUES ('Encapsulation', FALSE, 2),
       ('Inheritance', FALSE, 2),
       ('Polymorphism', FALSE, 2),
       ('Recursion', TRUE, 2);


-- Template for PDF generation

INSERT INTO templates (name, file_name)
VALUES ('Default Template', 'default.html');


-- Sample assessment using the questions above

INSERT INTO assessments (
    semester,
    teacher,
    assessment_date,
    elaboration_date,
    type,
    value,
    multiple_choice_question_count,
    open_question_count,
    course_id,
    discipline_id,
    author_id
)
VALUES (
           '2026.1',
           'Prof. Example',
           '2026-06-15',
           '2026-05-10',
           'AV1',
           10.00,
           1,
           1,
           1,
           1,
           2
       );


INSERT INTO assessment_multiple_choice_questions (assessment_id, question_id, value)
VALUES (1, 2, 2.50);

INSERT INTO assessment_open_questions (assessment_id, question_id, value)
VALUES (1, 1, 7.50);