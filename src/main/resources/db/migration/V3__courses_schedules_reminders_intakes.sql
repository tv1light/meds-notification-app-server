-- src/main/resources/db/migration/V3__courses_schedules_reminders_intakes.sql

CREATE TABLE IF NOT EXISTS courses (
                                       id             BIGSERIAL PRIMARY KEY,
                                       user_id        BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                                       medication_id  BIGINT NOT NULL REFERENCES medications(id),
                                       start_date     DATE   NOT NULL,
                                       end_date       DATE,                          -- NULL = бессрочный курс
                                       notes          TEXT,
                                       created_at     TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS schedules (
                                         id           BIGSERIAL PRIMARY KEY,
                                         course_id    BIGINT NOT NULL REFERENCES courses(id) ON DELETE CASCADE,
                                         remind_time  TIME   NOT NULL,                 -- время напоминания, напр. 08:30
                                         created_at   TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS reminders (
                                         id           BIGSERIAL PRIMARY KEY,
                                         schedule_id  BIGINT NOT NULL REFERENCES schedules(id) ON DELETE CASCADE,
                                         remind_at    TIMESTAMPTZ NOT NULL,            -- конкретный момент: дата + время
                                         status       VARCHAR(20)  NOT NULL DEFAULT 'PENDING',
                                         CONSTRAINT chk_reminder_status CHECK (status IN ('PENDING','DONE','SKIPPED','SNOOZED'))
);

CREATE INDEX IF NOT EXISTS idx_reminders_remind_at ON reminders(remind_at);

CREATE TABLE IF NOT EXISTS intakes (
                                       id           BIGSERIAL PRIMARY KEY,
                                       reminder_id  BIGINT NOT NULL REFERENCES reminders(id) ON DELETE CASCADE,
                                       user_id      BIGINT NOT NULL REFERENCES users(id),
                                       action       VARCHAR(20) NOT NULL,
                                       actioned_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                                       snoozed_to   TIMESTAMPTZ,                     -- заполняется при SNOOZED
                                       CONSTRAINT chk_intake_action CHECK (action IN ('DONE','SKIPPED','SNOOZED'))
);