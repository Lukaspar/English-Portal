CREATE TABLE IF NOT EXISTS english_dictionary (
    id serial NOT NULL,
    english_word varchar(80) NOT NULL,
    polish_transcription varchar(80) NOT NULL,
    CONSTRAINT english_dictionary_pkey PRIMARY KEY (id)
);